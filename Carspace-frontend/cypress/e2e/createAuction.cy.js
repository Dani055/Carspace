describe('Creating an auction', () => {
    before(() => {
        cy.visit('http://localhost:3000/login');
        cy.get('#username')
        .type('admin')
        cy.get('#password')
        .type('123')
        cy.get('form').submit()
      })
    it('Fails to create auction with garbage input', () => {
        cy.contains("Sell a car").click();
        cy.get('form').submit();
        cy.url().should('eq', 'http://localhost:3000/auction/create');
    })
})