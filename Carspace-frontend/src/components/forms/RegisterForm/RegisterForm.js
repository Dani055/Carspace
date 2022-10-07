import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { registerUserCall } from "../../../service/userService";
import { toast } from 'react-toastify';

function RegisterForm(props) {
  const navigate = useNavigate();
  const [formState, setFormState] = useState({firstName: "", lastName: "", address: "", phone: "", email: "", username: "", password: ""});


  const handleSubmit = async (e) => {
    try {
      e.preventDefault();
      const res = await registerUserCall(formState);
      toast.success(res.message);
      navigate("/login")
    } catch (err) {
      toast.error(err);
    }
  }

  const handleFormChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setFormState({ ...formState, [name]: value });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="row">
        <div className="col-md-6 mb-4">
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              onChange={handleFormChange}
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
              onChange={handleFormChange}
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
              onChange={handleFormChange}
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
              onChange={handleFormChange}
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
              onChange={handleFormChange}
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
              onChange={handleFormChange}
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
              onChange={handleFormChange}
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
