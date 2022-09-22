import React from "react"


function BidForm(props) {

    return (
        <div className="place-bid border-top p-3">
              <h6>Place bid as LOGGED USER</h6>
              <form>
                <div class="input-group mb-3">
                  <input
                    type="number"
                    min={0}
                    max={100000}
                    class="form-control"
                    id="bidAmount"
                    name="bidAmount"
                    placeholder="Bid amount"
                    aria-label="Bid amount"
                    aria-describedby="button-bid"
                  />
                  <button
                    class="btn btn-outline-dark mx-1"
                    type="submit"
                    id="button-bid"
                  >
                    Place bid
                  </button>
                </div>
              </form>
            </div>
    )
}

export default BidForm;