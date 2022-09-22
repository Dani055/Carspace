import React, { useState } from "react";
import Carousel from "../../components/Carousel/Carousel";
import { Link } from "react-router-dom";
import AuctionAccordeon from "../../components/AuctionAccordeon/AuctionAccordeon";
import AuctionComments from "../../components/AuctionComments/AuctionComments";
import BidForm from "../../components/forms/BidForm/BidForm";
import BidHistory from "../../components/BidHistory/BidHistory";

function AuctionDetails(props) {
  return (
    <div className="container">
      <div className="row my-4">
        <h3>
          2001 BMW 330i <span class="badge bg-success">Live</span>
        </h3>
        <div className="col-md-6 ">
          <Carousel />
          <div className="bg-primary p-3 rounded">
            <div className="row">
              <div className="col-auto me-auto">
              <p className="m-0">
              Vehicle owner:
              <Link className="link-light" to="profile">
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
                  Bai mitio
                </Link>
              </p>
              </div>
              <div className="col-auto buttons">
                <button type="button" className="btn btn-warning">
                  Edit auction
                </button>
                <button type="button" className="btn btn-danger ms-3">
                  Delete auction
                </button>
              </div>
            </div>
            
          </div>
          <AuctionAccordeon/>
          <AuctionComments/>  
        </div>
        <div className="col-md-6">
          <div className="bg-warning p-3 rounded">
            <p>
              Auction ending on <span className="bold">25.09.2022</span>
            </p>
            <p className="m-0">
              Time remaining:{" "}
              <span className="bold">3 days 15 hrs 25 seconds</span>
            </p>
          </div>
          <div className="rounded my-2 shadow">
            <div className="current-bid-details bg-success p-3">
              <p>
                Winning bid
                <Link className="link-light" to="profile">
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
                  QuickSilverZ
                </Link>
              </p>
              <h3>3500$</h3>
              <p>
                Placed on <span className="bold">22.09.2022 15:37:42</span>
              </p>
            </div>
            <BidForm/>
          </div>
          <BidHistory/>
        </div>
      </div>
    </div>
  );
}

export default AuctionDetails;
