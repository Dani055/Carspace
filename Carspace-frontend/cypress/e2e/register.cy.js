describe('Registering', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/register')
      })
    it('Fails to register with garbage input', () => {
        cy.get('form').submit()
        cy.url().should('include', '/register')
    })
    it('Registers with correct info', () => {
        cy.get('#firstName').type('John')
        cy.get('#lastName').type('Doe')
        cy.get('#address').type('Avenue 123')
        cy.get('#phone').type('+3111111')
        cy.get('#email').type('jdoe@mail.com')
        cy.get('#username').type('jdoe')
        cy.get('#password').type('123')
        cy.get('form').submit();
        cy.url().should('include', '/login')
    })
})