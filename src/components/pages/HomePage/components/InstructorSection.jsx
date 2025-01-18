import { Link } from "react-router-dom";
import ImageInstructor1 from "../../../../img/hiep.jpg";
import ImageInstructor2 from "../../../../img/trinh.jpg";

export const InstructorSection = () => {
  return (
    <div className="container-fluid py-5">
      <div className="container py-5">
        <div className="section-title text-center position-relative mb-5">
          <h6 className="d-inline-block position-relative text-secondary pb-2 text-uppercase pb-2">
            Instructors
          </h6>
          <h1 className="display-4">Meet Our Instructors</h1>
        </div>
        <div className="row">
          <div className="col-lg-6 col-md-6 mb-4">
            <div className="team-item">
              <img
                className="img-fluid w-100"
                src={ImageInstructor1}
                alt="Instructor 1"
              ></img>
              <div className="bg-light text-center p-4">
                <h5 className="mb-3">Instructor Name</h5>
                <p className="mb-2">Web Design & Development</p>
                <div className="d-flex justify-content-center">
                  <Link to="/" className="mx-1 p-1">
                    <i className="fab fa-twitter"></i>
                  </Link>
                  <Link to="/" className="mx-1 p-1">
                    <i className="fab fa-facebook-f"></i>
                  </Link>
                  <Link to="/" className="mx-1 p-1">
                    <i className="fab fa-linkedin-in"></i>
                  </Link>
                  <Link to="/" className="mx-1 p-1">
                    <i className="fab fa-instagram"></i>
                  </Link>
                  <Link to="/" className="mx-1 p-1">
                    {" "}
                    <i className="fab fa-youtube"></i>
                  </Link>
                </div>
              </div>
            </div>
          </div>
          <div className="col-lg-6 col-md-6 mb-4">
            <div className="team-item">
              <img
                className="img-fluid w-100"
                src={ImageInstructor2}
                alt="Instructor 1"
              ></img>
              <div className="bg-light text-center p-4">
                <h5 className="mb-3">Instructor Name</h5>
                <p className="mb-2">Web Design & Development</p>
                <div className="d-flex justify-content-center">
                  <Link to="/" className="mx-1 p-1">
                    <i className="fab fa-twitter"></i>
                  </Link>
                  <Link to="/" className="mx-1 p-1">
                    <i className="fab fa-facebook-f"></i>
                  </Link>
                  <Link to="/" className="mx-1 p-1">
                    <i className="fab fa-linkedin-in"></i>
                  </Link>
                  <Link to="/" className="mx-1 p-1">
                    <i className="fab fa-instagram"></i>
                  </Link>
                  <Link to="/" className="mx-1 p-1">
                    {" "}
                    <i className="fab fa-youtube"></i>
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
