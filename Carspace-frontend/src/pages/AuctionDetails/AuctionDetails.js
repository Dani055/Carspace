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
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { UserContext } from "../../UserProvider";
import TimerComponent from "../../components/TimerComponent/TimerComponent";
//&& dayjs().isBefore(auction.startsOn)
const ENDPOINT = "http://localhost:8080/ws";

function AuctionDetails(props) {
  const [stompClient, setStompClient] = useState(null);

  const params = useParams();
  const navigate = useNavigate();
  const [auction, setAuction] = useState(null);
  const [bids, setBids] = useState([]);
  const { loggedUser } = useContext(UserContext);

  useEffect(() => {
    async function getData() {
      try {
        const res = await getAuctionById(params.auctionId);
        await setAuction(res);
        await setBids(res.bids);

      } catch (error) {
        console.log(error)
        toast.error("Error loading auction");
        navigate('/')
      }
    }
    getData();
  }, [])
  useEffect(() => {
    if (auction !== null && !auction.hasSold) {
      const socket = SockJS(ENDPOINT);
      const stompClient = Stomp.over(socket);
      stompClient.connect({}, () => {
        // subscribe to the backend
        stompClient.subscribe('/topic/bids', (data) => {
          filterRelevantBids(JSON.parse(data.body));
        });
      });
      setStompClient(stompClient);
      return () => {
        // Anything in here is fired on component unmount.
        stompClient.disconnect();
      }
    }
  }, [auction])
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

  const filterRelevantBids = (bids) => {
    const auctionId = Number.parseInt(params.auctionId);
    let relevantBids = bids.filter(b => b.auctionId === auctionId);
    setBids(relevantBids);
  }

  const deleteButtonHTML = () => {
    return <>
      <button type="button" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal" className="btn text-dark btn-outline-danger ms-3">
        Delete auction
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle-fill ms-1" viewBox="0 0 16 16">
          <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293 5.354 4.646z" />
        </svg>
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
    if (loggedUser?.role === 'admin') {
      return deleteButtonHTML();
    }
  };

  const displayBidForm = () => {
    if (loggedUser !== null && dayjs().isAfter(auction.startsOn) && dayjs().isBefore(auction.endsOn)) {
      return <BidForm loggedUser={loggedUser} />;
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
              <span className="badge bg-opacity-50 bg-warning ms-2">Pending</span>
            }
            {
              dayjs().isAfter(auction.startsOn) && dayjs().isBefore(auction.endsOn) &&
              <span className="badge bg-success ms-2">Live</span>
            }
            {
              dayjs().isAfter(auction.endsOn) &&
              <span className="badge bg-opacity-75 bg-danger ms-2">Ended</span>
            }
          </h3>
          <div className="col-md-6 ">
            <Carousel images={auction.images} />
            <div className="border-purple-light p-3 border-2 border rounded-bottom">
              <div className="row">
                <div className="col-auto me-auto">
                  <p className="m-0">
                    Vehicle owner:
                    <Link className="link-dark" to={"/profile/" + auction.creator.username}>
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
                    (loggedUser?.id === auction.creator.id || loggedUser?.role === 'admin') &&
                    <button type="button" className="btn btn-warning-light" onClick={() => navigate("/auction/edit/" + auction.id)}>
                      Edit auction
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil ms-1" viewBox="0 0 16 16">
                        <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z" />
                      </svg>
                    </button>
                  }
                  {
                    displayDeleteButton()
                  }
                </div>
              </div>
            </div>
            <AuctionAccordeon auction={auction} />
            <AuctionComments auction={auction} comments={auction.comments} />
          </div>
          <div className="col-md-6">
            {
              dayjs().isBefore(auction.startsOn) &&
              <div className="bg-light text-muted p-3 rounded-top">
                <p>
                  Auction starts on <span className="bold">{dayjs(auction.startsOn).format("DD/MM/YYYY HH:mm:ss")}</span>
                </p>
                <TimerComponent diffDate={auction.startsOn} />
              </div>
            }
            {
              dayjs().isAfter(auction.startsOn) && dayjs().isBefore(auction.endsOn) &&
              <div className="bg-warning-light p-3 rounded-top">
                <p>
                  Auction ending on <span className="bold">{dayjs(auction.endsOn).format("DD/MM/YYYY HH:mm:ss")}</span>
                </p>
                <TimerComponent diffDate={auction.endsOn} />
              </div>
            }
            {
              dayjs().isAfter(auction.endsOn) &&
              <div className="bg-danger bg-opacity-50 text-dark p-3 rounded-top">
                <p className="m-0">
                  Auction ended on: <span className="bold">{dayjs(auction.endsOn).format("DD/MM/YYYY HH:mm:ss")}</span>
                </p>
              </div>
            }
            <div className="rounded-bottom my-2 shadow">
              {
                bids.length !== 0 && bids[0] !== undefined && <div className="current-bid-details bg-success text-success bg-opacity-10 p-3">
                  <p>
                    {auction.winningBid == null ? "Leading bid" : "Winner"}
                    <Link className="link-success" to="profile">
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
                      {bids[0].bidder.username}
                    </Link>
                  </p>
                  <h3>{bids[0].amount}€</h3>
                  <p>
                    Placed on <span className="bold">{dayjs(auction.bids[0]?.createdOn).format("DD/MM/YYYY HH:mm:ss")}</span>
                  </p>
                </div>
              }
              {
                auction.bids.length === 0 && !auction.hasSold && <h4 className="p-3">
                  This auction has no bids yet!
                </h4>
              }

              {
                auction.bids.length === 0 && auction.hasSold &&
                <div className="bg-danger bg-opacity-75 p-3">
                  <h4>
                    This auction failed to sell!
                  </h4>
                </div>
              }
              {displayBidForm()}
            </div>
            <BidHistory bids={bids} />
          </div>
        </div>
      }
    </div>
  );
}

export default AuctionDetails;
