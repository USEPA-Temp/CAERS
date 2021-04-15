describe('FORD TESTING SUITE', function(){
    
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
        cy.visit('https://dev.epacdx.net/')
        cy.get('#LoginUserId')
          .type(this.user.userId)
      
        cy.get('#LoginPassword')
          .type(this.user.password)

        cy.get('#LogInButton').click();  
        cy.get(':nth-child(1) > .mycdx-role > .handoff').click();
    });
    it('Handoff from CDX to CAER', function(){
        cy.login();
    });

    describe('FORD TESTING SUITE', function(){
        it('test', function(){
            cy.get('[aria-label="begin or continue reporting forFord Motor Company 20102021"]').click();
            cy.wait(1000);
        });
    });
});

