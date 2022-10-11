import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./EditAuctionForm.css"
import { toast } from 'react-toastify';
import DateTimePicker from "react-datetime-picker";
import { createAuctionCall } from "../../../service/auctionService";
import axios from "axios";

function EditAuctionForm(props) {
  const navigate = useNavigate();
  const [formState, setFormState] = useState({ carBrand: "", carModel: "", carDesc: "", carYear: 0, startingPrice: 0, buyoutPrice: 0, mileage: 0, location: ""});
  const [startsOn, setStartsOn] = useState(new Date());
  const [endsOn, setEndsOn] = useState(new Date());
  const [imageFiles, setImageFiles] = useState(null);

  const handleFormChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setFormState({ ...formState, [name]: value });
  };

  const handleImageChange= (e) => {
    setImageFiles(e.target.files)
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    let formData = new FormData();
    for(let i=0; i<imageFiles?.length;i++){
      formData.append("photo", imageFiles[i]);
    }
    try {
      const imageRes = await axios.post("https://ydimage-server.herokuapp.com/feed/uploadimg", formData, {
      headers:{
        "Content-type": "multipart/form-data",
      },})
      const links = imageRes.data.links;
      let body = {...formState, startsOn, endsOn, "urls": links};
      const res = await createAuctionCall(body);
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
              className="fw-normal mb-5"
              style={{ color: "var(--bs-purple)" }}
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
                  <input
                    type="text"
                    className="form-control"
                    id="carBrand"
                    name="carBrand"
                    onChange={handleFormChange}
                    placeholder="Elon mobile"
                  />
                  <label htmlFor="floatingInput">Brand</label>
                </div>
              </div>
              <div className="col-md-6 mb-4">
                <div className="form-floating mb-3">
                  <input
                    type="text"
                    className="form-control"
                    id="carModel"
                    name="carModel"
                    onChange={handleFormChange}
                    placeholder="Cybertrock"
                  />
                  <label htmlFor="floatingInput">Model</label>
                </div>
              </div>
            </div>

            <div className="mb-4">
              <div className="form-floating">
                <textarea
                  className="form-control"
                  placeholder="Vehicle description here"
                  id="carDesc"
                  name="carDesc"
                  onChange={handleFormChange}
                ></textarea>
                <label htmlFor="floatingTextarea">Description</label>
              </div>
            </div>

            <div className="row">
              <div className="col-md-6 mb-4">
                <div className="form-floating mb-3">
                  <input
                    type="text"
                    className="form-control"
                    id="location"
                    name="location"
                    onChange={handleFormChange}
                    placeholder="The moon"
                  />
                  <label htmlFor="floatingInput">Location</label>
                </div>
              </div>
              <div className="col-md-6 mb-4">
                <div className="form-floating mb-3">
                  <input
                    type="number"
                    min={0}
                    max={1000000}
                    className="form-control"
                    id="mileage"
                    name="mileage"
                    onChange={handleFormChange}
                    placeholder="20"
                  />
                  <label htmlFor="floatingInput">Mileage in KM</label>
                </div>
              </div>
              <div className="col-md-6 mb-4">
                <div className="form-floating mb-3">
                  <input
                    type="number"
                    min={1950}
                    max={2024}
                    className="form-control"
                    id="carYear"
                    name="carYear"
                    onChange={handleFormChange}
                    placeholder="2022"
                  />
                  <label htmlFor="floatingInput">Vehicle year</label>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="col-lg-6 bgIndigo text-white">
          <div className="p-5">
            <h3 className="fw-normal mb-5">Auction details</h3>

            <div className="mb-4 pb-2">
              <label className="form-label" htmlFor="form3Examplea3">
                Start date
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
                End date
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
              <div className="col-md-6 mb-4 text-black">
                <div className="form-floating mb-3">
                  <input
                    type="number"
                    min={0}
                    max={1000000}
                    className="form-control"
                    id="startingPrice"
                    onChange={handleFormChange}
                    name="startingPrice"
                    placeholder="20"
                  />
                  <label htmlFor="floatingInput">Starting Price</label>
                </div>
              </div>
              <div className="col-md-6 mb-4 text-black">
                <div className="form-floating mb-3">
                  <input
                    type="number"
                    min={0}
                    max={1000000}
                    className="form-control"
                    id="buyoutPrice"
                    onChange={handleFormChange}
                    name="buyoutPrice"
                    placeholder="20"
                  />
                  <label htmlFor="floatingInput">Buyout Price</label>
                </div>
              </div>
            </div>

            <button
              type="submit"
              className="btn btn-dark"
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
