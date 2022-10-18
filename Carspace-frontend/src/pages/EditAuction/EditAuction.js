import React, { useContext, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import EditAuctionForm from "../../components/forms/EditAuctionForm/EditAuctionForm";
import { getAuctionById } from "../../service/auctionService";
import { UserContext } from "../../UserProvider";

function EditAuction(props) {
  const params = useParams();
  const navigate = useNavigate();
  const [auction, setAuction] = useState(null);
  const { loggedUser } = useContext(UserContext);

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

  return (
    <div className="container py-5">
      <h3 className="text-center pb-3">Edit your auction</h3>
      <div className="row d-flex justify-content-center align-items-center">
        <div className="col-12">
          <div
            className="card card-registration card-registration-2"
            style={{ borderRadius: 15 + "px" }}
          >
            <div className="card-body p-0">
              <div className="alert alert-danger" role="alert">
                Warning! If you choose to upload any photos all old photos will
                be deleted! In any other case they will remain.
              </div>
              {auction != null && 
                <EditAuctionForm auction={auction} editmode={true}/>
              }
              
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default EditAuction;
