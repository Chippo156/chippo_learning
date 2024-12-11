import { Button } from "react-bootstrap";
import React from "react";
import { Link } from "react-router-dom";

export const ProfileDropdown = ({
  avatar,
  isTokenValid,
  role,
  handleLogout,
}) => {
  return (
    <div className="nav-item dropdown mx-2">
      <button
        className="btn btn-primary rounded-circle d-flex align-items-center justify-content-center p-0"
        style={{ width: "50px", height: "50px", overflow: "hidden" }}
        data-bs-toggle="dropdown"
        aria-expanded="false"
      >
        {avatar ? (
          <img
            src={avatar}
            alt="User Avatar"
            style={{
              width: "100%",
              height: "100%",
              objectFit: "cover",
              borderRadius: "50%",
            }}
          />
        ) : (
          <img
            src="https://bootdey.com/img/Content/avatar/avatar7.png"
            alt="User Avatar"
            style={{
              width: "100%",
              height: "100%",
              objectFit: "cover",
              borderRadius: "50%",
            }}
          />
        )}
      </button>

      <ul className="dropdown-menu dropdown-menu-end text-start">
        {isTokenValid === null ? (
          <li></li>
        ) : isTokenValid ? (
          <>
            <li>
              <Link
                to="/profile"
                className="dropdown-item d-flex align-items-center"
              >
                <i className="fa-solid fa-address-card me-2"></i>Profile
              </Link>
            </li>

            {role === "USER" && (
              <li>
                <Link
                  to="/my-certificates"
                  className="dropdown-item d-flex align-items-center"
                >
                  <i className="fa-solid fa-certificate me-2"></i>Certificates
                </Link>
              </li>
            )}

            {role === "TEACHER" && (
              <li>
                <Link
                  to="/manager-student"
                  className="dropdown-item d-flex align-items-center"
                >
                  <i className="fa-solid fa-user-graduate me-2"></i>Manager
                  Student
                </Link>
              </li>
            )}

            {(role === "USER" || role === "TEACHER") && (
              <li>
                <Link
                  to="/my-courses"
                  className="dropdown-item d-flex align-items-center"
                >
                  <i className="fa-solid fa-book me-2"></i>My Courses
                </Link>
              </li>
            )}

            {role === "TEACHER" && (
              <li>
                <Link
                  to="/manager-courses"
                  className="dropdown-item d-flex align-items-center"
                >
                  <i className="fa-solid fa-book me-2"></i>Manager
                </Link>
              </li>
            )}

            {role === "USER" && (
              <li>
                <Link
                  to="/register-teacher"
                  className="dropdown-item d-flex align-items-center"
                >
                  <i className="fa-solid fa-user-graduate me-2"></i>Teach Now
                </Link>
              </li>
            )}

            {role === "ADMIN" && (
              <li>
                <Link
                  to="/admin"
                  className="dropdown-item d-flex align-items-center"
                >
                  <i className="fa-solid fa-user-tie me-2"></i>Admin
                </Link>
              </li>
            )}

            {(role === "USER" || role === "TEACHER") && (
              <li>
                <Link
                  to="/deposit"
                  className="dropdown-item d-flex align-items-center"
                >
                  <i className="fa-brands fa-bitcoin me-2"></i>
                  Deposit
                </Link>
              </li>
            )}

            <li>
              <Link
                to="/change-password"
                className="dropdown-item d-flex align-items-center"
              >
                <i className="fa-solid fa-key me-2"></i>Password
              </Link>
            </li>

            <li>
              <Button
                className="dropdown-item d-flex align-items-center"
                id="logout"
                onClick={handleLogout}
              >
                <i className="fa-solid fa-sign-out-alt me-2"></i>Logout
              </Button>
            </li>
          </>
        ) : (
          <li>
            <Link
              to="/login"
              className="dropdown-item d-flex align-items-center"
              id="login"
            >
              <i className="fa-solid fa-sign-in-alt me-2"></i>Login
            </Link>
          </li>
        )}
      </ul>
    </div>
  );
};
