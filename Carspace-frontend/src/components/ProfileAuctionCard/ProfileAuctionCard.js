import React from "react";
import { Link, useNavigate } from "react-router-dom";

function ProfileAuctionCard(props) {
  const navigate = useNavigate();

  return (
    <div className="col-md-3 d-flex align-items-stretch">
            <div className="card shadow-sm">
              <Link to="#">
              {props.auction.images.length === 0 ? <img
                  src="Image_not_available.png"
                  className="card-img-top"
                  alt="img"
                /> : 
                <img
                src={props.auction.images[0].imgUrl}
                className="card-img-top"
                alt="img"
              />
              }
              </Link>
              <div className="card-body mb-auto">
                <Link to={"/auction/details/" + props.auction.id}>
                  <h5 className="card-title">{props.auction.carBrand} {props.auction.carModel}</h5>
                </Link>
              </div>
              <ul className="list-group list-group-flush">
                <li className="list-group-item">Mileage: {props.auction.mileage}km</li>
                <li className="list-group-item">
                  <span className="text-primary">{props.auction.bids.length === 0 ? 
                      "No bids yet" :
                      "Highest bid: $" + props.auction.bids[0].amount
                    }</span>
                </li>
                <li className="list-group-item">Location: {props.auction.location}</li>
              </ul>
              <div className="card-footer text-center p-3">
                <Link to={"/auction/details/" + props.auction.id} className="card-link">
                  <button type="button" className="btn btn-primary btn-sm">
                    Go to auction<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-up-right ms-1 mb-1" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M14 2.5a.5.5 0 0 0-.5-.5h-6a.5.5 0 0 0 0 1h4.793L2.146 13.146a.5.5 0 0 0 .708.708L13 3.707V8.5a.5.5 0 0 0 1 0v-6z" />
              </svg>
                  </button>
                </Link>
              </div>
            </div>
          </div>
  )
}

export default ProfileAuctionCard;