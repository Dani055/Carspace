describe('Logging in', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/login')
      })
    it('Fails to log in with garbage input', () => {
        cy.get('.btn-outline-dark').contains("Login").click();
        cy.url().should('include', '/login')
    })
    it('Fails to log in with incorrect info', () => {
        cy.get('#username')
        .type('ad')
        cy.get('#password')
        .type('maikati')
        cy.get('.btn-outline-dark').contains("Login").click();
        cy.url().should('include', '/login')
    })
    it('Logs in with correct info', () => {
        cy.get('#username')
        .type('admin')
        cy.get('#password')
        .type('123')
        cy.get('.btn-outline-dark').contains("Login").click();
        cy.url().should('eq', 'http://localhost:3000/');
    })
})