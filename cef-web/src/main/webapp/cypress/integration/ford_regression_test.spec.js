// test1.spec.js created with Cypress
//
// Start writing your Cypress tests below!
// If you're unfamiliar with how Cypress works,
// check out the link below and learn how to write your first test:
// https://on.cypress.io/writing-first-test

describe('FORD TESTING SUITE', () => {

  before(function(){
    Cypress.Cookies.debug(true);

    cy.clearCookie('CDX2_ASP.NET_SessionId');
    cy.clearCookie('CDX2AUTH');
    cy.clearCookie('pubCDXSessionEnd');
    cy.clearCookie('XSRF-TOKEN');
    cy.clearCookie('JSESSIONID');

    Cypress.Cookies.defaults({
        preserve: ['CDX2_ASP.NET_SessionId', '.CDX2AUTH', 'pubCDXSessionEnd', 'XSRF-TOKEN', 'JSESSIONID']
    });
    
    cy.fixture('userLogin').then(function(user){
        this.user=user;
    });
  });

  Cypress.Commands.add('login', function(){
    if(Cypress.env('environment') == 'dev') {
      cy.visit(Cypress.env('dev_url'));
    } else if (Cypress.env('environment') == 'test') {
      cy.visit(Cypress.env('test_url'));
    } else if (Cypress.env('environment') == 'prod') {
      cy.visit(Cypress.env('prod_url'));
    }

    cy.get('#LoginUserId').type(this.user.userId);
    cy.get('#LoginPassword').type(this.user.password);

    cy.get('#LogInButton').click();  
    cy.get(':nth-child(1) > .mycdx-role > .handoff').click();
  });



  it('Handoff from CDX to CAER', function(){
  	if(Cypress.env('environment') == 'local') {
  		cy.visit('/');
  	} else {
      cy.login();
  	}
  });

  describe('Basic Copy Test', () => {
    it('Create Report', function() {
      cy.get('#continueReportGADNR12100364').click();
      cy.get('#createNew2020Report').click();
      cy.get('[data-cy="reportSummaryTotals"] > :nth-child(6)').should('contain', '2793.4857256458');
      cy.get('[data-cy="select emissionUnitEmissions Units"] > span').click();
      cy.get('#euSummaryTable > tbody > tr').should(($tr) => {
          expect($tr).to.have.length(6)
      });
      cy.get('[data-cy="select releaseRelease Points"] > span').click();
      cy.get('#rpSummaryTable > tbody > tr').should(($tr) => {
          expect($tr).to.have.length(6)
      });
      cy.get('[data-cy="select controlControl Devices"] > span').click();
      cy.get('#cdSummaryTable > tbody > tr').should(($tr) => {
          expect($tr).to.have.length(6)
      });
      cy.get('[data-cy="select pathControl Paths"] > span').click();
      cy.get('#cpSummaryTable > tbody > tr').should(($tr) => {
          expect($tr).to.have.length(6)
      });
    });

    it('Edit Facility Info', function() {
      cy.get('[data-cy="select facilityInformationFacility Information"] > span').click();
      cy.get('app-facility-information > :nth-child(1) > .card-header > .float-right > .btn').click();
      cy.get('#facilityStatusCodeSelect').select('1: Object');
      cy.get('#statusYearInput').clear();
      cy.get('#statusYearInput').type('2017');
      cy.get('#facilityBIACodeSelect').select('1: Object');
      cy.get('#mailingStreetAddressInput').clear();
      cy.get('#mailingStreetAddressInput').type('test street');
      cy.get('#mailingPostalCodeInput').clear();
      cy.get('#mailingPostalCodeInput').type('30353');
      cy.get('#mailingCityInput').clear();
      cy.get('#mailingCityInput').type('Test');
      cy.get('#mailingStateCodeSelect').select('13: Object');
      cy.get('#descriptionInput').clear();
      cy.get('#descriptionInput').type('New Cypress Description');
      cy.get('#facilityCommentsInput').click();
      cy.get('.card-body > :nth-child(1) > .float-right > .btn-success').click();
    });

    it('Add Facility NAICS', function() {
      cy.get('#addNaicsBtn').click();
      cy.get('#facilityNAICS').clear();
      cy.get('.d-block').click();
      cy.get('#facilityNAICS').clear();
      cy.get('#facilityNAICS').type('auto');
      cy.get('#ngb-typeahead-0-6 > ngb-highlight').click();
      cy.get('.modal-footer > .btn-success').click();
      cy.get('#deleteNaics811111').click();
      cy.get('#modalConfirmBtn').click();
    });

    it('Create Facility Contact', function() {
      cy.get('.pb-3 > .btn').click();
      cy.get('#type').select('13: Object');
      cy.get('#firstName').clear();
      cy.get('#firstName').type('Cypress');
      cy.get('#lastName').clear();
      cy.get('#lastName').type('Test');
      cy.get('#phone').clear();
      cy.get('#phone').type('5555555555');
      cy.get('#phoneExt').clear();
      cy.get('#phoneExt').type('555');
      cy.get('#email').clear();
      cy.get('#email').type('test@example.com');
      cy.get('#streetAddress').clear();
      cy.get('#streetAddress').type('Cypress');
      cy.get('#city').clear();
      cy.get('#city').type('Cypress');
      cy.get('#stateCode').select('28: Object');
      cy.get('#stateCode').select('34: Object');
      cy.get('#countySelect').select('122: Object');
      cy.get('#countySelect').select('125: Object');
      cy.get('#countySelect').select('126: Object');
      cy.get('#postalCode').clear();
      cy.get('#postalCode').type('27707');
      cy.get('#mailingSameAsContactAddress').check();
      cy.get('.btn-success').click();
      cy.get('[data-cy="deleteContactCypressTest"]').click();
      cy.get('#modalConfirmBtn').click();
    });

    it('Create Emissions Unit', function() {
      cy.get('[data-cy="select emissionUnitEmissions Units"] > span').click();
      cy.get('#tblAddEmissionsUnitBtn > .ng-fa-icon > .svg-inline--fa').click();
      cy.get('#unitIdentifierInput').clear();
      cy.get('#unitIdentifierInput').type('Cypress');
      cy.get('#unitStatusCodeSelect').select('2: Object');
      cy.get('#unitTypeCodeSelect').select('3: Object');
      cy.get('#unitStatusYearInput').clear();
      cy.get('#unitStatusYearInput').type('1999');
      cy.get('#descriptionInput').clear();
      cy.get('#descriptionInput').type('Cypress Test Uni');
      cy.get('#unitDesignCapacityInput').clear();
      cy.get('#unitDesignCapacityInput').type('10000');
      cy.get('#unitOfMeasureCodeSelect').select('5: Object');
      cy.get('#unitCommentsInput').clear();
      cy.get('#unitCommentsInput').type('Cypress Test Commen');
      cy.get('.btn-success').click();
    });

    it('Edit Emissions Unit', function() {
      cy.get('[data-cy="emissions unitCypress"]').click();
      cy.get('.float-right > .btn').click();
      cy.get('#unitStatusCodeSelect').select('1: Object');
      cy.get('#unitTypeCodeSelect').select('1: Object');
      cy.get('#unitStatusYearInput').clear();
      cy.get('#unitStatusYearInput').type('2020');
      cy.get('#descriptionInput').clear();
      cy.get('#descriptionInput').type('Cypress Test Unit');
      cy.get('#unitDesignCapacityInput').clear();
      cy.get('#unitDesignCapacityInput').type('1000');
      cy.get('#unitOfMeasureCodeSelect').select('6: Object');
      cy.get('#unitCommentsInput').clear();
      cy.get('#unitCommentsInput').type('Cypress Test Comment');
      cy.get('.btn-success').click();
    });

    it('Create Process', function() {
      cy.get('#tblAddProcessBtn > .ng-fa-icon > .svg-inline--fa > path').click();
      cy.get('#processIdentifierInput').clear();
      cy.get('#processIdentifierInput').type('Cypress 1');
      cy.get('#processStatusCodeSelect').select('1: Object');
      cy.get('#processDescriptionInput').clear();
      cy.get('#processDescriptionInput').type('Cypress Test Process 1');
      cy.get('#processStatusYearInput').clear();
      cy.get('#processStatusYearInput').type('2020');
      cy.get('#openSccSearchModalBtn').click();
      cy.get('#pollutantNameInput').clear();
      cy.get('#pollutantNameInput').type('waste');
      cy.get('#sccSearchButton').click();
      cy.get('#selectScc10100901').click();
      cy.get('#odDaysPerWeekInput').clear();
      cy.get('#odDaysPerWeekInput').type('7');
      cy.get('#odHoursPerPeriodInput').clear();
      cy.get('#odHoursPerPeriodInput').type('8784');
      cy.get('#odHoursPerDayInput').clear();
      cy.get('#odHoursPerDayInput').type('24');
      cy.get('#odWeeksPerPeriodInput').clear();
      cy.get('#odWeeksPerPeriodInput').type('52');
      cy.get('#odPercentSummerInput').clear();
      cy.get('#odPercentSummerInput').type('25');
      cy.get('#odPercentWinterInput').clear();
      cy.get('#odPercentWinterInput').type('25');
      cy.get('#odPercentSpringInput').clear();
      cy.get('#odPercentSpringInput').type('25');
      cy.get('#odPercentFallInput').clear();
      cy.get('#odPercentFallInput').type('25');
      cy.get('#rpOperatingCodeSelect').select('1: Object');
      cy.get('#rpParamSelect').select('1: Object');
      cy.get('#rpMaterialSelect').select('1: Object');
      cy.get('#rpValueInput').clear();
      cy.get('#rpValueInput').type('1000');
      cy.get('#rpUomSelect').select('1: Object');
      cy.get('.float-right > .btn-success').click();

    });

    it('Create Emission', function() {
      cy.get('[data-cy="emissions processCypress 1"]').click();
      cy.get('#tblAddEmissionBtn').click();
      cy.get('#pollutantSelect').clear();
      cy.get('#pollutantSelect').type('nox');
      cy.get('#ngb-typeahead-1-2 > ngb-highlight').click();
      cy.get('#emissionsCalcMethodCodeSelect').select('1: Object');
      cy.get('#overallControlPercentInput').clear();
      cy.get('#overallControlPercentInput').type('0');
      cy.get('#totalEmissionsInput').clear();
      cy.get('#totalEmissionsInput').type('15');
      cy.get('#emissionsUomCodeSelect').select('8: Object');
      cy.get('.btn-success').click();
    });

    it('Create Release Points', function() {
      cy.get('[data-cy="select releaseRelease Points"] > span').click();
      cy.get('#tblAddReleasePointBtn > .ng-fa-icon > .svg-inline--fa > path').click();
      cy.get('#releasePointIdentifier').clear();
      cy.get('#releasePointIdentifier').type('CypressStack');
      cy.get('#releaseStatusCodeSelect').select('1: Object');
      cy.get('#description').clear();
      cy.get('#description').type("Cypress Stack Desc");
      cy.get('#statusYear').clear();
      cy.get('#statusYear').type('2020');
      cy.get('#releaseTypeCodeSelect').select('5: Object');
      cy.get('#exitGasVelocity').clear();
      cy.get('#exitGasVelocity').type('66');
      cy.get('#exitGasVelocityUomCodeSelect').select('1: Object');
      cy.get('#exitGasFlowRate').clear();
      cy.get('#exitGasFlowRate').type('5183.627');
      cy.get('#exitGasFlowUomCodeSelect').select('1: Object');
      cy.get('#exitGasTemperature').clear();
      cy.get('#exitGasTemperature').type('100');
      cy.get('#stackHeight').clear();
      cy.get('#stackHeight').type('100');
      cy.get('#stackDiameter').clear();
      cy.get('#stackDiameter').type('10');
      cy.get('.btn-success').click();

      cy.get('#tblAddReleasePointBtn > .ng-fa-icon > .svg-inline--fa > path').click();
      cy.get('#releasePointIdentifier').clear();
      cy.get('#releasePointIdentifier').type('CypressFugitive');
      cy.get('#releaseStatusCodeSelect').select('1: Object');
      cy.get('#description').clear();
      cy.get('#description').type("Cypress Fugitive Desc");
      cy.get('#statusYear').clear();
      cy.get('#statusYear').type('2020');
      cy.get('#releaseTypeCodeSelect').select('2: Object');
      cy.get('#latitude').clear();
      cy.get('#latitude').type('33.65332');
      cy.get('#longitude').clear();
      cy.get('#longitude').type('-84.4012');
      cy.get('.btn-success').click();

      cy.get('[data-cy="release pointCypressFugitive"]').click();
      cy.get('#editReleasePointBtn').click();
      cy.get('#description').clear();
      cy.get('#description').type("Cypress Fugitive Description Edit");
      cy.get('#longitude').clear();
      cy.get('#longitude').type('-84.4011');
      cy.get('.btn-success').click();
    });

    it('Create Control Device', function() {
      cy.get('[data-cy="select controlControl Devices"] > span').click();
      cy.get('#tblAddControlDeviceBtn > .ng-fa-icon > .svg-inline--fa').click();
      cy.get('#controlIdentifierInput').clear();
      cy.get('#controlIdentifierInput').type('Cypress Control 1');
      cy.get('#controlMeasureCodeSelect').select('1: Object');
      cy.get('#controlStatusCodeSelect').select('1: Object');
      cy.get('#controlStatusYearInput').clear();
      cy.get('#controlStatusYearInput').type('2021');
      cy.get('#descriptionInput').clear();
      cy.get('#descriptionInput').type('Cypress Test Control 1');
      cy.get('#numberOperatingMonths').clear();
      cy.get('#numberOperatingMonths').type('10');
      cy.get('#percentControlInput').clear();
      cy.get('#percentControlInput').type('85.5');
      cy.get('#startDate').clear();
      cy.get('#startDate').type('2019-01-01');
      cy.get('#upgradeDate').clear();
      cy.get('#upgradeDate').type('2019-06-01');
      cy.get('#endDate').clear();
      cy.get('#endDate').type('2020-12-31');
      cy.get('#upgradeDescription').clear();
      cy.get('#upgradeDescription').type('Description.');
      cy.get('#controlCommentsInput').clear();
      cy.get('#controlCommentsInput').type('Comments.');
      cy.get('.btn-success').click();
    });

    it('Create Control Pollutant', function() {
      cy.get('[data-cy="control deviceCypress Control 1"]').click();
      cy.get('#tblAddControlPollutantBtn > .ng-fa-icon > .svg-inline--fa').click();
      cy.get('#pollutantSelect').clear();
      cy.get('#pollutantSelect').type('nox');
      cy.get('#ngb-typeahead-2-2 > ngb-highlight').click();
      cy.get('#percentReductionSelect').clear();
      cy.get('#percentReductionSelect').type('80');
      cy.get('.modal-footer > .btn-success').click();
    });

    it('Edit Control Pollutant', function() {
      cy.get('[data-cy="edit control pollutantNOX"]').click();
      cy.get('#percentReductionSelect').clear();
      cy.get('#percentReductionSelect').type('75');
      cy.get('.modal-footer > .btn-success').click();
    });

    it('Edit Control Device', function() {
      cy.get('.float-right > .btn').click();
      cy.get('#controlStatusYearInput').clear();
      cy.get('#controlStatusYearInput').type('2020');
      cy.get('#upgradeDate').clear();
      cy.get('#upgradeDate').type('2020-01-01');
      cy.get('#upgradeDescription').clear();
      cy.get('#upgradeDescription').type('Description of control device upgraded.');
      cy.get('#controlCommentsInput').clear();
      cy.get('#controlCommentsInput').type('Comments about the control device.');
      cy.get('.btn-success').click();
    });

    it('Create Control Path', function() {
      cy.get('[data-cy="select pathControl Paths"] > span').click();
      cy.get('#tblAddControlPathBtn > .ng-fa-icon > .svg-inline--fa').click();
      cy.get('#controlPathIdInput').clear();
      cy.get('#controlPathIdInput').type('Cypress CP 1');
      cy.get('#percentControlInput').clear();
      cy.get('#percentControlInput').type('85');
      cy.get('#descriptionInput').clear();
      cy.get('#descriptionInput').type('Cypress Test');
      cy.get('.btn-success').click();
    });

    it('Create Control Path Pollutant', function() {
      cy.get('[data-cy="control pathCypress CP 1"]').click();
      cy.get('#tblAddControlPathPollutantBtn > .ng-fa-icon > .svg-inline--fa').click();
      cy.get('#pollutantSelect').clear();
      cy.get('#pollutantSelect').type('nox');
      cy.get('#ngb-typeahead-4-2 > ngb-highlight').click();
      cy.get('#percentReductionSelect').clear();
      cy.get('#percentReductionSelect').type('25');
      cy.get('.modal-footer > .btn-success').click();
    });

    it('Create Control Path Assignment', function() {
      cy.get('#tblAddControlPathAssignmentBtn > .ng-fa-icon > .svg-inline--fa').click();
      cy.get('#sequenceNumberInput').clear();
      cy.get('#sequenceNumberInput').type('1');
      cy.get('#controlSelect').select('6: Object');
      cy.get('#percentInput').clear();
      cy.get('#percentInput').type('50');
      cy.get('.modal-footer > .btn-success').click();
    });

    it('Edit Control Path', function() {
      cy.get('.float-right > .btn').click();
      cy.get('#percentControlInput').clear();
      cy.get('#percentControlInput').type('80.5');
      cy.get('#descriptionInput').clear();
      cy.get('#descriptionInput').type('Cypress test control path Cypress CP 1.');
      cy.get('.btn-success').click();
    });

    it('Edit Control Path Pollutant', function() {
      cy.get('[data-cy="edit control pollutantNOX"]').click();
      cy.get('#percentReductionSelect').clear();
      cy.get('#percentReductionSelect').type('75');
      cy.get('.modal-footer > .btn-success').click();
    });

    it('Edit Control Path Assignment', function() {
      cy.get('[data-cy="edit control path assignment sequence number1"]').click();
      cy.get('#controlSelect').select('0: null');
      cy.get('#controlPathSelect').select('1: Object');
      cy.get('#percentInput').clear();
      cy.get('#percentInput').type('85');
      cy.get('.modal-footer > .btn-success').click();
    });

    it('Delete Report', function() {
      cy.get('[data-cy="bcMy Facilities"]').click();
      cy.get('#continueReportGADNR12100364').click();
      cy.get('#delete2020Report').click();
      cy.get('app-confirmation-dialog > .modal-body').click();
      cy.get('#modalConfirmBtn').click();
    });
  });
})

