package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CommonInitializers;   
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.FacilitySite;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@SqlGroup(value = {@Sql("classpath:db/test/baseTestData.sql")})
@ContextConfiguration(initializers = {
    CommonInitializers.NoCacheInitializer.class
})
public class ControlPathRepositoryTest extends BaseRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    ControlPathRepository repository;
    
    @Before
    public void _onJunitBeginTest() {

        runWithMockUser();

        this.jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
    }

    @Test
    public void createControlPathTest() throws Exception {

        ControlPath controlPath = newControlPathInstance();

        this.repository.save(controlPath);

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", controlPath.getId());

        List<Map<String, Object>> controls = this.jdbcTemplate.queryForList(
            "select * from control_path where id = :id", params);

        assertEquals(1, controls.size());
    }

    @Test
    public void deleteControlPathTest() throws Exception {

        ControlPath controlPath = this.repository.findById(9999992L)
            .orElseThrow(() -> new IllegalStateException("Control 9999992L does not exist."));
        
        this.repository.delete(controlPath);

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", controlPath.getId());

        List<Map<String, Object>> controlPaths = this.jdbcTemplate.queryForList(
            "select * from control_path where id = :id", params);

        assertTrue(controlPaths.isEmpty());
                
    }

    @Test
    public void updateControlPathTest() throws Exception {

        ControlPath controlPath = this.repository.findById(9999992L)
            .orElseThrow(() -> new IllegalStateException("Control 9999992L does not exist."));

        String description = String.format("%s new description", UUID.randomUUID().toString());

        controlPath.setDescription(description);
        this.repository.save(controlPath);

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", controlPath.getId());

        List<Map<String, Object>> controls = this.jdbcTemplate.queryForList(
            "select * from control_path where id = :id", params);

        assertEquals(1, controls.size());

        assertEquals(description, controls.get(0).get("description"));
    }


    private ControlPath newControlPathInstance() {

        FacilitySite facilitySite = new FacilitySite();
        facilitySite.setId(9999991L);


        ControlPath controlPath = new ControlPath();
        controlPath.setDescription("Totally non-descriptive description.");
        controlPath.setFacilitySite(facilitySite);
        controlPath.setCreatedBy("JUNIT-TEST");
        controlPath.setCreatedDate(new Date());
        controlPath.setLastModifiedBy(controlPath.getCreatedBy());
        controlPath.setLastModifiedDate(controlPath.getCreatedDate());
        controlPath.setPathId("TEST PATH");
        
        return controlPath;
    }
}