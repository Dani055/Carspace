
describe('Auction details page', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/auction/details/1')
      })
    it('Hides UI when not logged in', () => {
      cy.get('form.bidForm').should('not.exist');
      cy.get('form.commentForm').should('not.exist');
      cy.get('form.commentForm').should('not.exist');
      cy.get('button').contains('Delete auction').should('not.exist');
      cy.get('button').contains('Edit auction').should('not.exist');
    })

})