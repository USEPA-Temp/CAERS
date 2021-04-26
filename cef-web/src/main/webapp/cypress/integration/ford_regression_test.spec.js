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
        preserve: ['CDX2_ASP.NET_SessionId', '.CDX2AUTH', 'pubCDXSessionEnd']
    });
    
    cy.fixture('userLogin').then(function(user){
        this.user=user;
    });
  });

  Cypress.Commands.add('login', function(){
    cy.visit(Cypress.env('login_url'));

    cy.get('#LoginUserId').type(this.user.userId);
    cy.get('#LoginPassword').type(this.user.password);

    cy.get('#LogInButton').click();  
    cy.get(':nth-child(1) > .mycdx-role > .handoff').click();
  });



  it('Handoff from CDX to CAER', function(){
  	if(Cypress.env('local')) {
  		cy.visit('/');
  	} else {
      cy.login();
  	}
  });

  describe('Basic Copy Test', () => {
    it('Create Report', function() {
      cy.get('#continueReportGADNR12100364').click();
      cy.get('#createNew2020Report').click();
    });

    it('Create Emissions Unit', function() {
      cy.get('[data-cy="select emissionUnitEmissions Units"] > span').click();
      cy.get('#tblAddEmissionsUnitBtn > .ng-fa-icon > .svg-inline--fa').click();
      cy.get('#unitIdentifierInput').clear();
      cy.get('#unitIdentifierInput').type('Cypress');
      cy.get('#unitStatusCodeSelect').select('1: Object');
      cy.get('#unitTypeCodeSelect').select('1: Object');
      cy.get('#unitStatusYearInput').clear();
      cy.get('#unitStatusYearInput').type('2020');
      cy.get('#descriptionInput').clear();
      cy.get('#descriptionInput').type('Cypress Test Unit');
      cy.get('.btn-success').click();

    });

    it('Create Process', function() {
      cy.get('[data-cy="emissions unitCypress"]').click();
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
      cy.get('#ngb-typeahead-0-2 > ngb-highlight').click();
      cy.get('#emissionsCalcMethodCodeSelect').select('1: Object');
      cy.get('#overallControlPercentInput').clear();
      cy.get('#overallControlPercentInput').type('0');
      cy.get('#totalEmissionsInput').clear();
      cy.get('#totalEmissionsInput').type('15');
      cy.get('#emissionsUomCodeSelect').select('8: Object');
      cy.get('.btn-success').click();
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

