import React from "react"


function CommentForm(props) {

    return (
        <div className="place-bid border-top p-3">
        <h6>Leave comment:</h6>
        <form>
          <div class="input-group mb-3">
            <input
              type="text"
              class="form-control"
              placeholder="Your comment here"
              id="commentText"
              name="commentText"
              aria-label="Your comment here"
              aria-describedby="button-comment"
            />
            <button
              class="btn btn-outline-dark mx-1"
              type="submit"
              id="button-comment"
            >
              Post Comment
            </button>
          </div>
        </form>
      </div>
    )
}

export default CommentForm;