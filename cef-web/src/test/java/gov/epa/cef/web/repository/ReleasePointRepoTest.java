package gov.epa.cef.web.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import gov.epa.cef.web.config.CommonInitializers;
import gov.epa.cef.web.domain.ContactTypeCode;
import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.domain.FipsStateCode;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.ProgramSystemCode;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointTypeCode;
import gov.epa.cef.web.domain.UnitMeasureCode;

@SqlGroup(value = {@Sql("classpath:db/test/baseTestData.sql")})
@ContextConfiguration(initializers = {
    CommonInitializers.NoCacheInitializer.class
})
public class ReleasePointRepoTest extends BaseRepositoryTest {
	
	@Autowired
    DataSource dataSource;

    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    ReleasePointRepository rpRepo;
    
    @Autowired
    EmissionsProcessRepository processRepo;

    @Before
    public void _onJunitBeginTest() {

        runWithMockUser();
        
        this.jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
    }
    
    @Test
    public void createControlTest() throws Exception {
    	
    	//delete the all release points from facility 9999992
    	rpRepo.deleteAll(rpRepo.findByFacilitySiteId(9999992L));
    	
    	List<ReleasePoint> releasePtList = rpRepo.findByFacilitySiteId(9999992L);
    	assertEquals(0, releasePtList.size());

    	//create release point
    	ReleasePoint releasePt = newReleasePt();

        this.rpRepo.save(releasePt);

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", releasePt.getId());

        List<Map<String, Object>> releasePts = this.jdbcTemplate.queryForList(
            "select * from release_point where id = :id", params);

        assertEquals(1, releasePts.size());
    }
    
    /**
     * Verify that deleting a release point works and any child apportioned processes
     * are also deleted
     * @throws Exception
     */
    @Test
    public void deletingReleasePoint_should_DeleteChildApportionedProcesses() throws Exception {
        
        //verify the unit and process exist
        Optional<ReleasePoint> releasePoint = rpRepo.findById(9999991L);
        assertEquals(true, releasePoint.isPresent());
        
        List<EmissionsProcess> process = processRepo.findByReleasePointApptsReleasePointId(9999991L);
        assertEquals(true, process.size()>0);
        
        //delete the unit and verify that the processes are gone as well
        rpRepo.deleteById(9999991L);
        
        //verify the unit and process exist
        releasePoint = rpRepo.findById(9999991L);
        assertEquals(false, releasePoint.isPresent());
        
        process = processRepo.findByReleasePointApptsReleasePointId(9999991L);
        assertEquals(0, process.size());
        
    }
    
    private ReleasePoint newReleasePt() {
    	
    	FacilitySite facilitySite = new FacilitySite();
        facilitySite.setId(9999992L);
        
        OperatingStatusCode operatingStatusCode = new OperatingStatusCode();
        operatingStatusCode.setCode("I");
        operatingStatusCode.setDescription("Fugitive");
        
        ProgramSystemCode programSystemCode = new ProgramSystemCode();
        programSystemCode.setCode("63IIII");
        programSystemCode.setDescription("SPPD Rule Category: Auto and Light Duty Trucks: Surface Coating");
        
        UnitMeasureCode distanceUom = new UnitMeasureCode();
        distanceUom.setCode("FT");
        
        UnitMeasureCode velocityUom = new UnitMeasureCode();
        velocityUom.setCode("FT/HR");
        
        UnitMeasureCode flowUom = new UnitMeasureCode();
        flowUom.setCode("FT3/HR");
        
        ReleasePointTypeCode releasePointTypeCode = new ReleasePointTypeCode();
        releasePointTypeCode.setCode("1"); 
        
        ReleasePoint releasePt = new ReleasePoint();
    	
        releasePt.setFacilitySite(facilitySite);
        releasePt.setOperatingStatusCode(operatingStatusCode);
        releasePt.setProgramSystemCode(programSystemCode);
        releasePt.setTypeCode(releasePointTypeCode);
        releasePt.setReleasePointIdentifier("TestRP");
        releasePt.setDescription("Test Description");
        releasePt.setStackHeightUomCode(distanceUom);
        releasePt.setStackDiameterUomCode(distanceUom);
        releasePt.setExitGasVelocityUomCode(velocityUom);
        releasePt.setExitGasFlowUomCode(flowUom);
        releasePt.setLatitude(1111.000000);
        releasePt.setLongitude(1111.000000);

        return releasePt;
    }
       
}
