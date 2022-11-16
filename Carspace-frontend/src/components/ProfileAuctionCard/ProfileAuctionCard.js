import React from "react";
import { Link, useNavigate } from "react-router-dom";

function ProfileAuctionCard(props) {
  const navigate = useNavigate();

  return (
    <div className="col-md-3 d-flex align-items-stretch">
            <div className="card">
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
                    Go to auction
                  </button>
                </Link>
              </div>
            </div>
          </div>
  )
}

export default ProfileAuctionCard;