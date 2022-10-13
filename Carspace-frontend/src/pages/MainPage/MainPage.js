import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import AuctionCard from "../../components/AuctionCard/AuctionCard";
import FilterForm from "../../components/forms/FilterForm/FilterForm";
import PaginationLinks from "../../components/PaginationLinks/PaginationLinks";
import { getAuctionsCall } from "../../service/auctionService";

function MainPage() {
  const [auctions, setAuctions] = useState(null);

  useEffect(()=>{
    async function getData(){
      console.log("getting auctions")
      try {
        const res = await getAuctionsCall();
        console.log(res);
        setAuctions(res.obj)
      } catch (error) {
        console.log(error)
      }
    }
    getData();
  }, [])
  return (
    <div>
      <div className="container my-4">
        <FilterForm />
      </div>
      <div className="container mt-5">
        <h3 className="pb-2 border-bottom">Live/Pending auctions</h3>
        <div className="row mb-5 g-4">
          <p className="text-info">Found {auctions?.length} auctions...</p>
          
          {auctions?.map((auction) => {
            return <AuctionCard key={auction.id} auction={auction}/>
          })}


        </div>

        <PaginationLinks/>
      </div>
    </div>
  );
}

export default MainPage;
