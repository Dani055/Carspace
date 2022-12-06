
describe('Getting auctions', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000')
      })
    it('Gets the one running auction in system', () => {
      cy.get('.card').contains("BMW 330i")
      cy.get('.alert-success').contains("Live")
    })
    it('Gets the one ended auction in system', () => {
      cy.get('.nav-item.dropdown').click()
      cy.get('.dropdown-item').contains("Ended auctions").click()
      cy.get('.card').contains("Mercedes 300E")
      cy.get('.alert-danger').contains("ended")
    })
})