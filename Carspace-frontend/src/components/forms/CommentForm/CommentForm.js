import React from "react"
import { useState } from "react";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";
import { createCommentCall } from "../../../service/commentService";
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';

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
      const res = await createCommentCall({ text }, params.auctionId);
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
          <Tooltip className="tooltipsize" title={<Typography fontSize={14}>Post comment</Typography>} placement="top-start">
          <button
            className="btn px-4 btn-outline-dark mx-1"
            type="submit"
            id="button-comment"
          >
            <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor" class="bi bi-send" viewBox="0 0 16 16">
              <path d="M15.854.146a.5.5 0 0 1 .11.54l-5.819 14.547a.75.75 0 0 1-1.329.124l-3.178-4.995L.643 7.184a.75.75 0 0 1 .124-1.33L15.314.037a.5.5 0 0 1 .54.11ZM6.636 10.07l2.761 4.338L14.13 2.576 6.636 10.07Zm6.787-8.201L1.591 6.602l4.339 2.76 7.494-7.493Z" />
            </svg>
          </button>
          </Tooltip>
        </div>
      </form>
    </div>
  )
}

export default CommentForm;