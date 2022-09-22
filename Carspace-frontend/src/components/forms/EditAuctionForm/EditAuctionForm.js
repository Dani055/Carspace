import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./EditAuctionForm.css"
import DateTimePicker from "react-datetime-picker";

function EditAuctionForm(props) {
    const [startDate, onChangeStartDate] = useState(new Date());
    const [endDate, onChangeEndDate] = useState(new Date());

    const testForm = (e) => {
        e.preventDefault()
        console.log(startDate);
      }
  return (
    <form onSubmit={testForm}>
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
                multiple
              />
            </div>

            <div className="row">
              <div className="col-md-6 mb-4">
                <div className="form-floating mb-3">
                  <input
                    type="text"
                    className="form-control"
                    id="brand"
                    name="brand"
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
                    id="model"
                    name="model"
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
                  id="description"
                  name="description"
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
                    id="year"
                    name="year"
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
                  id="startDate"
                  name="startDate"
                  onChange={onChangeStartDate}
                  value={startDate}
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
                  id="endDate"
                  name="endDate"
                  onChange={onChangeEndDate}
                  value={endDate}
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
