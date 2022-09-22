import React from "react";

import Bid from "../Bid/Bid";

function BidHistory(props) {
  return (
    <div className="bid-history bg-light p-3">
      <h4>Bid history</h4>
      <Bid/>
      <Bid/>
      <Bid/>
      <Bid/>
    </div>
  );
}

export default BidHistory;
