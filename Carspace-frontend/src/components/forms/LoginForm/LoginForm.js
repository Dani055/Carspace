import React from "react";

function LoginForm(props) {
  return (
    <form>
      <div className="form-floating mb-3 text-start">
        <input
          type="username"
          className="form-control"
          id="username"
          name="username"
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
