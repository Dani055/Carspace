import React from "react";

import {Link} from "react-router-dom";

function PaginationLinks(props) {
  return (
    <nav className="mb-5">
      <ul className="pagination justify-content-center">
        <li className={`page-item ${props.currentPage <= 0 ? "disabled" : ""}`}>
          <Link onClick={() => props.changePage(props.currentPage + -1)} className="page-link" to="#" tabIndex="-1">
            Previous
          </Link>
        </li>
        {
          props.currentPage - 1 > 0 && <li className="page-item">
          <Link onClick={() => props.changePage(0)} className="page-link" to="#">
            1...
          </Link>
          {/* first page */}
        </li>
        }
        
        {
          props.currentPage > 0 && <li className="page-item">
          <Link onClick={() => props.changePage(props.currentPage - 1)} className="page-link " to="#">
            {props.currentPage}
            {/* previous page */}
          </Link>
        </li>
        }
        <li className="page-item">
          <Link className="page-link active" to="#">
            {props.currentPage + 1}
            {/* current page */}
          </Link>
        </li>
        {
          props.currentPage + 2 <= props.totalPages && <li className="page-item">
          <Link onClick={() => props.changePage(props.currentPage + 1)} className="page-link" to="#">
            {props.currentPage + 2}
            {/* next page */}
          </Link>
        </li>
        }
        {
          props.currentPage + 3 <= props.totalPages && <li className="page-item">
          <Link onClick={() => props.changePage(props.totalPages - 1)} className="page-link" to="#">
            ...{props.totalPages}
            {/* last page */}
          </Link>
        </li>
        }
        <li className={`page-item ${props.currentPage + 2 > props.totalPages ? "disabled" : ""}`}>
          <Link onClick={() => props.changePage(props.currentPage + 1)} className="page-link" to="#">
            Next
          </Link>
        </li>
      </ul>
    </nav>
  );
}

export default PaginationLinks;
