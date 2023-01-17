import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import AuctionCard from "../../components/AuctionCard/AuctionCard";
import FilterForm from "../../components/forms/FilterForm/FilterForm";
import PaginationLinks from "../../components/PaginationLinks/PaginationLinks";
import { getLiveAuctionsWithFiltersAndPage } from "../../service/auctionService";

function MainPage() {
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
  const loadAuctionsWithFiltersAndPage = async () => {
    try {
      const res = await getLiveAuctionsWithFiltersAndPage(filters, currentPage);
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
    loadAuctionsWithFiltersAndPage();
  }, [filters, currentPage])

  useEffect(() => {
    loadAuctionsWithFiltersAndPage();
  }, [])
  return (
    <div>
      <div className="container my-4">
        <FilterForm applyFilters={applyFilters} />
      </div>
      <div className="container rounded mt-5">
        <div className="row mb-5 g-4 bg-light mx-1 px-2 pb-4">
          <div className="auctions-top">
            <h3 className="m-0">Live/Pending auctions</h3>
            <hr></hr>
            <p className="text-purple-light m-0">Found {results} auctions...</p>
          </div>


          {auctions?.map((auction) => {
            return <AuctionCard key={auction.id} auction={auction} />
          })}


        </div>

        <PaginationLinks changePage={changePage} totalPages={totalPages} currentPage={currentPage} />
      </div>
    </div>
  );
}

export default MainPage;
