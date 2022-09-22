import React, { useState } from "react";
import { Link } from "react-router-dom";

import EditAuctionForm from "../../components/forms/EditAuctionForm/EditAuctionForm";

function CreateAuction(props) {

  
  return (
    <div className="container py-5">
      <h3 className="text-center pb-3">Create auction</h3>
      <div className="row d-flex justify-content-center align-items-center">
        <div className="col-12">
          <div
            className="card card-registration card-registration-2"
            style={{ borderRadius: 15 + "px" }}
          >
            <div className="card-body p-0">
              <EditAuctionForm/>    
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CreateAuction;
