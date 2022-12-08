describe('Creating a bid', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/login');
        cy.get('#username')
        .type('test')
        cy.get('#password')
        .type('123')
        cy.get('form').submit()
      })
    it('Fails to create comment with no input', () => {
        cy.contains("BMW 330i").click();
        cy.get('form.commentForm').submit()
        cy.get('.Toastify__toast-container').contains("Comment length").should('be.visible');
    })
    it('Comment successfully', () => {
        cy.contains("BMW 330i").click();
        cy.contains("Login successful").click();
        cy.get('#commentText').type('test comment');
        cy.get('form.commentForm').submit()
        cy.get('.Toastify__toast-container').contains("Comment created").should('be.visible');
        cy.reload()
        cy.get('.comment').contains("test comment");
        cy.get('.comment button').contains("Delete");
    })
})