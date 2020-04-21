package gov.epa.cef.web.provider.system;

import com.google.common.base.Ascii;
import com.google.common.base.Splitter;

import gov.epa.cef.web.config.CacheName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyProvider {

    private final String deleteSql;

    private final String insertSql;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String listSql;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String selectSql;

    private final String sqlCount;


    private final String updateSql;

    public PropertyProvider(NamedParameterJdbcTemplate jdbcTemplate,
                            String tableName) {

        super();
        this.jdbcTemplate = jdbcTemplate;

        this.insertSql = String.format("insert into %s (name, value) values (:name, :value)", tableName);
        this.updateSql = String.format("update %s set value = :value where name = :name ", tableName);
        this.deleteSql = String.format("delete from %s where name = :name ", tableName);
        this.selectSql = String.format("select value from %s where name = :name ", tableName);
        this.listSql = String.format("select name, value from %s ", tableName);
        this.sqlCount = String.format("select count(1) from %s where name = :name ", tableName);
    }

    public boolean create(IPropertyKey propertyKey, String value) {

        String name = Ascii.truncate(propertyKey.configKey(), 64, "");

        logger.info("Creating system property '{}' = '{}'", name, value);

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", name)
            .addValue("value", value);

        int count = this.jdbcTemplate.update(this.insertSql, params);

        return count == 1;
    }

    public boolean delete(IPropertyKey propertyKey) {

        String name = propertyKey.configKey();

        logger.info("Deleting system property '{}'", name);

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", name);

        int count = this.jdbcTemplate.update(this.deleteSql, params);

        return count == 1;
    }

    @Cacheable(value = CacheName.PropertyBigDecimal)
    public BigDecimal getBigDecimal(IPropertyKey propertyKey) {

        String name = propertyKey.configKey();

        SqlParameterSource params = new MapSqlParameterSource().addValue("name", name);

        return this.jdbcTemplate.queryForObject(this.selectSql, params, BigDecimal.class);
    }

    @Cacheable(value = CacheName.PropertyBoolean)
    public boolean getBoolean(IPropertyKey propertyKey, boolean defvalue) {

        boolean result = defvalue;

        String name = propertyKey.configKey();

        if (exists(name)) {

            SqlParameterSource params = new MapSqlParameterSource().addValue("name", name);

            result = this.jdbcTemplate.queryForObject(this.selectSql, params, Boolean.class);
        }

        return result;
    }

    public Integer getInt(IPropertyKey propertyKey) {

        String name = propertyKey.configKey();

        SqlParameterSource params = new MapSqlParameterSource().addValue("name", name);

        return this.jdbcTemplate.queryForObject(this.selectSql, params, Integer.class);
    }

    public Integer getInt(IPropertyKey propertyKey, Integer defvalue) {

        Integer result = defvalue;

        String name = propertyKey.configKey();

        if (exists(name)) {

            SqlParameterSource params = new MapSqlParameterSource().addValue("name", name);

            result = this.jdbcTemplate.queryForObject(this.selectSql, params, Integer.class);
        }

        return result;
    }

    public Long getLong(IPropertyKey propertyKey) {

        String name = propertyKey.configKey();

        SqlParameterSource params = new MapSqlParameterSource().addValue("name", name);

        return this.jdbcTemplate.queryForObject(this.selectSql, params, Long.class);
    }

    public Long getLong(IPropertyKey propertyKey, Long defvalue) {

        Long result = defvalue;

        String name = propertyKey.configKey();

        if (exists(name)) {

            SqlParameterSource params = new MapSqlParameterSource().addValue("name", name);

            result = this.jdbcTemplate.queryForObject(this.selectSql, params, Long.class);
        }

        return result;
    }

    @Cacheable(value = CacheName.PropertyString)
    public String getString(IPropertyKey propertyKey) {

        String name = propertyKey.configKey();

        SqlParameterSource params = new MapSqlParameterSource().addValue("name", name);

        return this.jdbcTemplate.queryForObject(this.selectSql, params, String.class);
    }

    @Cacheable(value = CacheName.PropertyString)
    public String getString(IPropertyKey propertyKey, String defvalue) {

        String result = defvalue;

        String name = propertyKey.configKey();

        if (exists(name)) {

            SqlParameterSource params = new MapSqlParameterSource().addValue("name", name);

            result = this.jdbcTemplate.queryForObject(this.selectSql, params, String.class);
        }

        return result;
    }

    @Cacheable(value = CacheName.PropertyStringList)
    public List<String> getStringList(IPropertyKey propertyKey) {

        String csv = this.getString(propertyKey);

        List<String> result = Splitter.on(",").trimResults().splitToList(csv);

        return result;
    }

    public URL getUrl(IPropertyKey propertyKey) {

        String name = propertyKey.configKey();

        SqlParameterSource params = new MapSqlParameterSource().addValue("name", name);

        String strurl = this.jdbcTemplate.queryForObject(this.selectSql, params, String.class);

        URL result = null;
        try {

            result = new URL(strurl);

        } catch (MalformedURLException e) {

            String msg = String.format("URL '%s' is not a valid URL in ['%s'].", strurl, this.selectSql);
            throw new IllegalStateException(msg, e);
        }

        return result;
    }

    public Map<String, String> retrieveAll() {

        return this.jdbcTemplate.query(this.listSql, rse -> {

            Map<String, String> map = new HashMap<>();

            while (rse.next()) {

                map.put(rse.getString("name"), rse.getString("value"));
            }

            return map;
        });
    }

    public boolean update(IPropertyKey propertyKey, String value) {

        String name = propertyKey.configKey();

        logger.info("Updating system property '{}' = '{}'", name, value);

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", name)
            .addValue("value", value);

        int count = this.jdbcTemplate.update(this.updateSql, params);

        return count == 1;
    }

    private boolean exists(String name) {

        SqlParameterSource params = new MapSqlParameterSource().addValue("name", name);

        return this.jdbcTemplate.queryForObject(sqlCount, params, Integer.class) > 0;
    }
}
