package gov.epa.cef.web.config;

public class TestCategories {

    public static class UnitTest {    };

    public static class SlowTest extends UnitTest {    };

    public static class FastTest extends UnitTest {    };

    public static class EmbeddedDatabaseTest extends SlowTest {    };

    public static class IntegrationTest {  };
}
