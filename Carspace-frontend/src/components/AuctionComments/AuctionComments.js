import React from "react"
import CommentForm from "../forms/CommentForm/CommentForm";
import Comment from "../Comment/Comment";

import { useContext } from "react";
import { UserContext } from "../../UserProvider";
import dayjs from "dayjs";

function AuctionComments(props) {
    const { loggedUser } = useContext(UserContext);

    const displayCommentForm = () => {
      if (loggedUser !== null) {
        if (dayjs().isBefore(props.auction.endsOn)) {
          return <CommentForm />
        }
      }
    };

    return (
      <div className="comments p-3 mt-2">
      <h4>Comments ({props.comments.length})</h4>
      {
        displayCommentForm()
      }
      {props.comments?.map((comment) => {
            return <Comment key={comment.id} comment={comment}/>
      })}
    </div>   
    )
}

export default AuctionComments;