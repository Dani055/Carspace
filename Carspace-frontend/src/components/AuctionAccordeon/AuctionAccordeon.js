import React from "react"

function AuctionAccordeon(props) {

    return (
        <div class="accordion mt-4" id="accordionDesc">
        <div class="accordion-item">
          <h2 class="accordion-header" id="headingOne">
            <button
              class="accordion-button"
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
            class="accordion-collapse collapse show"
            aria-labelledby="headingOne"
            data-bs-parent="#accordionDesc"
          >
            <div class="accordion-body">
              <p>Vehicle brand : <strong>BMW</strong></p>
              <p>Vehicle model : <strong>330i</strong></p>
              <p>Vehicle location : <strong>Eindhoven</strong></p>
              <p>Year of production : <strong>2001  </strong></p>
              <p>Mielage : <strong>210 000km</strong></p>
            </div>
          </div>
        </div>
        <div class="accordion-item">
          <h2 class="accordion-header" id="headingTwo">
            <button
              class="accordion-button collapsed"
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
            class="accordion-collapse collapse"
            aria-labelledby="headingTwo"
            data-bs-parent="#accordionDesc"
          >
            <div class="accordion-body">
              <p>Starting price : <strong>3000$</strong></p>
              <p>Buyout price : <strong>10000$</strong></p>
              <p>Auction start date : <strong>22.09.2022 11:00:00</strong></p>
              <p>Auction end date : <strong>27.09.2022 11:00:00  </strong></p>
            </div>
          </div>
        </div>
      </div>        
    )
}

export default AuctionAccordeon;