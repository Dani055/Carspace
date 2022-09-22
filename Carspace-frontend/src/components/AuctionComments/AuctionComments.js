import React from "react"

import { Link } from "react-router-dom";
import CommentForm from "../forms/CommentForm/CommentForm";
import Comment from "../Comment/Comment";
function AuctionComments(props) {

    return (
      <div className="comments p-3 mt-2">
      <h4>Comments (14)</h4>
      <CommentForm/>
      <Comment/>
      <Comment/>
      <Comment/>
    </div>   
    )
}

export default AuctionComments;