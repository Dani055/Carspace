import React from "react";
import { Link } from "react-router-dom";

function RegisterForm(props) {
  return (
    <form>
      <div className="row">
        <div className="col-md-6 mb-4">
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="firstName"
              name="firstName"
              placeholder="name@example.com"
            />
            <label htmlFor="floatingInput">First name</label>
          </div>
        </div>
        <div className="col-md-6 mb-4">
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="lastName"
              name="lastName"
              placeholder="name@example.com"
            />
            <label htmlFor="floatingInput">Last name</label>
          </div>
        </div>
      </div>

      <div className="row border-bottom mb-4">
        <div className="col-md-6">
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="address"
              name="address"
              placeholder="name@example.com"
            />
            <label htmlFor="floatingInput">Address</label>
          </div>
        </div>
        <div className="col-md-6">
          <div className="form-floating mb-3">
            <input
              type="tel"
              className="form-control"
              id="phone"
              name="phone"
              placeholder="name@example.com"
            />
            <label htmlFor="floatingInput">Phone number</label>
          </div>
        </div>
      </div>

      <div className="row">
        <div className="col-md-6 mb-4">
          <div className="form-floating mb-3">
            <input
              type="email"
              className="form-control"
              id="email"
              name="email"
              placeholder="name@example.com"
            />
            <label htmlFor="floatingInput">Email</label>
          </div>
        </div>
        <div className="col-md-6 mb-4">
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="username"
              name="username"
              placeholder="name@example.com"
            />
            <label htmlFor="floatingInput">Username</label>
          </div>
        </div>
      </div>

      <div className="row justify-content-center">
        <div className="col-md-6 mb-4">
          <div className="form-floating mb-3">
            <input
              type="password"
              className="form-control"
              id="password"
              name="password"
              placeholder="name@example.com"
            />
            <label htmlFor="floatingInput">Password</label>
          </div>
        </div>
      </div>

      <div className="row">
        <button className="btn btn-outline-dark px-5" type="submit">
          Register
        </button>
      </div>

      <div className="row justify-content-center mt-4">
        <div className="col-md-6 text-center">
          Already have an account?
          <Link to="/login" className="fw-bold ms-2">
            Sign in
          </Link>
        </div>
      </div>
    </form>
  );
}

export default RegisterForm;
