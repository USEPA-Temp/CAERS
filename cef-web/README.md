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

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
