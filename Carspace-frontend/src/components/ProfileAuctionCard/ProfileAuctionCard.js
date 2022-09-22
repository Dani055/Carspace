import React from "react";
import { Link } from "react-router-dom";

function ProfileAuctionCard(props) {

  return (
    <div className="col-md-3 d-flex align-items-stretch">
            <div className="card">
              <Link to="#">
                <img
                  src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/1998-2001_BMW_328i_%28E46%29_sedan_%282011-07-17%29_01.jpg/1200px-1998-2001_BMW_328i_%28E46%29_sedan_%282011-07-17%29_01.jpg"
                  className="card-img-top"
                  alt=""
                />
              </Link>
              <div className="card-body mb-auto">
                <Link to="#">
                  <h5 className="card-title">BMW 325i</h5>
                </Link>
              </div>
              <ul className="list-group list-group-flush">
                <li className="list-group-item">Mileage: 222 000 km</li>
                <li className="list-group-item">
                  <span className="text-primary">Highest bid 6500$</span>
                </li>
                <li className="list-group-item">Location: Eindhoven</li>
              </ul>
              <div className="card-footer text-center p-3">
                <Link to="#" className="card-link">
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