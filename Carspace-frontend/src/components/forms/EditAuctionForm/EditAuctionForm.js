import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./EditAuctionForm.css"
import { toast } from 'react-toastify';
import DateTimePicker from "react-datetime-picker";
import { createAuctionCall, editAuctionCall } from "../../../service/auctionService";
import axios from "axios";
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';

function EditAuctionForm(props) {
  const navigate = useNavigate();
  const [formState, setFormState] = useState({ carBrand: "", carModel: "", carDesc: "", carYear: "", startingPrice: "", buyoutPrice: "", mileage: "", location: "" });
  const [startsOn, setStartsOn] = useState(new Date());
  const [endsOn, setEndsOn] = useState(new Date());
  const [imageFiles, setImageFiles] = useState(null);

  useEffect(() => {
    if (props.editmode) {
      let newFormState = {
        carBrand: props.auction.carBrand,
        carModel: props.auction.carModel,
        carDesc: props.auction.carDesc,
        carYear: props.auction.carYear,
        startingPrice: props.auction.startingPrice,
        buyoutPrice: props.auction.buyoutPrice,
        mileage: props.auction.mileage,
        location: props.auction.location
      }
      setFormState(newFormState);
      setStartsOn(props.auction.startsOn);
      setEndsOn(props.auction.endsOn);
    }
  }, [])

  const handleFormChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setFormState({ ...formState, [name]: value });
  };

  const handleImageChange = (e) => {
    setImageFiles(e.target.files)
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(formState);
    let formData = new FormData();
    for (let i = 0; i < imageFiles?.length; i++) {
      formData.append("photo", imageFiles[i]);
    }
    try {
      
      // const imageRes = await axios.post("http://localhost:9999/feed/uploadimg", formData, {
      //   headers: {
      //     "Content-type": "multipart/form-data",
      //   },
      // })
      const links = [];
      let body = { ...formState, startsOn, endsOn, "urls": links };
      let res;
      if (props.editmode) {
        res = await editAuctionCall(body, props.auction.id);
      }
      else {
        res = await createAuctionCall(body);
      }

      toast.success(res.message);
      navigate('/')
    } catch (err) {
      toast.error(err);
    }

  }
  return (
    <form onSubmit={handleSubmit}>
      <div className="row g-0">
        <div className="col-lg-6">
          <div className="p-5">
            <h3
              
            >
              General vehicle infomation
            </h3>

            <div className="mb-4 pb-2">
              <label htmlFor="formFileMultiple" className="form-label">
                Upload photos:
              </label>
              <input
                className="form-control"
                type="file"
                accept="image/*"
                id="photosUpload"
                name="photosUpload"
                onChange={handleImageChange}
                multiple
              />
            </div>

            <div className="row">
              <div className="col-md-6 mb-4">
                <div className="form-floating mb-3">
                <Tooltip  className="tooltipsize" title={<Typography fontSize={14}>The vehicle's brand. <span className="text-mint">Min. 2 characters!</span></Typography>} placement="top-start" arrow>
                <input
                    type="text"
                    className="form-control"
                    id="carBrand"
                    name="carBrand"
                    onChange={handleFormChange}
                    value={formState.carBrand}
                    placeholder="Elon mobile"
                  />
                </Tooltip>
                  
                  <label htmlFor="floatingInput">Brand *</label>
                </div>
              </div>
              <div className="col-md-6 mb-4">
                <div className="form-floating mb-3">
                <Tooltip  className="tooltipsize" title={<Typography fontSize={14}>Model of the vehicle. <span className="text-mint">Min. 2 characters!</span></Typography>} placement="top-start" arrow>
                  <input
                    type="text"
                    className="form-control"
                    id="carModel"
                    name="carModel"
                    onChange={handleFormChange}
                    value={formState.carModel}
                    placeholder="Cybertrock"
                  />
                  </Tooltip>
                  <label htmlFor="floatingInput">Model *</label>
                </div>
              </div>
            </div>

            <div className="mb-4">
              <div className="form-floating">
              <Tooltip  className="tooltipsize" title={<Typography fontSize={14}>Description of the auction <span className="text-mint">Min. 10 characters!</span></Typography>} placement="top-start" arrow>
                <textarea
                  className="form-control"
                  placeholder="Vehicle description here"
                  id="carDesc"
                  name="carDesc"
                  onChange={handleFormChange}
                  value={formState.carDesc}
                ></textarea>
                </Tooltip>
                <label htmlFor="floatingTextarea">Description *</label>
              </div>
            </div>

            <div className="row">
              <div className="col-md-6 mb-4">
                <div className="form-floating mb-3">
                <Tooltip  className="tooltipsize" title={<Typography fontSize={14}>Location of the vehicle</Typography>} placement="top-start" arrow>
                  <input
                    type="text"
                    className="form-control"
                    id="location"
                    name="location"
                    onChange={handleFormChange}
                    value={formState.location}
                    placeholder="The moon"
                  />
                  </Tooltip>
                  <label htmlFor="floatingInput">Location *</label>
                </div>
              </div>
              <div className="col-md-6 mb-4">
                <div className="form-floating mb-3">
                <Tooltip  className="tooltipsize" title={<Typography fontSize={14}>Mileage of the vehicle <span className="text-mint">in KM</span></Typography>} placement="top-start" arrow>
                  <input
                    type="number"
                    min={0}
                    max={1000000}
                    className="form-control"
                    id="mileage"
                    name="mileage"
                    onChange={handleFormChange}
                    value={formState.mileage}
                    placeholder="20"
                  />
                  </Tooltip>
                  <label htmlFor="floatingInput">Mileage in KM*</label>
                </div>
              </div>
              <div className="col-md-6 mb-4">
                <div className="form-floating mb-3">
                <Tooltip  className="tooltipsize" title={<Typography fontSize={14}>Year which the vehicle was manufactured</Typography>} placement="top-start" arrow>
                  <input
                    type="number"
                    min={1950}
                    max={2024}
                    className="form-control"
                    id="carYear"
                    name="carYear"
                    onChange={handleFormChange}
                    value={formState.carYear}
                    placeholder="2022"
                  />
                  </Tooltip>
                  <label htmlFor="floatingInput">Vehicle year*</label>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="col-lg-6 background-main-alpha text-white">
          <div className="p-5">
            <h3 className="fw-normal mb-5">Auction details</h3>

            <div className="mb-4 pb-2">
              <label className="form-label" htmlFor="form3Examplea3">
                Start date*
              </label>
              <div>
                <DateTimePicker
                  className="bg-light text-dark p-3 rounded"
                  id="startsOn"
                  name="startsOn"
                  onChange={setStartsOn}
                  value={startsOn}
                />
              </div>
            </div>

            <div className="mb-4 pb-2">
              <label className="form-label" htmlFor="form3Examplea3">
                End date*
              </label>
              <div>
                <DateTimePicker
                  className="bg-light text-dark p-3 rounded"
                  id="endsOn"
                  name="ensOn"
                  onChange={setEndsOn}
                  value={endsOn}
                />
              </div>
            </div>

            <div className="row">
              <div className="col-md-6 mb-1 text-black">
                <div className="form-floating mb-3">
                <Tooltip title={<Typography fontSize={14}>Starting price of the auction <span className="text-mint">Min. €1000</span></Typography>} placement="top-start" arrow>
                  <input
                    type="number"
                    min={0}
                    max={1000000}
                    className="form-control"
                    id="startingPrice"
                    onChange={handleFormChange}
                    value={formState.startingPrice}
                    name="startingPrice"
                    placeholder="20"
                  />
                  </Tooltip>
                  <label htmlFor="floatingInput">Starting Price*</label>
                </div>
              </div>
              <div className="col-md-6 mb-4 text-black">
                <div className="form-floating mb-1">
                <Tooltip title={<Typography fontSize={14}>Buyout price of the auction <span className="text-mint">Max. €2 000 000</span></Typography>} placement="top-start" arrow>
                  <input
                    type="number"
                    min={0}
                    max={1000000}
                    className="form-control"
                    id="buyoutPrice"
                    onChange={handleFormChange}
                    value={formState.buyoutPrice}
                    name="buyoutPrice"
                    placeholder="20"
                  />
                  </Tooltip>
                  <label htmlFor="floatingInput">Buyout Price*</label>
                </div>
              </div>
            </div>
            <p className="text-mint bg-dark bg-opacity-50 px-2 py-1 rounded">* Fields are required</p>
            <button
              type="submit"
              className="btn btn-jet"
              data-mdb-ripple-color="dark"
            >
              Confirm
            </button>
          </div>
        </div>
      </div>
    </form>
  );
}

export default EditAuctionForm;
