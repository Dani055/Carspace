import React from "react"
import dayjs from "dayjs";
import { Link } from "react-router-dom";
import { useContext } from "react";
import { UserContext } from "../../UserProvider";

function Comment(props) {
  const { loggedUser } = useContext(UserContext);
    return (
      <div className="bg-light rounded p-3 mb-4">
        <div className="comment-header row">
        <div className="col-auto me-auto">
        <p>
          <Link className="link-dark" to="profile">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="32"
              height="32"
              fill="currentColor"
              class="bi bi-person"
              viewBox="0 0 16 16"
            >
              <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z" />
            </svg>{" "}
            {props.comment.creator.username}
          </Link>{" "}
          commented:
        </p>
        </div>
        {
          (loggedUser?.id === props.comment.creator.id || loggedUser?.role === 'admin') &&
          <div className="col-auto">
            <button className="btn btn-danger ms-auto">Delete</button>
          </div>
        }
        
        </div>    
        <p>{props.comment.text}</p>
        <p className="m-0">
          {dayjs(props.comment.createdOn).format("DD/MM/YYYY HH:MM:ss")}
        </p>
      </div>
    )
}

export default Comment;