import React from "react"
import { useState } from "react";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";
import { createCommentCall } from "../../../service/commentService";

function CommentForm(props) {
  const params = useParams();
  const [text, setText] = useState("");

  const handleFormChange = (event) => {
    setText(event.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    e.preventDefault();
    try {
      const res = await createCommentCall({text}, params.auctionId);
      toast.success(res.message);
    } catch (error) {
      console.log(error);
      toast.error("Comment length must be at least 5");
    }
  };
    return (
        <div className="place-bid border-top p-3">
        <h6>Leave comment:</h6>
        <form className="commentForm" onSubmit={handleSubmit}>
          <div className="input-group mb-3">
            <input
              type="text"
              className="form-control"
              placeholder="Your comment here"
              id="commentText"
              name="commentText"
              onChange={handleFormChange}
              aria-label="Your comment here"
              aria-describedby="button-comment"
            />
            <button
              className="btn btn-outline-dark mx-1"
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