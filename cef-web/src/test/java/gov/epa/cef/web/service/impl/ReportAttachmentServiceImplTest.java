package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.config.CommonInitializers;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.dto.ReportAttachmentDto;
import gov.epa.cef.web.util.TempFile;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SqlGroup(value = {@Sql("classpath:db/test/emptyTestData.sql")})
@ContextConfiguration(initializers = {
    CommonInitializers.NoCacheInitializer.class
})
public class ReportAttachmentServiceImplTest extends BaseServiceDatabaseTest {


    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
	EmissionsReportRepository reportRepo;
    
    @Autowired
    private ReportAttachmentServiceImpl rptAttachmentSvc;
    
    @Autowired
    WebApplicationContext webContext;

    @Autowired
    DataSource dataSource;

    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    BulkUploadServiceImpl uploadService;
    

    @Before
    public void _onJunitBeginTest() {

        this.jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
        
    }
        
    
    @Test
    public void saveReportAttachmentTest() throws Exception {
    	
    	EmissionsReport report = createHydratedEmissionsReport();
    	report = reportRepo.save(report);
    	
    	String text ="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", text.getBytes());
        
        TempFile temp = TempFile.from(file.getInputStream(), file.getOriginalFilename());
        
        ReportAttachmentDto dto = new ReportAttachmentDto();
        dto.setId(1L);
        dto.setReportId(report.getId());
        dto.setFileName(file.getOriginalFilename());
        dto.setFileType(file.getContentType());
        
        String comments = "Test...";
    	
        assertNotNull(dto);
        
        this.rptAttachmentSvc.saveAttachment(temp, dto);

        List<Map<String, Object>> reportAttachments = this.jdbcTemplate.queryForList(
                "select * from report_attachment", new MapSqlParameterSource());

        assertEquals(1, reportAttachments.size());

    }

    private EmissionsReport createHydratedEmissionsReport() {
    	EmissionsReport er = new EmissionsReport();
		er.setAgencyCode("GA");
		er.setEisProgramId("ABC");
		er.setFrsFacilityId("111111111111");
		er.setId(1L);
		er.setStatus(ReportStatus.APPROVED);
		er.setValidationStatus(ValidationStatus.PASSED);
		er.setYear((short) 2019);
		
    	return er;
    }
}
