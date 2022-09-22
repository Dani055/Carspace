import React from "react";
import { Link } from "react-router-dom";
import ProfileAuctionCard from "../../components/ProfileAuctionCard/ProfileAuctionCard";

function ProfilePage(props) {
  return (
    <div className="container my-5">
      <div className="row">
        <div className="col-md-6 mx-auto">
            <div className="row">
                <div className="col-sm-4">
                    <div className="media profile rounded-circle p-0">
                        <img src="/default-user-image.png"></img>
                    </div>
                </div>
                <div className="col-sm-8 py-5 px-4">
                    <h4>QuickSilverZ</h4>
                    <p>John Doe</p>
                    <div className="border-top">
                        <p><strong>Contact info:</strong></p>
                        <p>Email: jdoe@mail.com</p>
                        <p>Mobile: +31 999999 999</p>
                    </div>
                </div>
            </div>
        </div>
        <div className="col-md-10 mx-auto border-top pt-4">
            <h3 className="pb-2">Cars auctioned: (3)</h3>
            <row className="row mb-5 g-4">
                <ProfileAuctionCard/>
                <ProfileAuctionCard/>
                <ProfileAuctionCard/>
            </row>
        </div>
        <div className="col-md-10 mx-auto border-top pt-4">
            <h3 className="pb-2">Bid history (12)</h3>
            <row className="row mb-5 g-4">
                <ProfileAuctionCard/>
                <ProfileAuctionCard/>
                <ProfileAuctionCard/>
                <div className="col-12 text-center">
                    <button type="button" className="btn btn-dark w-50">Load more</button>
                </div>
            </row>
        </div>
      </div>
    </div>
  );
}

export default ProfilePage;
