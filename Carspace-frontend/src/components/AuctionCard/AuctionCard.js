import dayjs from "dayjs";
import React from "react";
import {Link, useNavigate } from "react-router-dom";

function AuctionCard(props) {
  const navigate = useNavigate();
  // BIDS ARE NOT SORTED BY HIGHEST, FIX LATER
  // COMMENTS NEED TO BE SORTED - desc
  return (
    <div className="col-md-3 d-flex align-items-stretch">
            <div className="card">
              
              <Link to={"/auction/details/" + props.auction.id}>
              {props.auction.images.length === 0 ? <img
                  src="Image_not_available.png"
                  className="card-img-top"
                  alt=""
                /> : 
                <img
                src={props.auction.images[0].imgUrl}
                className="card-img-top"
                alt=""
              />
              }
              </Link>
              <div className="card-body mb-auto">
                <Link to={"/auction/details/" + props.auction.id}>
                  <h5 className="card-title">{props.auction.carBrand} {props.auction.carModel}</h5>
                </Link>

                <p className="card-text">
                  {props.auction.carDesc}
                </p>
                {
                  dayjs().isBefore(props.auction.startsOn) && 
                  <div className="alert alert-warning" role="alert">
                  Auction starts on  - {dayjs(props.auction.startsOn).format("DD/MM/YYYY HH:mm:ss")}
                  </div>
                }
                {
                  dayjs().isAfter(props.auction.startsOn) && dayjs().isBefore(props.auction.endsOn) &&
                  <div className="alert alert-success" role="alert">
                    Live - Ends {dayjs(props.auction.endsOn).format("DD/MM/YYYY HH:mm:ss")}
                  </div>
                }
                {
                  dayjs().isAfter(props.auction.endsOn) &&
                  <div className="alert alert-danger" role="alert">
                    Auction has ended on {dayjs(props.auction.endsOn).format("DD/MM/YYYY HH:mm:ss")}
                  </div>
                }
              </div>
              <ul className="list-group list-group-flush">
                <li className="list-group-item">Mileage: {props.auction.mileage}</li>
                <li className="list-group-item">
                  <span className="text-primary">
                    {props.auction.bids.length === 0 ? 
                      "No bids yet" :
                      "Highest bid: $" + props.auction.bids[0].amount
                    }
                    
                    </span>
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
  );
}

export default AuctionCard;
