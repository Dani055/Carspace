import React, { useState } from "react";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";
import { createBidCall } from "../../../service/bidService";

function BidForm(props) {
  const params = useParams();
  const [amount, setAmount] = useState("");

  const handleFormChange = (event) => {
    setAmount(event.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await createBidCall({amount}, params.auctionId);
      toast.success(res.message);
    } catch (error) {
      console.log(error);
      toast.error(error);
    }
  };


  return (
    <div className="place-bid border-top p-3">
      <h6>Place bid as LOGGED USER</h6>
      <form id="bidForm" onSubmit={handleSubmit}>
        <div className="input-group mb-3">
          <input
            type="number"
            min={0}
            max={100000}
            onChange={handleFormChange}
            className="form-control"
            id="bidAmount"
            name="bidAmount"
            placeholder="Bid amount"
            aria-label="Bid amount"
            aria-describedby="button-bid"
          />
          <button
            className="btn btn-outline-dark mx-1"
            type="button"
            data-bs-toggle="modal"
            data-bs-target="#confirmBidModal"
            id="button-bid"
          >
            Place bid
          </button>
        </div>
      </form>

        <div className="modal fade" id="confirmBidModal" tabIndex={-1} aria-labelledby="bidModalLabel">
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="bidModalLabel">Place bid?</h5>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div className="modal-body">
                <p>Are you sure that you want to place your bid? This process is irrevirsible</p>
              </div>
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-secondary"
                  data-bs-dismiss="modal"
                >
                  Cancel
                </button>
                <button type="submit" data-bs-dismiss="modal" form="bidForm" className="btn btn-primary">
                  Confirm
                </button>
              </div>
            </div>
          </div>
          </div>
      </div>
  );
}

export default BidForm;
