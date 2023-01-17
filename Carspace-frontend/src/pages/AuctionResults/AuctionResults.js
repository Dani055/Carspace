import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";
import AuctionCard from "../../components/AuctionCard/AuctionCard";
import FilterForm from "../../components/forms/FilterForm/FilterForm";
import PaginationLinks from "../../components/PaginationLinks/PaginationLinks";
import { getEndedAuctionsWithFiltersAndPage } from "../../service/auctionService";
import "./AuctionResults.css"

function AuctionResults() {
  const [auctions, setAuctions] = useState(null);
  const [filters, setFilters] = useState({ carBrand: "", carModel: "", location: "", minYear: 0, maxYear: 3000, minPrice: 0, maxPrice: 214748364, minMileage: 0, maxMileage: 2000000 });
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
    try {
      const res = await getEndedAuctionsWithFiltersAndPage(filters, currentPage);
      setResults(res.totalItems);
      setAuctions(res.auctions);
      setTotalPages(res.totalPages);
    }
    catch (error) {
      console.log(error);
      toast.error("Something went wrong. Please try again later.")
    }
  }
  useEffect(() => {
    loadEndedAuctionsWithFiltersAndPage();
  }, [filters, currentPage])

  useEffect(() => {
    loadEndedAuctionsWithFiltersAndPage();
  }, [])
  return (
    <div>
      <div className="container my-4">
        <FilterForm applyFilters={applyFilters} />
      </div>
      <div className="container mt-5">
        <div className="bd-callout bd-callout-warning"><p className="p-0 m-0"><strong><svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor" class="bi bi-exclamation-lg" viewBox="0 0 16 16">
          <path d="M7.005 3.1a1 1 0 1 1 1.99 0l-.388 6.35a.61.61 0 0 1-1.214 0L7.005 3.1ZM7 12a1 1 0 1 1 2 0 1 1 0 0 1-2 0Z" />
        </svg>You are not browsing auction results</strong></p></div>
        <div className="row bg-light rounded mb-5 g-4 pb-3 mx-1">
          <p className="text-purple-light">Found {results} auctions...</p>

          {auctions?.map((auction) => {
            return <AuctionCard key={auction.id} auction={auction} />
          })}


        </div>

        <PaginationLinks changePage={changePage} totalPages={totalPages} currentPage={currentPage} />
      </div>
    </div>
  );
}

export default AuctionResults;
