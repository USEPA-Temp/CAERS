package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.CersXmlService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.EisSubmissionStatus;
import gov.epa.cef.web.service.mapper.cers.CersDataTypeMapper;
import net.exchangenetwork.schema.cer._1._2.CERSDataType;
import net.exchangenetwork.schema.cer._1._2.ControlApproachDataType;
import net.exchangenetwork.schema.cer._1._2.ControlMeasureDataType;
import net.exchangenetwork.schema.cer._1._2.ControlPollutantDataType;
import net.exchangenetwork.schema.cer._1._2.EmissionsUnitDataType;
import net.exchangenetwork.schema.cer._1._2.FacilitySiteDataType;
import net.exchangenetwork.schema.cer._1._2.ObjectFactory;
import net.exchangenetwork.schema.cer._1._2.ProcessDataType;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CersXmlServiceImpl implements CersXmlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CersXmlServiceImpl.class);

    private final EmissionsReportRepository reportRepo;

    private final UserService userService;

    private final CersDataTypeMapper cersMapper;

    @Autowired
	CersXmlServiceImpl(UserService userService,
					   EmissionsReportRepository reportRepo,
					   CersDataTypeMapper cersMapper) {

    	this.userService = userService;
    	this.reportRepo = reportRepo;
    	this.cersMapper = cersMapper;
	}

	/* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.CersXmlService#generateCersData(java.lang.Long)
     */
    @Override
    public CERSDataType generateCersData(Long reportId, EisSubmissionStatus submissionStatus) {

        EmissionsReport source = reportRepo.findById(reportId)
            .orElseThrow(() -> new NotExistException("Emissions Report", reportId));

        if (submissionStatus != null) {
            if ("Point".contentEquals(submissionStatus.dataCategory())) {
                source.getFacilitySites().forEach(fs -> {

                    // remove extra data
                    fs.getReleasePoints().clear();
                    fs.getControlPaths().clear();
                    fs.getControls().clear();
                    // remove non-operating units and units without processes
                    fs.setEmissionsUnits(fs.getEmissionsUnits().stream()
                        .peek(eu -> {
                           
                            // remove non-operating processes and processes without emissions
                            eu.setEmissionsProcesses(eu.getEmissionsProcesses().stream()
                               .peek(ep -> {

                                   // remove extra data and remove periods without emissions
                                   ep.getReleasePointAppts().clear();
                                   ep.setReportingPeriods(ep.getReportingPeriods().stream()
                                           .filter(rp -> !rp.getEmissions().isEmpty())
                                           .collect(Collectors.toList()));

                               }).filter(ep -> "OP".equals(ep.getOperatingStatusCode().getCode()) && !ep.getReportingPeriods().isEmpty())
                               .collect(Collectors.toList()));
                        }).filter(eu -> "OP".equals(eu.getOperatingStatusCode().getCode()) && !eu.getEmissionsProcesses().isEmpty())
                        .collect(Collectors.toList()));
                });
            }
        }

        CERSDataType cers = cersMapper.fromEmissionsReport(source);
        // TODO: find out if programSystemCode should always be the same at the facilitySite and report level
        // and if this needs to be added at the report level or moved there instead
        if (!cers.getFacilitySite().isEmpty()) {
            cers.setProgramSystemCode(cers.getFacilitySite().get(0).getFacilityIdentification().get(0).getProgramSystemCode());
        }

        if (submissionStatus == null || !"Point".contentEquals(submissionStatus.dataCategory())) {
            addProcessControls(source, cers);
        }

        cers.setUserIdentifier(userService.getCurrentUser().getEmail());

        return cers;
    }


	/* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.CersXmlService#retrieveCersXml(java.lang.Long)
     */
    @Override
    public void writeCersXmlTo(long reportId, OutputStream outputStream, EisSubmissionStatus submissionStatus) {
    	
    	CERSDataType cers = generateCersData(reportId, submissionStatus);

        try {
            ObjectFactory objectFactory = new ObjectFactory();
            JAXBContext jaxbContext = JAXBContext.newInstance(CERSDataType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            jaxbMarshaller.marshal(objectFactory.createCERS(cers), outputStream);

        } catch (JAXBException e) {

            LOGGER.error("error while marshalling", e);
            throw ApplicationException.asApplicationException(e);
        }
    }



    /***
     * This method will become obsolete when EIS updates the CERS schema with more robust control reporting
     *
     * Manually add the controls from the EmissionsReport processes to the corresponding CERSDataType process
     *
     * Multiple control measures per control approach
     * Recursively iterate over the control paths to account for child control paths
     * Remove duplicate control pollutants and duplicate control measures
     */
    private void addProcessControls(EmissionsReport report, CERSDataType cers) {
		for (FacilitySite facility : report.getFacilitySites()) {
			for (EmissionsUnit unit : facility.getEmissionsUnits()) {
				for (EmissionsProcess process : unit.getEmissionsProcesses()) {

					//instantiate the control approach
					ControlApproachDataType ca = new ControlApproachDataType();

					for (ReleasePointAppt rpa : process.getReleasePointAppts()) {
						if (rpa.getControlPath() != null) {
							populateControlApproach(rpa.getControlPath(), ca);
						}
					}

					if (ca.getControlPollutant().size() > 0 && ca.getControlMeasure().size() > 0) {

						addControlToCersProcess(ca, process, cers);
					}
				}
			}
		}

	}


    /***
     * Recursive method that loops through the control assignments for each control path and child control paths to increment the control approach capture efficiency,
     * increment the control approach effectiveness, and add the control measures to the control approach
     * @param path Control path whose assignments will be iterated
     * @param ca Control Approach that will be updated
     */
    private void populateControlApproach(ControlPath path, ControlApproachDataType ca) {
		for (ControlAssignment assignment : path.getAssignments()) {
			if (assignment.getControl() != null) {
				ca.setControlApproachComment(assignment.getControl().getComments());
				ca.setControlApproachDescription(assignment.getControl().getDescription());

				// CEF-905 Commenting out
				// PercentControlApproachCaptureEfficiency and Capture Effectiveness are
				// currently being generated greater than 100% by the code. We need to comment
				// these out until we have firm guidance on how to proceed with calculating these

//				//add to the capture efficiency and %effectiveness to the control approach
//				if (assignment.getControl().getPercentCapture() != null) {
//					if (ca.getPercentControlApproachCaptureEfficiency() == null) {
//						ca.setPercentControlApproachCaptureEfficiency(
//								new BigDecimal(assignment.getControl().getPercentCapture().toString()));
//					} else {
//						ca.setPercentControlApproachCaptureEfficiency(ca.getPercentControlApproachCaptureEfficiency().add(
//								new BigDecimal(assignment.getControl().getPercentCapture().toString())));
//					}
//				}
//
//				if (assignment.getControl().getPercentControl() != null) {
//					if (ca.getPercentControlApproachEffectiveness() == null) {
//						ca.setPercentControlApproachEffectiveness(
//								new BigDecimal(assignment.getControl().getPercentControl().toString()));
//					} else {
//						ca.setPercentControlApproachEffectiveness(ca.getPercentControlApproachEffectiveness().add(
//								new BigDecimal(assignment.getControl().getPercentControl().toString())));
//					}
//				}

				//add a new control measure to the control measure list
				if (!isDuplicateControlMeasure(ca.getControlMeasure(), assignment.getControl())) {
					ControlMeasureDataType cm = new ControlMeasureDataType();
					cm.setControlMeasureCode(assignment.getControl().getControlMeasureCode().getCode());
					cm.setControlMeasureSequence(assignment.getSequenceNumber().toString());
					ca.getControlMeasure().add(cm);
				}

				for (ControlPollutant pollutant : assignment.getControl().getPollutants()) {
					if (!isDuplicateControlPollutant(ca.getControlPollutant(), pollutant)) {
						ControlPollutantDataType cp = new ControlPollutantDataType();
						cp.setPercentControlMeasuresReductionEfficiency(new BigDecimal(pollutant.getPercentReduction().toString()));
						cp.setPollutantCode(pollutant.getPollutant().getPollutantCode());
						ca.getControlPollutant().add(cp);
					}
				}
			} else if (assignment.getControlPathChild() != null) {
				//recursive call to collect data from children control paths
				populateControlApproach(assignment.getControlPathChild(), ca);
			}
		}

    }


    /***
     * Add the fully hydrated control approach to the appropriate emissions process within the current CERS object hierarchy
     * @param ca
     * @param sourceProcess
     * @param cers
     */
	private void addControlToCersProcess(ControlApproachDataType ca, EmissionsProcess sourceProcess, CERSDataType cers) {
		for (FacilitySiteDataType cersFacilitySite: cers.getFacilitySite()) {
			for (EmissionsUnitDataType cersUnit : cersFacilitySite.getEmissionsUnit()) {
				for (ProcessDataType cersProcess : cersUnit.getUnitEmissionsProcess()) {
					if (processesMatch(sourceProcess, cersProcess)) {
						cersProcess.setProcessControlApproach(ca);
					}
				}
			}
		}
	}


	/***
	 * Return true if the control already exists in the controlMeasures list
	 * @param controlMeasures
	 * @param control
	 * @return
	 */
	private boolean isDuplicateControlMeasure(List<ControlMeasureDataType> controlMeasures, Control control) {
		for (ControlMeasureDataType controlMeasure : controlMeasures) {
			if (controlMeasure.getControlMeasureCode().equals(control.getControlMeasureCode().getDescription())) {
				return true;
			}
		}
		return false;
	}


	/***
	 * Return true if the pollutant already exists in the controlPollutants list
	 * @param controlPollutants
	 * @param pollutant
	 * @return
	 */
	private boolean isDuplicateControlPollutant(List<ControlPollutantDataType> controlPollutants, ControlPollutant pollutant) {
		for (ControlPollutantDataType controlPollutant : controlPollutants) {
			if (controlPollutant.getPollutantCode().contentEquals(pollutant.getPollutant().getPollutantCode())) {
				return true;
			}
		}
		return false;
	}


	/***
	 * Determine if the sourceProcess and the cersProcess refer to the same emissions process
	 * @param sourceProcess
	 * @param cersProcess
	 * @return
	 */
	private boolean processesMatch(EmissionsProcess sourceProcess, ProcessDataType cersProcess) {

		if (sourceProcess.getSccCode() != null && !sourceProcess.getSccCode().equals(cersProcess.getSourceClassificationCode())) {
			return false;
		}

		if (sourceProcess.getAircraftEngineTypeCode() != null && !sourceProcess.getAircraftEngineTypeCode().getCode().equals(cersProcess.getAircraftEngineTypeCode())) {
			return false;
		}

		if (sourceProcess.getDescription() != null && !sourceProcess.getDescription().equals(cersProcess.getProcessDescription())) {
			return false;
		}

		if (sourceProcess.getComments() != null && !sourceProcess.getComments().equals(cersProcess.getProcessComment())) {
			return false;
		}

		return true;
	}


}
