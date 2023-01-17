import dayjs from "dayjs";
import React from "react";
import { Link, useNavigate } from "react-router-dom";
import "./AuctionCard.css"
function AuctionCard(props) {
  const navigate = useNavigate();

  return (
    <div className="auction-card col-md-3">
      <Link className="text-decoration-none text-jet" to={"/auction/details/" + props.auction.id}>
      <div className="card bg-light shadow-sm">
        <div className="card-img-top">
        
          {props.auction.images.length === 0 ? <img
            src="Image_not_available.png"
            alt="img"
          /> :
            <img
              src={props.auction.images[0].imgUrl}
              alt="img"
            />
          }
        </div>
        
        <div className="card-body mb-auto">
          <div>
            <h5 className="m-0">{props.auction.carBrand} {props.auction.carModel}</h5>
            <hr className="mt-2 mb-2"></hr>
          </div>
          
          <div className="description pb-1 mb-auto">
            <p className="card-text">
              {props.auction.carDesc}
            </p>
          </div>

          <div className="mt-auto">
            {
              dayjs().isBefore(props.auction.startsOn) &&
              <div className="w-100 badge bg-opacity-50 text-dark-grey bg-warning py-2">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-clock me-1" viewBox="0 0 16 16">
                  <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z" />
                  <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z" />
                </svg>
                Pending
              </div>
            }
            {
              dayjs().isAfter(props.auction.startsOn) && dayjs().isBefore(props.auction.endsOn) &&
              <div className="w-100 badge bg-opacity-25 text-success bg-success py-2" role="alert">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-check-lg me-1" viewBox="0 0 16 16">
                  <path d="M12.736 3.97a.733.733 0 0 1 1.047 0c.286.289.29.756.01 1.05L7.88 12.01a.733.733 0 0 1-1.065.02L3.217 8.384a.757.757 0 0 1 0-1.06.733.733 0 0 1 1.047 0l3.052 3.093 5.4-6.425a.247.247 0 0 1 .02-.022Z" />
                </svg>
                Live
              </div>
            }
            {
              dayjs().isAfter(props.auction.endsOn) &&
              <div className="w-100 badge background-red py-2" role="alert">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img" aria-label="Warning:">
                  <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                </svg>
                Ended
              </div>
            }
          </div>

        </div>
        <ul className="list-group text-center font-90 list-group-flush">
          <li className="list-group-item bg-light-gray"><i class="fa-solid fa-road me-1"></i>{props.auction.mileage} km</li>
          <li className="list-group-item bg-light-gray">
            <span className="">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-coin me-1" viewBox="0 0 16 16">
                <path d="M5.5 9.511c.076.954.83 1.697 2.182 1.785V12h.6v-.709c1.4-.098 2.218-.846 2.218-1.932 0-.987-.626-1.496-1.745-1.76l-.473-.112V5.57c.6.068.982.396 1.074.85h1.052c-.076-.919-.864-1.638-2.126-1.716V4h-.6v.719c-1.195.117-2.01.836-2.01 1.853 0 .9.606 1.472 1.613 1.707l.397.098v2.034c-.615-.093-1.022-.43-1.114-.9H5.5zm2.177-2.166c-.59-.137-.91-.416-.91-.836 0-.47.345-.822.915-.925v1.76h-.005zm.692 1.193c.717.166 1.048.435 1.048.91 0 .542-.412.914-1.135.982V8.518l.087.02z" />
                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z" />
                <path d="M8 13.5a5.5 5.5 0 1 1 0-11 5.5 5.5 0 0 1 0 11zm0 .5A6 6 0 1 0 8 2a6 6 0 0 0 0 12z" />
              </svg>
              {props.auction.bids.length === 0 ?
                "No bids yet" :
                <span>Highest bid: {props.auction.bids[0].amount} €</span>
              }

            </span>
          </li>
          <li className="list-group-item bg-light-gray"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-geo-alt-fill me-1" viewBox="0 0 16 16">
            <path d="M8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10zm0-7a3 3 0 1 1 0-6 3 3 0 0 1 0 6z" />
          </svg>{props.auction.location}</li>
        </ul>
      </div>
      </Link>
    </div>
  );
}

export default AuctionCard;
