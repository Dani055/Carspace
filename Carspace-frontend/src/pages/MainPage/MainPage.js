import React, { useState } from "react";
import { Link } from "react-router-dom";
import AuctionCard from "../../components/AuctionCard/AuctionCard";
import FilterForm from "../../components/forms/FilterForm/FilterForm";
import PaginationLinks from "../../components/PaginationLinks/PaginationLinks";

function MainPage() {

  return (
    <div>
      <div className="container my-4">
        <FilterForm />
      </div>
      <div className="container mt-5">
        <h3 className="pb-2 border-bottom">Live/Pending auctions</h3>
        <div className="row mb-5 g-4">
          <p className="text-info">Found 12 auctions...</p>
          <AuctionCard/>
          <div className="col-md-3 d-flex align-items-stretch">
            <div className="card">
              <Link to="#">
                <img
                  src="https://upload.wikimedia.org/wikipedia/commons/e/e5/Chevrolet_Corvette_C8_Stingray_blue.jpg"
                  className="card-img-top"
                  alt=""
                />
              </Link>
              <div className="card-body mb-auto">
                <Link to="#">
                  <h5 className="card-title">Corvette 'C8</h5>
                </Link>

                <p className="card-text">
                  Some quick example text to build on the card title and make up
                  the bulk of the card's content. asdjhajdha;...
                </p>
                <div className="alert alert-success mb-auto" role="alert">
                  Live - Ends 2022/07/07 15:23:53
                </div>
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

          <div className="col-md-3 d-flex align-items-stretch">
            <div className="card">
              <Link to="#">
                <img
                  src="https://i.pinimg.com/originals/c9/09/b8/c909b8bdf9521f08fdafd3c3b8518cf7.jpg"
                  className="card-img-top"
                  alt=""
                />
              </Link>
              <div className="card-body">
                <Link to="#">
                  <h5 className="card-title">Mazda miata</h5>
                </Link>

                <p className="card-text">
                  Some quick example text to build on the card title and make up
                  the bulk of the card's content. asdjhajdha;...
                </p>
                <div className="alert alert-danger" role="alert">
                  Ended 2022/07/07 15:23:53
                </div>
              </div>
              <ul className="list-group list-group-flush">
                <li className="list-group-item">Mileage: 222 000 km</li>
                <li className="list-group-item">
                  <span className="text-success">Winning bid 15000$</span>
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

          <div className="col-md-3 d-flex align-items-stretch">
            <div className="card">
              <Link to="#">
                <img
                  src="https://www.autovisie.nl/wp-content/uploads/bmw-z4-2019-6.jpg"
                  className="card-img-top"
                  alt=""
                />
              </Link>
              <div className="card-body">
                <Link to="#">
                  <h5 className="card-title">BMW Z4</h5>
                </Link>

                <p className="card-text">
                  Some quick example text to build on the card title and make up
                  the bulk of the card's content. asdjhajdha;...
                </p>
                <div className="alert alert-danger" role="alert">
                  Ended 2022/07/07 15:23:53
                </div>
              </div>
              <ul className="list-group list-group-flush">
                <li className="list-group-item">Mileage: 222 000 km</li>
                <li className="list-group-item">
                  <span className="text-success">Winning bid 15000$</span>
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

          <div className="col-md-3 d-flex align-items-stretch">
            <div className="card">
              <Link to="#">
                <img
                  src="https://www.autovisie.nl/wp-content/uploads/bmw-z4-2019-6.jpg"
                  className="card-img-top"
                  alt=""
                />
              </Link>
              <div className="card-body">
                <Link to="#">
                  <h5 className="card-title">BMW Z4</h5>
                </Link>

                <p className="card-text">
                  Some quick example text to build on the card title and make up
                  the bulk of the card's content. asdjhajdha;...
                </p>
                <div className="alert alert-danger" role="alert">
                  Ended 2022/07/07 15:23:53
                </div>
              </div>
              <ul className="list-group list-group-flush">
                <li className="list-group-item">Mileage: 222 000 km</li>
                <li className="list-group-item">
                  <span className="text-success">Winning bid 15000$</span>
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


        </div>

        <PaginationLinks/>
      </div>
    </div>
  );
}

export default MainPage;
