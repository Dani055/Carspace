import React, { useEffect, useState, useContext } from "react";
import Carousel from "../../components/Carousel/Carousel";
import { Link, useNavigate, useParams } from "react-router-dom";
import AuctionAccordeon from "../../components/AuctionAccordeon/AuctionAccordeon";
import AuctionComments from "../../components/AuctionComments/AuctionComments";
import BidForm from "../../components/forms/BidForm/BidForm";
import BidHistory from "../../components/BidHistory/BidHistory";
import { deleteAuctionCall, getAuctionById } from "../../service/auctionService";
import { toast } from "react-toastify";
import dayjs from "dayjs";
import { UserContext } from "../../UserProvider";

function AuctionDetails(props) {
  const params = useParams();
  const navigate = useNavigate();
  const [auction, setAuction] = useState(null);
  const { loggedUser } = useContext(UserContext);

  console.log(loggedUser);
  useEffect(() => {
    async function getData() {
      try {
        const res = await getAuctionById(params.auctionId);
        console.log(res);
        setAuction(res)
      } catch (error) {
        console.log(error)
        toast.error("Error loading auction");
        navigate('/')
      }
    }
    getData();
  }, [])

  const deleteAuction = async () => {
    try {
      const res = await deleteAuctionCall(params.auctionId);
      toast.success(res.message)
      navigate('/')
    } catch (error) {
      console.log(error);
      toast.error(error);
    }
  };

  const deleteButtonHTML = () => {
    return <>
      <button type="button" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal" className="btn btn-danger ms-3">
        Delete auction
      </button>
      {modalHTML()}
    </>
  }

  const modalHTML = () => {
    return <div
      className="modal fade"
      id="confirmDeleteModal"
      tabIndex={-1}
      aria-labelledby="deleteModalLabel">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title text-black" id="deleteModalLabel">
              Delete auction?
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div className="modal-body text-black">
            <p>
              Are you sure that you want to delete this auction? This
              process is irrevirsible
            </p>
          </div>
          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Cancel
            </button>
            <button
              type="submit"
              data-bs-dismiss="modal"
              onClick={deleteAuction}
              className="btn btn-danger"
            >
              Delete
            </button>
          </div>
        </div>
      </div>
    </div>;
  }

  const displayDeleteButton = () => {
    if (loggedUser?.id === auction.creator.id) {
      if (dayjs().isBefore(auction.startsOn)) {
        return deleteButtonHTML();
      }
    }
    else if (loggedUser?.role === 'admin') {
      return deleteButtonHTML();
    }
  };

  const displayBidForm = () => {
    if (loggedUser !== null && dayjs().isAfter(auction.startsOn) && dayjs().isBefore(auction.endsOn)) {
      return <BidForm />;
    }
  }


  return (
    <div className="container">
      {
        auction !== null &&
        <div className="row my-4">
          <h3>
            {auction.carYear} {auction.carBrand} {auction.carModel}

            {
              dayjs().isBefore(auction.startsOn) &&
              <span className="badge bg-warning ms-2">Pending</span>
            }
            {
              dayjs().isAfter(auction.startsOn) && dayjs().isBefore(auction.endsOn) &&
              <span className="badge bg-success ms-2">Live</span>
            }
            {
              dayjs().isAfter(auction.endsOn) &&
              <span className="badge bg-danger ms-2">Ended</span>
            }
          </h3>
          <div className="col-md-6 ">
            <Carousel images={auction.images} />
            <div className="bg-primary p-3 rounded">
              <div className="row">
                <div className="col-auto me-auto">
                  <p className="m-0">
                    Vehicle owner:
                    <Link className="link-light" to="profile">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="32"
                        height="32"
                        fill="currentColor"
                        className="bi bi-person"
                        viewBox="0 0 16 16"
                      >
                        <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z" />
                      </svg>{" "}
                      {auction.creator.username}
                    </Link>
                  </p>
                </div>
                <div className="col-auto buttons">
                  {
                    (loggedUser?.id === auction.creator.id || loggedUser?.role === 'admin') && dayjs().isBefore(auction.startsOn) &&
                    <button type="button" className="btn btn-warning" onClick={() => navigate("/auction/edit/" + auction.id)}>
                      Edit auction
                    </button>
                  }
                  {
                    displayDeleteButton()
                  }
                </div>
              </div>
            </div>
            <AuctionAccordeon auction={auction} />
            <AuctionComments auction={auction} comments={auction.comments}/>
          </div>
          <div className="col-md-6">
            {
              dayjs().isBefore(auction.startsOn) &&
              <div className="bg-dark text-light p-3 rounded">
              <p>
                Auction starts on <span className="bold">{dayjs(auction.startsOn).format("DD/MM/YYYY HH:mm:ss")}</span>
              </p>
              <p className="m-0">
                Time remaining until start:{" "}
                <span className="bold">(calculate)</span>
              </p>
              </div>
            }
            {
              dayjs().isAfter(auction.startsOn) && dayjs().isBefore(auction.endsOn) &&
              <div className="bg-warning p-3 rounded">
              <p>
                Auction ending on <span className="bold">{dayjs(auction.endsOn).format("DD/MM/YYYY HH:mm:ss")}</span>
              </p>
              <p className="m-0">
                Time remaining:{" "}
                <span className="bold">(calculate)</span>
              </p>
            </div>
            }
            {
              dayjs().isAfter(auction.endsOn) &&
              <div className="bg-danger text-white p-3 rounded">
              <p>
                Auction ended on: <span className="bold">{dayjs(auction.endsOn).format("DD/MM/YYYY HH:mm:ss")}</span>
              </p>
              </div>
            }
            <div className="rounded my-2 shadow">
              <div className="current-bid-details bg-success p-3">
                {
                  auction.bids.length !== 0 ? <>
                  <p>
                  Winning bid
                  <Link className="link-light" to="profile">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="32"
                      height="32"
                      fill="currentColor"
                      className="bi bi-person"
                      viewBox="0 0 16 16"
                    >
                      <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z" />
                    </svg>{" "}
                    {auction.bids[0].bidder.username}
                  </Link>
                </p>
                <h3>{auction.bids[0].amount}€</h3>
                <p>
                  Placed on <span className="bold">{dayjs(auction.bids[0].creatordOn).format("DD/MM/YYYY HH:mm:ss")}</span>
                </p>
                </> : <h4>This auction has no bids yet</h4>
                }
                
              </div>

              {displayBidForm()}
            </div>
            <BidHistory bids={auction.bids}/>
          </div>
        </div>
      }
    </div>
  );
}

export default AuctionDetails;
