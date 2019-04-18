package gov.epa.cef.web.hibernate;

import org.hibernate.dialect.PostgreSQL95Dialect;

import java.sql.Types;

/**
 * Custom dialect for PostgreSQL that adds support for jsonb type.
 *
 * @author dfladung
 */
public class PostgreSQLDialectWithJsonSupport extends PostgreSQL95Dialect {

    public PostgreSQLDialectWithJsonSupport() {
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }
}
