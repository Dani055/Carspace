import React from "react"
import dayjs from "dayjs";

function AuctionAccordeon(props) {

    return (
        <div className="accordion mt-4" id="accordionDesc">
        <div className="accordion-item">
          <h2 className="accordion-header" id="headingOne">
            <button
              className="accordion-button"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#collapseOne"
              aria-expanded="true"
              aria-controls="collapseOne"
            >
              Vehicle information
            </button>
          </h2>
          <div
            id="collapseOne"
            className="accordion-collapse collapse show"
            aria-labelledby="headingOne"
            data-bs-parent="#accordionDesc"
          >
            <div className="accordion-body">
              <p>Vehicle brand : <strong>{props.auction.carBrand}</strong></p>
              <p>Vehicle model : <strong>{props.auction.carModel}</strong></p>
              <p>Vehicle location : <strong>{props.auction.location}</strong></p>
              <p>Year of production : <strong>{props.auction.carYear}</strong></p>
              <p>Mielage : <strong>{props.auction.mileage} km</strong></p>
              <p>Description : <strong>{props.auction.carDesc}</strong></p>
            </div>
          </div>
        </div>
        <div className="accordion-item">
          <h2 className="accordion-header" id="headingTwo">
            <button
              className="accordion-button collapsed"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#collapseTwo"
              aria-expanded="false"
              aria-controls="collapseTwo"
            >
              Auction information
            </button>
          </h2>
          <div
            id="collapseTwo"
            className="accordion-collapse collapse"
            aria-labelledby="headingTwo"
            data-bs-parent="#accordionDesc"
          >
            <div className="accordion-body">
              <p>Starting price : <strong>{props.auction.startingPrice}€</strong></p>
              <p>Buyout price : <strong>{props.auction.buyoutPrice}€</strong></p>
              <p>Auction start date : <strong>{dayjs(props.auction.startsOn).format("DD/MM/YYYY HH:mm:ss")}</strong></p>
              <p>Auction end date : <strong>{dayjs(props.auction.endsOn).format("DD/MM/YYYY HH:mm:ss")}</strong></p>
            </div>
          </div>
        </div>
      </div>        
    )
}

export default AuctionAccordeon;