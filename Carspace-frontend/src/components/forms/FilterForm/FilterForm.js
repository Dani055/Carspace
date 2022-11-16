import React, { useState } from "react";
import years from "../../../years";

function FilterForm(props) {
  const [formState, setFormState] = useState({ carBrand: "", carModel: "", location: "", minYear: 0, maxYear: 3000, minPrice: 0, maxPrice: 214748364, minMileage: 0, maxMileage: 2000000 });

  const handleFormChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setFormState({ ...formState, [name]: value });
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    props.applyFilters(formState);
  }

  return (
    <div className="accordion" id="filtersAccordion">
      <h2 className="accordion-header" id="headingOne">
        <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFilters" aria-expanded="false" aria-controls="collapseFilters">
          <h4>Filters:</h4>
        </button>
      </h2>
      <div id="collapseFilters" className="accordion-collapse collapse" aria-labelledby="headingOne" data-bs-parent="#filtersAccordion">
        <div className="accordion-body">
        <form onSubmit={handleSubmit}>
        <div className="row gx-5 align-items-center">
          <div className="col-md-5">
            <div className="row gx-3 mb-3">
              <label htmlFor="inputYearMin" className="col-auto col-form-label">
                Year: from
              </label>
              <div className="col-md-4 me-auto">
                <select name="minYear" id="inputYearMin" onChange={handleFormChange} defaultValue={0} className="form-select" aria-label="Default select example">
                  {
                    years.map((number) => ( 
                      <option value={number} key={number}>{number}</option>
                    ))
                  };  
                </select>
              </div>
              <label htmlFor="inputYearMax" className="col-auto col-form-label">
                To
              </label>
              <div className="col-md-4  ">
                <select id="inputYearMax" onChange={handleFormChange} name="maxYear" defaultValue={0} className="form-select" aria-label="Default select example">
                  {
                    years.map((number) => ( 
                      <option value={number} key={number}>{number}</option>
                    ))
                  }; 
                </select>
              </div>
            </div>

            <div className="row gx-3 mb-3">
              <label htmlFor="inputPriceMin" className="col-auto col-form-label">
                Price: from
              </label>
              <div className="col-md-4 me-auto">
                <input
                  type="number"
                  min={0}
                  max={2000000}
                  onChange={handleFormChange}
                  className="form-control"
                  name="minPrice"
                  id="inputPriceMin"
                  placeholder="€2000"
                />
              </div>
              <label htmlFor="inputPriceMax" className="col-auto col-form-label">
                To
              </label>
              <div className="col-md-4">
                <input
                  type="number"
                  min={0}
                  max={2000000}
                  onChange={handleFormChange}
                  name="maxPrice"
                  className="form-control"
                  placeholder="€4000"
                  id="inputPriceMax"
                />
              </div>
            </div>

            <div className="row gx-3 mb-3">
              <label htmlFor="inputMileageMin" className="col-auto col-form-label">
                Mileage: from
              </label>
              <div className="col-md-4 me-auto">
                <input
                  type="number"
                  min={0}
                  max={2000000}
                  onChange={handleFormChange}
                  name="minMileage"
                  className="form-control"
                  id="inputMileageMin"
                  placeholder="20 km"
                />
              </div>
              <label htmlFor="inputMileageMax" className="col-auto col-form-label">
                To
              </label>
              <div className="col-md-4">
                <input
                  type="number"
                  min={0}
                  max={2000000}
                  onChange={handleFormChange}
                  name="maxMileage"
                  className="form-control"
                  placeholder="220 000 km"
                  id="inputMileageMax"
                />
              </div>
            </div>
          </div>

          <div className="col-md-5">
            <div className="mb-3">
              <label htmlFor="brand" className="form-label">
                Brand
              </label>
              <input
                type="text"
                className="form-control mb-2"
                name="carBrand"
                onChange={handleFormChange}
                id="brand"
                placeholder="BMW, Ford, Toyota..."
              />
              <label htmlFor="model" className="form-label">
                Vehicle model
              </label>
              <input
                type="text"
                className="form-control"
                id="model"
                onChange={handleFormChange}
                name="carModel"
                placeholder="Land cruiser"
              />
              <label htmlFor="location" className="form-label">
                Location
              </label>
              <input
                type="text"
                className="form-control"
                onChange={handleFormChange}
                id="location"
                name="location"
                placeholder="Eindhoven, Netherlands"
              />
            </div>
          </div>

          <div className="col-md-2">
            <button type="submit" className="btn btn-primary btn-lg">Search</button>
          </div>
        </div>
      </form>
        </div>
      </div>
    </div>

  )
}

export default FilterForm;