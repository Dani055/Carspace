import React from "react";
import styles from "./NavBar.module.css";
import { NavLink, Link, useNavigate } from "react-router-dom";

function NavBar() {
  const navigate = useNavigate();
  const links = [
    {
      id: 1,
      path: "/",
      text: "Todo List",
    },
    {
      id: 2,
      path: "/user",
      text: "User",
    },
  ];

  return (
    <nav className="navbar navbar-expand-lg navbar-dark">
        <div className="container">
          <NavLink className={`navbar-brand ${styles.logo}`} to="/">CarSpace</NavLink>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                <li className="nav-item dropdown">
                    <NavLink className="nav-link dropdown-toggle" to="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                      Auctions
                    </NavLink>
                    <ul className="dropdown-menu" aria-labelledby="navbarDropdown">
                      <li><Link className="dropdown-item" to="#">Live auctions</Link></li>
                      <li><hr className="dropdown-divider"/></li>
                      <li><Link className="dropdown-item" to="#">Ended actions</Link></li>
                    </ul>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/auction/create">Sell a car</NavLink>
              </li>
            </ul>
            <ul className="navbar-nav">
            <span className="navbar-text mx-3">
                Welcome, --user--
            </span>
              <li className="nav-item">
                <NavLink className="nav-link" to="/CreateAuction">My profile</NavLink>
              </li> 
            </ul>
            <button type="button" onClick={() => navigate("/login")} className="btn btn-dark mx-2">Log in</button>
            <button type="button" className="btn btn-dark mx-2">Log out</button>
            <button type="button" onClick={() => navigate("/register")} className="btn btn-outline-dark">Register</button>
          </div>
        </div>
    </nav>
  );
}

export default NavBar;
