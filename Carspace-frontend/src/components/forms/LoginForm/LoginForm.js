import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { loginUserCall } from "../../../service/userService";
import { toast } from 'react-toastify';
import { UserContext } from "../../../UserProvider";
import { useCookies } from 'react-cookie';

function LoginForm(props) {
  const navigate = useNavigate();
  const [formState, setFormState] = useState({username: "", password: ""});
  const {setLoggedUser} = useContext(UserContext)
  const [cookies, setCookie] = useCookies();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await loginUserCall(formState);
      toast.success(res.message);
      setCookie('token', res.obj.id, {maxAge: 180});
      setLoggedUser(res.obj);
      navigate("/");

    } 
    catch (err) {
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
      <div className="form-floating mb-3 text-start">
        <input
          type="username"
          className="form-control"
          id="username"
          name="username"
          onChange={handleFormChange}
          placeholder="name@example.com"
        />
        <label htmlFor="floatingInput">Username</label>
      </div>

      <div className="form-floating mb-3 text-start">
        <input
          type="password"
          className="form-control"
          id="password"
          name="password"
          onChange={handleFormChange}
          placeholder="Password"
        />
        <label htmlFor="floatingPassword">Password</label>
      </div>

      <p className="small mb-4 pb-lg-2">
        <a href="#!">Forgot password?</a>
      </p>

      <button className="btn btn-outline-dark px-5" type="submit">
        Login
      </button>
    </form>
  );
}

export default LoginForm;
