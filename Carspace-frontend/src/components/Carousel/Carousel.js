import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Carousel.css"

function Carousel(props) {
  return (
          <div
            id="carouselExampleIndicators"
            className={`carousel slide`}
            data-bs-ride="carousel"
          >
            <div class="carousel-indicators">
              <button
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide-to="0"
                class="active"
                aria-current="true"
                aria-label="Slide 1"
              ></button>
              <button
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide-to="1"
                aria-label="Slide 2"
              ></button>
              <button
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide-to="2"
                aria-label="Slide 3"
              ></button>
            </div>
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img src="https://autoarp.nl/wp-content/uploads/2022/05/BMW-330-1-van-23-scaled.jpg" class="d-block w-100" alt="" />
              </div>
              <div class="carousel-item">
                <img src="https://www.bmw-vergelijker.nl/wp-content/uploads/2020/05/100354487_296656154827984_1968978154273898496_n-scaled.jpg" class="d-block w-100" alt="" />
              </div>
              <div class="carousel-item">
                <img src="https://i.pinimg.com/1200x/60/0f/e1/600fe1088953132a957942d814a92c02.jpg" class="d-block w-100" alt="" />
              </div>
            </div>
            <button
              class="carousel-control-prev"
              type="button"
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide="prev"
            >
              <span
                class="carousel-control-prev-icon"
                aria-hidden="true"
              ></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button
              class="carousel-control-next"
              type="button"
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide="next"
            >
              <span
                class="carousel-control-next-icon"
                aria-hidden="true"
              ></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
  );
}

export default Carousel;
