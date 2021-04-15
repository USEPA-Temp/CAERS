# CefWeb Cypress Testing

To start Cypress, you will want to run `./node_modules/.bin/cypress open` from the webapp directory of this project

## Running Locally

You will need to have your local instance of CAER running for Cypress to work locally. After CAER is running, make sure that the `local` property in `cypress.json` is set to true and any Cypress tests that work locally should be functional (you might need to make sure Ford Motor Co doesn't have a 2020 year report).

## Running in DEV

Make sure that the `local` property in `cypress.json` is set to false. If Cypress is using Chrome as it's browser you might need to set the `chrome://flags/#same-site-by-default-cookies` and `chrome://flags/#cookies-without-same-site-must-be-secure` to disabled in the Cypress browser. Firefox appears to work by default at time of writing.
