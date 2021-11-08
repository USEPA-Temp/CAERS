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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import gov.epa.cef.web.client.api.ExcelParserClient;
import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.config.TestCategories;
import gov.epa.cef.web.config.slt.SLTConfigImpl;
import gov.epa.cef.web.domain.CalculationMaterialCode;
import gov.epa.cef.web.domain.CalculationMethodCode;
import gov.epa.cef.web.domain.CalculationParameterTypeCode;
import gov.epa.cef.web.domain.ContactTypeCode;
import gov.epa.cef.web.domain.ControlMeasureCode;
import gov.epa.cef.web.domain.EfVariableValidationType;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionFactor;
import gov.epa.cef.web.domain.EmissionFormulaVariableCode;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FipsCounty;
import gov.epa.cef.web.domain.FipsStateCode;
import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.Pollutant;
import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.domain.UnitTypeCode;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.provider.system.SLTPropertyProvider;
import gov.epa.cef.web.repository.AircraftEngineTypeCodeRepository;
import gov.epa.cef.web.repository.CalculationMaterialCodeRepository;
import gov.epa.cef.web.repository.CalculationMethodCodeRepository;
import gov.epa.cef.web.repository.CalculationParameterTypeCodeRepository;
import gov.epa.cef.web.repository.ContactTypeCodeRepository;
import gov.epa.cef.web.repository.ControlMeasureCodeRepository;
import gov.epa.cef.web.repository.ControlRepository;
import gov.epa.cef.web.repository.EmissionFactorRepository;
import gov.epa.cef.web.repository.EmissionFormulaVariableCodeRepository;
import gov.epa.cef.web.repository.EmissionsOperatingTypeCodeRepository;
import gov.epa.cef.web.repository.EmissionsProcessRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.EmissionsUnitRepository;
import gov.epa.cef.web.repository.FacilityCategoryCodeRepository;
import gov.epa.cef.web.repository.FacilitySourceTypeCodeRepository;
import gov.epa.cef.web.repository.FipsCountyRepository;
import gov.epa.cef.web.repository.FipsStateCodeRepository;
import gov.epa.cef.web.repository.MasterFacilityRecordRepository;
import gov.epa.cef.web.repository.NaicsCodeRepository;
import gov.epa.cef.web.repository.OperatingStatusCodeRepository;
import gov.epa.cef.web.repository.PollutantRepository;
import gov.epa.cef.web.repository.ProgramSystemCodeRepository;
import gov.epa.cef.web.repository.ReleasePointRepository;
import gov.epa.cef.web.repository.ReleasePointTypeCodeRepository;
import gov.epa.cef.web.repository.ReportingPeriodCodeRepository;
import gov.epa.cef.web.repository.TribalCodeRepository;
import gov.epa.cef.web.repository.UnitMeasureCodeRepository;
import gov.epa.cef.web.repository.UnitTypeCodeRepository;
import gov.epa.cef.web.service.CersXmlService;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.service.mapper.BulkUploadMapper;
import gov.epa.cef.web.service.mapper.BulkUploadMapperImpl;
import gov.epa.cef.web.service.mapper.EmissionsReportMapper;
import gov.epa.cef.web.service.mapper.EmissionsReportMapperImpl;
import gov.epa.cef.web.service.mapper.cers._1._2.CersDataTypeMapper;
import gov.epa.cef.web.service.mapper.cers._1._2.CersDataTypeMapperImpl;
import gov.epa.cef.web.service.mapper.cers._1._2.CersEmissionsUnitMapper;
import gov.epa.cef.web.service.mapper.cers._1._2.CersEmissionsUnitMapperImpl;
import gov.epa.cef.web.service.mapper.cers._1._2.CersFacilitySiteMapper;
import gov.epa.cef.web.service.mapper.cers._1._2.CersFacilitySiteMapperImpl;
import gov.epa.cef.web.service.mapper.cers._1._2.CersReleasePointMapper;
import gov.epa.cef.web.service.mapper.cers._1._2.CersReleasePointMapperImpl;
import gov.epa.cef.web.service.mapper.cers._2._0.CersV2ControlMapper;
import gov.epa.cef.web.service.mapper.cers._2._0.CersV2ControlMapperImpl;
import gov.epa.cef.web.service.mapper.cers._2._0.CersV2DataTypeMapper;
import gov.epa.cef.web.service.mapper.cers._2._0.CersV2DataTypeMapperImpl;
import gov.epa.cef.web.service.mapper.cers._2._0.CersV2EmissionsUnitMapper;
import gov.epa.cef.web.service.mapper.cers._2._0.CersV2EmissionsUnitMapperImpl;
import gov.epa.cef.web.service.mapper.cers._2._0.CersV2FacilitySiteMapper;
import gov.epa.cef.web.service.mapper.cers._2._0.CersV2FacilitySiteMapperImpl;
import gov.epa.cef.web.service.mapper.cers._2._0.CersV2ReleasePointMapper;
import gov.epa.cef.web.service.mapper.cers._2._0.CersV2ReleasePointMapperImpl;
import gov.epa.cef.web.util.SLTConfigHelper;
import net.exchangenetwork.schema.cer._1._2.CERSDataType;
import net.exchangenetwork.schema.cer._1._2.ControlApproachDataType;
import net.exchangenetwork.schema.cer._1._2.EmissionsDataType;
import net.exchangenetwork.schema.cer._1._2.ObjectFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Category(TestCategories.FastTest.class)
public class CersXmlServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void totalEmissionsRoundingTest() {

        CersEmissionsUnitMapper emissionsUnitMapper =
            new CersEmissionsUnitMapperImpl(new CersReleasePointMapperImpl());

        Emission emission = new Emission();
        emission.setTotalEmissions(new BigDecimal("7323.234258245252345"));

        EmissionsDataType emissionsDataType = emissionsUnitMapper.emissionsFromEmission(emission);
        assertEquals("7323.234258", emissionsDataType.getTotalEmissions());

        emission.setTotalEmissions(new BigDecimal("7323.234258745252345"));
        emissionsDataType = emissionsUnitMapper.emissionsFromEmission(emission);
        assertEquals("7323.234259", emissionsDataType.getTotalEmissions());

        emission.setTotalEmissions(new BigDecimal("7323.234257745252345"));
        emissionsDataType = emissionsUnitMapper.emissionsFromEmission(emission);
        assertEquals("7323.234258", emissionsDataType.getTotalEmissions());

        emission.setTotalEmissions(new BigDecimal("7323.234257345252345"));
        emissionsDataType = emissionsUnitMapper.emissionsFromEmission(emission);
        assertEquals("7323.234257", emissionsDataType.getTotalEmissions());
    }

    @Test
    public void noFacilitySiteAffiliationTest() throws Exception {

        CERSDataType cersData =
            createCersDataType("json/cersXmlServiceImpl/cef-916.json", "12687411");

        cersData.getFacilitySite().forEach(facilitySiteDataType -> {

            assertTrue(facilitySiteDataType.getFacilitySiteAffiliation().isEmpty());
        });

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            JAXBContext jaxbContext = JAXBContext.newInstance(CERSDataType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            jaxbMarshaller.marshal(new ObjectFactory().createCERS(cersData), outputStream);

            String xml = outputStream.toString(StandardCharsets.UTF_8.name());
            assertFalse(xml.contains("FacilitySiteAffiliation"));
        }
    }

    @Test
    public void noEmptyProcessControlApproach() throws Exception {

        CERSDataType cersData =
            createCersDataType("json/cersXmlServiceImpl/cef-916.json", "12687411");

        cersData.getFacilitySite().forEach(facilitySiteDataType -> {

            facilitySiteDataType.getEmissionsUnit().forEach(emissionsUnitDataType -> {

                emissionsUnitDataType.getUnitEmissionsProcess().forEach(processDataType -> {

                    ControlApproachDataType ca = processDataType.getProcessControlApproach();
                    if (ca != null) {

                        assertTrue(ca.getControlPollutant().size() > 0
                            || ca.getControlMeasure().size() > 0);
                    }
                });
            });
        });

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            JAXBContext jaxbContext = JAXBContext.newInstance(CERSDataType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            jaxbMarshaller.marshal(new ObjectFactory().createCERS(cersData), outputStream);

            String xml = outputStream.toString(StandardCharsets.UTF_8.name());
            assertFalse(xml.contains("<ProcessControlApproach/>"));
        }
    }

    private CERSDataType createCersDataType(String filename, String eisId) throws IOException {

        EmissionsReport emissionsReport = createEmissionsReport(filename);

        assertEquals(eisId, emissionsReport.getEisProgramId());

        FacilitySite facilitySite = emissionsReport.getFacilitySites().get(0);

//        assertEquals(eisId, facilitySite.getEisProgramId());

        UserService userService = mock(UserService.class);
        when(userService.getCurrentUser()).then(invocation ->
            new UserDto()
                .withFirstName("Fred")
                .withLastName("Flintstone")
                .withEmail("fflinstone@bedrock.com"));

        EmissionsReportRepository reportRepo = mock(EmissionsReportRepository.class);
        when(reportRepo.findById(any())).thenReturn(Optional.of(emissionsReport));

        CersReleasePointMapper releasePointMapper = new CersReleasePointMapperImpl();
        CersEmissionsUnitMapper emissionsUnitMapper = new CersEmissionsUnitMapperImpl(releasePointMapper);
        CersFacilitySiteMapper facilitySiteMapper = new CersFacilitySiteMapperImpl(emissionsUnitMapper, releasePointMapper);
        CersDataTypeMapper cersMapper = new CersDataTypeMapperImpl(facilitySiteMapper);

        CersV2ControlMapper controlV2mapper = new CersV2ControlMapperImpl();
        CersV2ReleasePointMapper releasePointV2Mapper = new CersV2ReleasePointMapperImpl();
        CersV2EmissionsUnitMapper emissionsUnitV2Mapper = new CersV2EmissionsUnitMapperImpl(releasePointV2Mapper);
        CersV2FacilitySiteMapper facilitySiteV2Mapper = new CersV2FacilitySiteMapperImpl(controlV2mapper, emissionsUnitV2Mapper, releasePointV2Mapper);
        CersV2DataTypeMapper cersV2Mapper = new CersV2DataTypeMapperImpl(facilitySiteV2Mapper);

        CersXmlService cersXmlService = new CersXmlServiceImpl(userService, reportRepo, cersMapper, emissionsUnitMapper, releasePointMapper, cersV2Mapper);

        return cersXmlService.generateCersData(1l, null);
    }

    private EmissionsReport createEmissionsReport(String filename) throws IOException {

        URL json = Resources.getResource(filename);
        assertNotNull(json);
        JsonNode jsonNode = this.objectMapper.readTree(json);

        EmissionsReportBulkUploadDto dto = new BulkUploadServiceImpl.JsonNodeToBulkUploadDto(
            this.objectMapper, true).apply(jsonNode);

        FacilitySiteBulkUploadDto facilitySite = dto.getFacilitySites().get(0);

        dto.setProgramSystemCode("GADNR");
        dto.setEisProgramId(facilitySite.getEisProgramId());
        dto.setMasterFacilityRecordId(facilitySite.getMasterFacilityRecordId());
        dto.setAltSiteIdentifier(facilitySite.getStateCode());
        dto.setYear(Short.valueOf("2019"));
        dto.setStatus(ReportStatus.IN_PROGRESS.name());
        dto.setValidationStatus(ValidationStatus.UNVALIDATED.name());

        return this.bulkUploadService.toEmissionsReport().apply(dto);
    }


    /*
    I could not figure out a way to get around the mess below w/o refactoring
    so I'll hide it down here and hope no one sees it
     */

    @Before
    public void initMocks(){

        MockitoAnnotations.initMocks(this);

        // wire all the repos to return something
        when(this.aircraftEngineRepo.findById(any())).thenReturn(Optional.empty());
        when(this.calcMaterialCodeRepo.findById(any())).then(invocation -> {

            CalculationMaterialCode result = new CalculationMaterialCode();
            result.setCode(invocation.getArgument(0).toString());
            result.setDescription(invocation.getArgument(0).toString().concat(" Description"));

            return Optional.of(result);
        });
        when(this.calcMethodCodeRepo.findById(any())).then(invocation -> {

            CalculationMethodCode result = new CalculationMethodCode();
            result.setCode(invocation.getArgument(0).toString());
            result.setDescription(invocation.getArgument(0).toString().concat(" Description"));

            return Optional.of(result);
        });
        when(this.calcParamTypeCodeRepo.findById(any())).then(invocation -> {

            CalculationParameterTypeCode result = new CalculationParameterTypeCode();
            result.setCode(invocation.getArgument(0).toString());
            result.setDescription(invocation.getArgument(0).toString().concat(" Description"));

            return Optional.of(result);
        });
        when(this.contactTypeRepo.findById(any())).then(invocation -> {

            ContactTypeCode result = new ContactTypeCode();
            result.setCode(invocation.getArgument(0).toString());
            result.setDescription(invocation.getArgument(0).toString().concat(" Description"));

            return Optional.of(result);
        });
        when(this.controlMeasureCodeRepo.findById(any())).then(invocation -> {

            ControlMeasureCode result = new ControlMeasureCode();
            result.setCode(invocation.getArgument(0).toString());
            result.setDescription(invocation.getArgument(0).toString().concat(" Description"));

            return Optional.of(result);
        });
        when(this.countyRepo.findById(any())).then(invocation -> {

            FipsCounty result = new FipsCounty();
            result.setCode(invocation.getArgument(0).toString());
            result.setName(invocation.getArgument(0).toString().concat(" Description"));

            return Optional.of(result);
        });
        when(this.emissionFormulaVariableCodeRepo.findById(any())).then(invocation -> {

            EmissionFormulaVariableCode result = new EmissionFormulaVariableCode();
            result.setCode("A");
            result.setValidationType(EfVariableValidationType.PERCENT);
            result.setDescription("% Ash");

            return Optional.of(result);
        });
        when(this.emissionsOperatingTypeCodeRepo.findById(any())).thenReturn(Optional.empty());
        when(this.facilityCategoryRepo.findById(any())).thenReturn(Optional.empty());
        when(this.facilitySourceTypeRepo.findById(any())).thenReturn(Optional.empty());
        when(this.mfrRepo.findByProgramSystemCodeCodeAndAgencyFacilityId(any(),any())).thenReturn(Optional.of(new MasterFacilityRecord()));
        when(this.mfrRepo.findById(any())).thenReturn(Optional.of(new MasterFacilityRecord()));
        when(this.naicsCodeRepo.findById(any())).thenReturn(Optional.empty());
        when(this.operatingStatusRepo.findById(any())).then(invocation -> {

            OperatingStatusCode result = new OperatingStatusCode();
            result.setCode(invocation.getArgument(0).toString());
            result.setDescription(invocation.getArgument(0).toString().concat(" Description"));

            return Optional.of(result);
        });
        when(this.pollutantRepo.findById(any())).then(invocation -> {

            Pollutant result = new Pollutant();
            result.setPollutantCode(invocation.getArgument(0).toString());
            result.setPollutantName(invocation.getArgument(0).toString().concat(" Description"));
            result.setPollutantType("OTH");
            result.setPollutantStandardUomCode("LB");

            return Optional.of(result);
        });
        when(this.programSystemCodeRepo.findById(any())).thenReturn(Optional.empty());
        when(this.releasePointTypeRepo.findById(any())).thenReturn(Optional.empty());
        when(this.reportingPeriodCodeRepo.findById(any())).thenReturn(Optional.empty());
        when(this.stateCodeRepo.findById(any())).then(invocation -> {

            FipsStateCode result = new FipsStateCode();
            result.setCode(invocation.getArgument(0).toString());
            result.setName(invocation.getArgument(0).toString());

            return Optional.of(result);
        });
        when(this.tribalCodeRepo.findById(any())).thenReturn(Optional.empty());
        when(this.unitMeasureCodeRepo.findById(any())).then(invocation -> {

            UnitMeasureCode result = new UnitMeasureCode();
            result.setCode(invocation.getArgument(0).toString());
            result.setDescription(invocation.getArgument(0).toString().concat(" Description"));

            return Optional.of(result);
        });
        when(this.unitTypeRepo.findById(any())).then(invocation -> {

            UnitTypeCode result = new UnitTypeCode();
            result.setCode(invocation.getArgument(0).toString());
            result.setDescription(invocation.getArgument(0).toString().concat(" Description"));

            return Optional.of(result);
        });
        
        SLTBaseConfig sltBaseConfig = new SLTConfigImpl("GADNR",sltPropertyProvider);
        when(this.sltConfigHelper.getCurrentSLTConfig("GADNR")).thenReturn(sltBaseConfig);
        
        when(this.emissionsReportRepo.findByMasterFacilityRecordId(any())).thenReturn(Collections.emptyList());
        when(this.emissionsUnitRepo.retrieveByFacilityYear(any(), any())).thenReturn(Collections.emptyList());
        when(this.emissionsProcessRepo.retrieveByParentFacilityYear(any(), any(), any())).thenReturn(Collections.emptyList());
        when(this.releasePointRepo.retrieveByFacilityYear(any(), any())).thenReturn(Collections.emptyList());
        when(this.controlRepo.retrieveByFacilityYear(any(), any())).thenReturn(Collections.emptyList());
        
        when(this.efRepo.findBySccCodePollutantEmissionFactorFormulaControlIndicator(any(), any(), any(), any())).thenReturn(mock(EmissionFactor.class));
        when(this.efRepo.findBySccCodePollutantEmissionFactorControlIndicator(any(), any(), any(), any())).thenReturn(mock(EmissionFactor.class));
        
    }
    
    @Mock
    SLTPropertyProvider sltPropertyProvider;
    
    @Mock
    SLTConfigHelper sltConfigHelper;
    
    @Mock
    AircraftEngineTypeCodeRepository aircraftEngineRepo;

    @Mock
    CalculationMaterialCodeRepository calcMaterialCodeRepo;

    @Mock
    CalculationMethodCodeRepository calcMethodCodeRepo;

    @Mock
    CalculationParameterTypeCodeRepository calcParamTypeCodeRepo;

    @Mock
    ContactTypeCodeRepository contactTypeRepo;

    @Mock
    ControlMeasureCodeRepository controlMeasureCodeRepo;

    @Mock
    FipsCountyRepository countyRepo;

    @Mock
    EmissionFormulaVariableCodeRepository emissionFormulaVariableCodeRepo;

    @Mock
    EmissionsOperatingTypeCodeRepository emissionsOperatingTypeCodeRepo;

    @Spy
    EmissionsReportMapper emissionsReportMapper = new EmissionsReportMapperImpl();

    @Mock
    EmissionsReportService emissionsReportService;

    @Mock
    ExcelParserClient excelParserClient;

    @Mock
    FacilityCategoryCodeRepository facilityCategoryRepo;

    @Mock
    FacilitySourceTypeCodeRepository facilitySourceTypeRepo;

    @Mock
    MasterFacilityRecordRepository mfrRepo;

    @Mock
    NaicsCodeRepository naicsCodeRepo;

    @Mock
    OperatingStatusCodeRepository operatingStatusRepo;

    @Mock
    PollutantRepository pollutantRepo;

    @Mock
    ProgramSystemCodeRepository programSystemCodeRepo;

    @Mock
    ReleasePointTypeCodeRepository releasePointTypeRepo;

    @Mock
    ReportingPeriodCodeRepository reportingPeriodCodeRepo;

    @Mock
    FipsStateCodeRepository stateCodeRepo;

    @Mock
    TribalCodeRepository tribalCodeRepo;

    @Mock
    UnitMeasureCodeRepository unitMeasureCodeRepo;

    @Mock
    UnitTypeCodeRepository unitTypeRepo;

    @Spy
    BulkUploadMapper uploadMapper = new BulkUploadMapperImpl();

    @Mock
    BulkReportValidator validator;

    @InjectMocks
    BulkUploadServiceImpl bulkUploadService;
    
    @Mock
    EmissionsReportRepository emissionsReportRepo;
    
    @Mock
    EmissionsProcessRepository emissionsProcessRepo;
    
    @Mock
    ReleasePointRepository releasePointRepo;
    
    @Mock
    EmissionsUnitRepository emissionsUnitRepo;
    
    @Mock
    ControlRepository controlRepo;
    
    @Mock
    EmissionFactorRepository efRepo;
    
}
