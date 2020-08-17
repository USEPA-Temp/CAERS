package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlMeasureCode;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.CersXmlService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.EisSubmissionStatus;
import gov.epa.cef.web.service.mapper.cers.CersDataTypeMapper;
import gov.epa.cef.web.service.mapper.cers.CersEmissionsUnitMapper;
import gov.epa.cef.web.service.mapper.cers.CersReleasePointMapper;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
public class CersXmlServiceImpl implements CersXmlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CersXmlServiceImpl.class);

    private final EmissionsReportRepository reportRepo;

    private final UserService userService;

    private final CersDataTypeMapper cersMapper;

    private final CersEmissionsUnitMapper euMapper;

    private final CersReleasePointMapper rpMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
	CersXmlServiceImpl(UserService userService,
					   EmissionsReportRepository reportRepo,
					   CersDataTypeMapper cersMapper,
					   CersEmissionsUnitMapper euMapper,
					   CersReleasePointMapper rpMapper) {

    	this.userService = userService;
    	this.reportRepo = reportRepo;
    	this.cersMapper = cersMapper;
    	this.euMapper = euMapper;
    	this.rpMapper = rpMapper;
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
            } else if ("FacilityInventory".contentEquals(submissionStatus.dataCategory())) {
                source.getFacilitySites().forEach(fs -> {

                    // remove extra information from PS units
                    fs.setEmissionsUnits(fs.getEmissionsUnits().stream()
                        .map(eu -> {

                            if ("PS".equals(eu.getOperatingStatusCode().getCode())) {
                                EmissionsUnit result = this.euMapper.emissionsUnitToPSEmissionsUnit(eu);
                                return result;
                            } else {
                                // remove extra information from PS processes
                                eu.setEmissionsProcesses(eu.getEmissionsProcesses().stream()
                                    .map(ep -> {

                                        if ("PS".equals(ep.getOperatingStatusCode().getCode())) {
                                            EmissionsProcess result = this.euMapper.processToPsEmissionsProcess(ep);
                                            return result;
                                        }
                                        return ep;
                                    }).collect(Collectors.toList()));
                            }
                            return eu;
                        }).collect(Collectors.toList()));

                    // remove extra information from PS release points
                    fs.setReleasePoints(fs.getReleasePoints().stream()
                        .map(rp -> {

                            if ("PS".equals(rp.getOperatingStatusCode().getCode())) {
                                ReleasePoint result = this.rpMapper.releasePointToPSReleasePoint(rp);
                                return result;
                            }
                            return rp;
                        }).collect(Collectors.toList()));

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

        // detach entity from session so that the dirty entity won't get picked up by other database calls
        entityManager.detach(source);

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

				    ControlApproachDataType ca = createProcessControlApproach(process);

				    if (ca != null && ca.getControlPollutant().size() > 0 && ca.getControlMeasure().size() > 0) {

                          addControlToCersProcess(ca, process, cers);
                      }
				}
			}
		}

	}


    /**
     * Create a Control Approach for a process by averaging all values for all paths the process uses
     * @param process
     * @return
     */
    private ControlApproachDataType createProcessControlApproach(EmissionsProcess process) {

        List<Control> controls = new ArrayList<>();

        ControlApproachDataType ca = new ControlApproachDataType();

        // find all controls for each RPA with a control path
        for (ReleasePointAppt rpa : process.getReleasePointAppts()) {
            if (rpa.getControlPath() != null) {
                controls.addAll(findChildControls(rpa.getControlPath()));
            }
        }

        if (controls.isEmpty()) {
            return null;
        }

        // average percent capture
        ca.setPercentControlApproachCaptureEfficiency(BigDecimal.valueOf(controls.stream()
                .filter(c -> c.getPercentCapture() != null)
                .collect(Collectors.averagingDouble(Control::getPercentCapture))).setScale(1, RoundingMode.HALF_UP));

        // average percent control
        ca.setPercentControlApproachEffectiveness(BigDecimal.valueOf(controls.stream()
                .filter(c -> c.getPercentControl() != null)
                .collect(Collectors.averagingDouble(Control::getPercentControl))).setScale(1, RoundingMode.HALF_UP));

        // make description a list of control measure code descriptions
        ca.setControlApproachDescription(controls.stream()
                .map(Control::getControlMeasureCode)
                .map(ControlMeasureCode::getDescription)
                .distinct()
                .collect(Collectors.joining(", ")));

        // find distinct control measure codes and add them as control measure data types
        ca.getControlMeasure().addAll(controls.stream()
                .map(Control::getControlMeasureCode)
                .map(ControlMeasureCode::getCode)
                .distinct()
                .map(code -> {
                    ControlMeasureDataType cm = new ControlMeasureDataType();
                    cm.setControlMeasureCode(code);
                    return cm;
                }).collect(Collectors.toList()));

        // make a map of pollutants for easy use
        Map<String, List<ControlPollutant>> pollutantMap = controls.stream()
                .filter(c -> !c.getPollutants().isEmpty())
                .flatMap(c -> c.getPollutants().stream())
                .collect(Collectors.groupingBy(cp -> cp.getPollutant().getPollutantCode()));

        // create a control pollutant with the average % reduction of that pollutant across all controls
        for (Entry<String, List<ControlPollutant>> entry : pollutantMap.entrySet()) {

            ControlPollutantDataType cp = new ControlPollutantDataType();
            cp.setPercentControlMeasuresReductionEfficiency(BigDecimal.valueOf((entry.getValue().stream()
                    .collect(Collectors.averagingDouble(ControlPollutant::getPercentReduction)))).setScale(1, RoundingMode.HALF_UP));
            cp.setPollutantCode(entry.getKey());
            ca.getControlPollutant().add(cp);
        }

        return ca;
    }

    /**
     * Recursively find all controls associated with a control path by navigating down the tree of paths.
     * This also stores the control measure data while it is in reference
     * @param path
     * @param ca
     * @return
     */
    private List<Control> findChildControls(ControlPath path) {

        List<Control> result = new ArrayList<>();

        for (ControlAssignment assignment : path.getAssignments()) {

            if (assignment.getControl() != null) {
                result.add(assignment.getControl());

//                if (!isDuplicateControlMeasure(ca.getControlMeasure(), assignment.getControl())) {
//                    ControlMeasureDataType cm = new ControlMeasureDataType();
//                    cm.setControlMeasureCode(assignment.getControl().getControlMeasureCode().getCode());
//                    cm.setControlMeasureSequence(assignment.getSequenceNumber().toString());
//                    ca.getControlMeasure().add(cm);
//                }
            }

            if (assignment.getControlPathChild() != null) {
                // recursively find child controls
                result.addAll(findChildControls(assignment.getControlPathChild()));
            }
        }

        return result;
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
			    // check to make sure the parent unit is the same
			    if (sourceProcess.getEmissionsUnit().getUnitIdentifier().equals(cersUnit.getUnitIdentification().get(0).getIdentifier())) {
    				for (ProcessDataType cersProcess : cersUnit.getUnitEmissionsProcess()) {
    					if (processesMatch(sourceProcess, cersProcess)) {
    						cersProcess.setProcessControlApproach(ca);
    					}
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
