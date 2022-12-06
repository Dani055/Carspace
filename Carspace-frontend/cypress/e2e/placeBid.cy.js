describe('Creating a bid', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/login');
        cy.get('#username')
        .type('test')
        cy.get('#password')
        .type('123')
        cy.get('form').submit()
      })
    it('Fails to bid with insufficient amount', () => {
        cy.contains("BMW 330i").click();
        cy.contains("Login successful").click();
        cy.get('#bidAmount').type('1');
        cy.get('form#bidForm').submit()
        cy.get('.Toastify__toast-container').contains("Cannot place bid.").should('be.visible');
    })
    it('Fails to bid with too much amount', () => {
        cy.contains("BMW 330i").click();
        cy.contains("Login successful").click();
        cy.get('#bidAmount').type('500000');
        cy.get('form#bidForm').submit()
        cy.get('.Toastify__toast-container').contains("Cannot place bid.").should('be.visible');
    })
    it('Bids successfully', () => {
        cy.contains("BMW 330i").click();
        cy.contains("Login successful").click();
        cy.get('#bidAmount').type('1500');
        cy.get('form#bidForm').submit()
        cy.get('.Toastify__toast-container').contains("Bid placed").should('be.visible');
        cy.get('.current-bid-details').contains("1500").should('be.visible');
    })
})