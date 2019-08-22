# CefWeb

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 7.3.8. The generated code lives in `src/main/webapp`

## Development server

Run `mvn spring-boot:run` Navigate to `http://localhost:8080/cef-web`. The app will automatically reload if you change any of the source files.

You need to open a command line and navigate to your project and to `src/main/webapp` and run `ng build --watch` to continuously build the Angular TypeScript code while developing and reloading it.

## Run locally on Tomcat
Make sure to edit the Tomcat eclipse server and modify the launch configuration and add `-Dspring.profiles.active=dev` this helps the server set the right spring profile when starting the app.
Make sure you have the Dynamic Web Module facet is enabled so it keeps synching your web resources to the server.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `target/www/` directory. Use the `--prod` flag for a production build.
This is automatically done by Maven frontend plugin in the pom.xml.

## Running unit tests

### Java
```
mvn test
mvn -P allTests test
```
Unit tests can be categorized using JUnit `@Category` annotation. 
The categories and their hierarchy are in `TestCategories.java`. The categories are used
in pom.xml in the Surefire plugin to segregate tests in maven profiles.

Units test annotated with `@Category(TestCategories.FastTest.class)` 
are run by default; `mvn test`.

All unit tests can be run by adding a profile, `-P allTests`.

### Embedded Database Tests

Annotating the Test Class with the following will start up a postgres embedded database 
and replace the dataSource created by Spring Boot with a datasource that points to the
embedded database. All the initialization that Spring Boot, including Flyway, will use the
embedded database. 
```
@Category(TestCategories.EmbeddedDatabaseTest.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase
```

#### Test Preparation

Concepts can be found here `https://reflectoring.io/spring-boot-data-jpa-test/`

The embedded database is shared in the scope of the @SpringBootTest. This can cause
test method conflicts or dependencies if the database is not "cleansed" before each 
test method.

The use of `@SqlGroup(value = {@Sql("classpath:db/test/controlRepositoryITCase-1.sql")})` 
to prepare the database before each @Test method is straight forward. 
The annotation can be at the Class or Method level. The SQL is run before each @Test method.

### Angular
Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
