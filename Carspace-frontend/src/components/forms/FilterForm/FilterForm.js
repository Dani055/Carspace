import React from "react";
import years from "../../../years";

function FilterForm(props) {

    return (
        <form>
        <h4 className="border-bottom p-2">Filters:</h4>
        <div className="row gx-5 pt-3 align-items-center">
          <div className="col-md-5">
            <div className="row gx-3 mb-3">
              <label htmlFor="inputYearMin" className="col-auto col-form-label">
                Year: from
              </label>
              <div className="col-md-4 me-auto">
                <select defaultValue={"Min"} className="form-select" aria-label="Default select example">
                  {
                    years.map((number) => ( 
                      <option value={number}>{number}</option>
                    ))
                  };  
                </select>
              </div>
              <label htmlFor="" className="col-auto col-form-label">
                To
              </label>
              <div className="col-md-4  ">
                <select defaultValue={"Max"} className="form-select" aria-label="Default select example">
                  {
                    years.map((number) => ( 
                      <option value={number}>{number}</option>
                    ))
                  }; 
                </select>
              </div>
            </div>

            <div className="row gx-3 mb-3">
              <label htmlFor="inputYearMin" className="col-auto col-form-label">
                Price: from
              </label>
              <div className="col-md-4 me-auto">
                <input
                  type="number"
                  min={0}
                  max={2000000}
                  className="form-control"
                  id="exampleFormControlInput1"
                  placeholder="€2000"
                />
              </div>
              <label htmlFor="" className="col-auto col-form-label">
                To
              </label>
              <div className="col-md-4">
                <input
                  type="number"
                  min={0}
                  max={2000000}
                  className="form-control"
                  placeholder="€4000"
                  id="exampleFormControlInput1"
                />
              </div>
            </div>

            <div className="row gx-3 mb-3">
              <label htmlFor="inputYearMin" className="col-auto col-form-label">
                Mileage: from
              </label>
              <div className="col-md-4 me-auto">
                <input
                  type="number"
                  min={0}
                  max={2000000}
                  className="form-control"
                  id="exampleFormControlInput1"
                  placeholder="20km"
                />
              </div>
              <label htmlFor="" className="col-auto col-form-label">
                To
              </label>
              <div className="col-md-4">
                <input
                  type="number"
                  min={0}
                  max={2000000}
                  className="form-control"
                  placeholder="220 000km"
                  id="exampleFormControlInput1"
                />
              </div>
            </div>
          </div>

          <div className="col-md-5">
            <div className="mb-3">
              <label htmlFor="formGroupExampleInput" className="form-label">
                Search
              </label>
              <input
                type="text"
                className="form-control mb-2"
                id="formGroupExampleInput"
                placeholder="BMW, Ford, Toyota..."
              />
              <label htmlFor="formGroupExampleInput" className="form-label">
                Location
              </label>
              <input
                type="text"
                className="form-control"
                id="formGroupExampleInput"
                placeholder="Eindhoven, Netherlands"
              />
            </div>
          </div>

          <div className="col-md-2">
            <button className="btn btn-primary btn-lg">Search</button>
          </div>
        </div>
      </form>
    )
}

export default FilterForm;