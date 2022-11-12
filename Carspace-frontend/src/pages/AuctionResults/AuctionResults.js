import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";
import AuctionCard from "../../components/AuctionCard/AuctionCard";
import FilterForm from "../../components/forms/FilterForm/FilterForm";
import PaginationLinks from "../../components/PaginationLinks/PaginationLinks";
import {getEndedAuctionsWithFiltersAndPage } from "../../service/auctionService";

function AuctionResults() {
  const [auctions, setAuctions] = useState(null);
  const [filters, setFilters] = useState({ carBrand: "", carModel: "", location: "", minYear: 0, maxYear: 3000, minPrice: 0, maxPrice: 214748364, minMileage: 0, maxMileage: 2000000});
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [results, setResults] = useState(0);

  const applyFilters = (filterObj) => {
    setFilters(filterObj);
    setCurrentPage(0);
  }
  const changePage = (page) => {
    setCurrentPage(page);
  }
  const loadEndedAuctionsWithFiltersAndPage = async () => {
      try{
        const res = await getEndedAuctionsWithFiltersAndPage(filters, currentPage);
        setResults(res.totalItems);
        setAuctions(res.auctions);
        setTotalPages(res.totalPages);
      }
      catch(error){
        console.log(error);
        toast.error("Something went wrong. Please try again later.")
      }
  }
  useEffect(() => {
    loadEndedAuctionsWithFiltersAndPage();
  }, [filters, currentPage])

  useEffect(()=>{
    loadEndedAuctionsWithFiltersAndPage();
  }, [])
  return (
    <div>
      <div className="container my-4">
        <FilterForm applyFilters={applyFilters}/>
      </div>
      <div className="container mt-5">
        <h3 className="border-bottom bg-warning p-2 mb-4 rounded">You are not browsing auction results</h3>
        <div className="row bg-light mb-5 g-4 pb-3">
          <p className="text-info">Found {results} auctions...</p>
          
          {auctions?.map((auction) => {
            return <AuctionCard key={auction.id} auction={auction}/>
          })}


        </div>

        <PaginationLinks changePage={changePage} totalPages={totalPages} currentPage={currentPage}/>
      </div>
    </div>
  );
}

export default AuctionResults;
