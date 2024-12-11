import React from "react";
import { GiMoneyStack } from "react-icons/gi";
import { Link } from "react-router-dom";

export const ViewRevenue = ({ role }) => {
  return (
    role === "TEACHER" && (
      <div className="nav-item mx-2">
        <Link to="/revenue">
          <button
            className="btn btn-light rounded-circle d-flex align-items-center justify-content-center"
            style={{ width: "40px", height: "40px" }}
          >
            <span style={{ fontSize: "20px" }}>
              <GiMoneyStack />
            </span>
          </button>
        </Link>
      </div>
    )
  );
};
