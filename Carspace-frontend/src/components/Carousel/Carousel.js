import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Carousel.css"

function Carousel(props) {
  return (
    <div
      id="carouselExampleIndicators"
      className="carousel rounded-top slide"
      data-bs-ride="carousel"
    >
      {
        props.images.length !== 0 &&
        <div className="carousel-indicators">
          {props.images.map((img, index) => {
            if (index === 0) {
              return <button
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide-to={index}
                className="active"
                aria-current="true"
                aria-label={"Slide " + index}
                key={img.id}
              >
              </button>
            }
            else {
              return <button
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide-to={index}
                aria-label={"Slide " + index}
                key={img.id}
              ></button>
            }
          })}
        </div>
      }
      {
        props.images.length !== 0 &&
        <div className="carousel-inner">
          {props.images.map((img, index) => {
            if (index === 0) {
              return <div className="carousel-item active" key={img.id}>
                <img src={img.imgUrl} className="d-block w-100" alt="" />
              </div>
            }
            else {
              return <div className="carousel-item" key={img.id}>
                <img src={img.imgUrl} className="d-block w-100" alt="" />
              </div>
            }
          })}
        </div>
      }
      <button
        className="carousel-control-prev"
        type="button"
        data-bs-target="#carouselExampleIndicators"
        data-bs-slide="prev"
      >
        <span
          className="carousel-control-prev-icon"
          aria-hidden="true"
        ></span>
        <span className="visually-hidden">Previous</span>
      </button>
      <button
        className="carousel-control-next"
        type="button"
        data-bs-target="#carouselExampleIndicators"
        data-bs-slide="next"
      >
        <span
          className="carousel-control-next-icon"
          aria-hidden="true"
        ></span>
        <span className="visually-hidden">Next</span>
      </button>
    </div>
  );
}

export default Carousel;
