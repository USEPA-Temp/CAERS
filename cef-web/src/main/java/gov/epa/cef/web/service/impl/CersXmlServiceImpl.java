/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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
import gov.epa.cef.web.service.mapper.cers._1._2.CersDataTypeMapper;
import gov.epa.cef.web.service.mapper.cers._1._2.CersEmissionsUnitMapper;
import gov.epa.cef.web.service.mapper.cers._1._2.CersReleasePointMapper;
import gov.epa.cef.web.service.mapper.cers._2._0.CersV2DataTypeMapper;
import gov.epa.cef.web.util.ConstantUtils;
import net.exchangenetwork.schema.cer._1._2.CERSDataType;
import net.exchangenetwork.schema.cer._1._2.ControlApproachDataType;
import net.exchangenetwork.schema.cer._1._2.ControlMeasureDataType;
import net.exchangenetwork.schema.cer._1._2.ControlPollutantDataType;
import net.exchangenetwork.schema.cer._1._2.EmissionsUnitDataType;
import net.exchangenetwork.schema.cer._1._2.FacilitySiteDataType;
import net.exchangenetwork.schema.cer._1._2.ObjectFactory;
import net.exchangenetwork.schema.cer._1._2.ProcessDataType;

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

    private static final Logger logger = LoggerFactory.getLogger(CersXmlServiceImpl.class);

    private final EmissionsReportRepository reportRepo;

    private final UserService userService;

    private final CersDataTypeMapper cersMapper;

    private final CersEmissionsUnitMapper euMapper;

    private final CersReleasePointMapper rpMapper;
    
    private final CersV2DataTypeMapper cersV2Mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
	CersXmlServiceImpl(UserService userService,
					   EmissionsReportRepository reportRepo,
					   CersDataTypeMapper cersMapper,
					   CersEmissionsUnitMapper euMapper,
					   CersReleasePointMapper rpMapper,
					   CersV2DataTypeMapper cersV2Mapper) {

    	this.userService = userService;
    	this.reportRepo = reportRepo;
    	this.cersMapper = cersMapper;
    	this.euMapper = euMapper;
    	this.rpMapper = rpMapper;
    	this.cersV2Mapper = cersV2Mapper;
	}

    public net.exchangenetwork.schema.cer._2._0.CERSDataType generateCersV2Data(Long reportId, EisSubmissionStatus submissionStatus) {

        EmissionsReport source = reportRepo.findById(reportId)
            .orElseThrow(() -> new NotExistException("Emissions Report", reportId));

        if (submissionStatus != null) {
            if (ConstantUtils.EIS_TRANSMISSION_POINT_EMISSIONS.contentEquals(submissionStatus.dataCategory())) {
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

                                   // remove extra data and remove reporting periods without emissions
                                   ep.getReleasePointAppts().clear();
                                   ep.setReportingPeriods(ep.getReportingPeriods().stream()
                                           .filter(rp -> !rp.getEmissions().isEmpty())
                                           .collect(Collectors.toList()));

                               }).filter(ep -> ConstantUtils.STATUS_OPERATING.equals(ep.getOperatingStatusCode().getCode()) && !ep.getReportingPeriods().isEmpty())
                               .collect(Collectors.toList()));
                        }).filter(eu -> ConstantUtils.STATUS_OPERATING.equals(eu.getOperatingStatusCode().getCode()) && !eu.getEmissionsProcesses().isEmpty())
                        .collect(Collectors.toList()));
                });
            } else if (ConstantUtils.EIS_TRANSMISSION_FACILITY_INVENTORY.equals(submissionStatus.dataCategory())) {
                source.getFacilitySites().forEach(fs -> {

                    fs.setEmissionsUnits(fs.getEmissionsUnits().stream()
                        .map(eu -> {
                            
                            // remove extra information from units which are not operational
                            if (!ConstantUtils.STATUS_OPERATING.equals(eu.getOperatingStatusCode().getCode())) {
                                EmissionsUnit result = this.cersV2Mapper.emissionsUnitToNonOperatingEmissionsUnit(eu);
                                return result;
                            } else {
                            
                                //first set the Processes for the emissions unit
                                eu.setEmissionsProcesses(eu.getEmissionsProcesses().stream()
                                    .map(ep -> {
                                        
                                        //remove all reporting periods, operating details, and emissions from the emission process
                                        //for a FacilityInventory submission
                                        ep.getReportingPeriods().clear();
    
                                        // remove extra information from processes which are not operational
                                        if (!ConstantUtils.STATUS_OPERATING.equals(ep.getOperatingStatusCode().getCode())) {
                                            EmissionsProcess result = this.cersV2Mapper.processToNonOperatingEmissionsProcess(ep);
                                            return result;
                                        }
                                        return ep;
                                    }).collect(Collectors.toList()));
                                
                                return eu;
                            }
                            
                        }).collect(Collectors.toList()));

                    fs.setReleasePoints(fs.getReleasePoints().stream()
                        .map(rp -> {

                            // remove extra information from release points which are not operational
                            if (!ConstantUtils.STATUS_OPERATING.equals(rp.getOperatingStatusCode().getCode())) {
                                ReleasePoint result = this.cersV2Mapper.releasePointToNonOperatingReleasePoint(rp);
                                return result;
                            }
                            return rp;
                        }).collect(Collectors.toList()));

                });
            }
        }

        net.exchangenetwork.schema.cer._2._0.CERSDataType cers = cersV2Mapper.fromEmissionsReport(source);

        cers.setUserIdentifier(userService.getCurrentUser().getEmail());

        // check if this exists for unit tests
        if (this.entityManager != null) {
            // detach entity from session so that the dirty entity won't get picked up by other database calls
            entityManager.detach(source);
        }

        return cers;
    }

	/* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.CersXmlService#generateCersData(java.lang.Long)
     */
    @Override
    public CERSDataType generateCersData(Long reportId, EisSubmissionStatus submissionStatus) {

        EmissionsReport source = reportRepo.findById(reportId)
            .orElseThrow(() -> new NotExistException("Emissions Report", reportId));

        if (submissionStatus != null) {
            if (ConstantUtils.EIS_TRANSMISSION_POINT_EMISSIONS.contentEquals(submissionStatus.dataCategory())) {
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

                                   // remove extra data and remove reporting periods without emissions
                                   ep.getReleasePointAppts().clear();
                                   ep.setReportingPeriods(ep.getReportingPeriods().stream()
                                           .filter(rp -> !rp.getEmissions().isEmpty())
                                           .collect(Collectors.toList()));

                               }).filter(ep -> ConstantUtils.STATUS_OPERATING.equals(ep.getOperatingStatusCode().getCode()) && !ep.getReportingPeriods().isEmpty())
                               .collect(Collectors.toList()));
                        }).filter(eu -> ConstantUtils.STATUS_OPERATING.equals(eu.getOperatingStatusCode().getCode()) && !eu.getEmissionsProcesses().isEmpty())
                        .collect(Collectors.toList()));
                });
            } else if (ConstantUtils.EIS_TRANSMISSION_FACILITY_INVENTORY.equals(submissionStatus.dataCategory())) {
                source.getFacilitySites().forEach(fs -> {

                    fs.setEmissionsUnits(fs.getEmissionsUnits().stream()
                        .map(eu -> {
                        	
                            // remove extra information from units which are not operational
                            if (!ConstantUtils.STATUS_OPERATING.equals(eu.getOperatingStatusCode().getCode())) {
                                EmissionsUnit result = this.euMapper.emissionsUnitToNonOperatingEmissionsUnit(eu);
                                return result;
                            } else {
                            
	                        	//first set the Processes for the emissions unit
	                            eu.setEmissionsProcesses(eu.getEmissionsProcesses().stream()
	                                .map(ep -> {
	                                	
	                                    //remove all reporting periods, operating details, and emissions from the emission process
	                                    //for a FacilityInventory submission
	                                    ep.getReportingPeriods().clear();
	
	                                    // remove extra information from processes which are not operational
	                                    if (!ConstantUtils.STATUS_OPERATING.equals(ep.getOperatingStatusCode().getCode())) {
	                                        EmissionsProcess result = this.euMapper.processToNonOperatingEmissionsProcess(ep);
	                                        return result;
	                                    }
	                                    return ep;
	                                }).collect(Collectors.toList()));
	                            
	                            return eu;
                            }
                            
                        }).collect(Collectors.toList()));

                    fs.setReleasePoints(fs.getReleasePoints().stream()
                        .map(rp -> {

                            // remove extra information from release points which are not operational
                            if (!ConstantUtils.STATUS_OPERATING.equals(rp.getOperatingStatusCode().getCode())) {
                                ReleasePoint result = this.rpMapper.releasePointToNonOperatingReleasePoint(rp);
                                return result;
                            }
                            return rp;
                        }).collect(Collectors.toList()));

                });
            }
        }

        CERSDataType cers = cersMapper.fromEmissionsReport(source);

        if (submissionStatus == null || !ConstantUtils.EIS_TRANSMISSION_POINT_EMISSIONS.equals(submissionStatus.dataCategory())) {
            addProcessControls(source, cers);
        }

        cers.setUserIdentifier(userService.getCurrentUser().getEmail());

        // check if this exists for unit tests
        if (this.entityManager != null) {
            // detach entity from session so that the dirty entity won't get picked up by other database calls
            entityManager.detach(source);
        }

        return cers;
    }


    @Override
    public void writeCersV2XmlTo(long reportId, OutputStream outputStream, EisSubmissionStatus submissionStatus) {

        net.exchangenetwork.schema.cer._2._0.CERSDataType cers = generateCersV2Data(reportId, submissionStatus);

        try {
            net.exchangenetwork.schema.cer._2._0.ObjectFactory objectFactory = new net.exchangenetwork.schema.cer._2._0.ObjectFactory();
            JAXBContext jaxbContext = JAXBContext.newInstance(net.exchangenetwork.schema.cer._2._0.CERSDataType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            jaxbMarshaller.marshal(objectFactory.createCERS(cers), outputStream);

        } catch (JAXBException e) {

            logger.error("error while marshalling", e);
            throw ApplicationException.asApplicationException(e);
        }
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

            logger.error("error while marshalling", e);
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

        // average percent control
        List<Control> cList = controls.stream()
                .filter(c -> c.getPercentControl() != null)
                .collect(Collectors.toList());
        BigDecimal totalPercentControlApproachEff = cList.stream()
        		.map(Control::getPercentControl)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        if (!cList.isEmpty()) {
        	ca.setPercentControlApproachEffectiveness(totalPercentControlApproachEff.divide(BigDecimal.valueOf(cList.size())).setScale(3, RoundingMode.HALF_UP));
        }

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
            BigDecimal totalPercentControlRedEff = entry.getValue().stream()
                    .map(ControlPollutant::getPercentReduction)
                    .reduce(BigDecimal.ZERO,BigDecimal::add);
            cp.setPercentControlMeasuresReductionEfficiency(totalPercentControlRedEff.divide(BigDecimal.valueOf(entry.getValue().size())).setScale(3, RoundingMode.HALF_UP));
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

            if (assignment.getControl() != null && ConstantUtils.STATUS_OPERATING.equals(assignment.getControl().getOperatingStatusCode().getCode())) {
                result.add(assignment.getControl());
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
