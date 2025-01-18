import { TypeAnimation } from "react-type-animation";

export const Banner = () => {
  return (
    <div className="content-page">
      <div className="jumbotron jumbotron-fluid position-relative overlay-bottom banner-container">
        <div className="container text-center my-5 py-5 container-banner-text">
          <h1 className="text-white mt-4 mb-4 banner-subheading">
            <TypeAnimation
              sequence={[
                "Learn From The Best",
                1000,
                "Study Anywhere, Anytime",
                1000,
                "Master New Skills Today",
                1000,
              ]}
              wrapper="span"
              speed={50}
              style={{ display: "inline-block" }}
              repeat={Infinity}
            ></TypeAnimation>
          </h1>
          <h1 className="text-white display-1 mb-5 banner-heading">
            <TypeAnimation
              sequence={[
                "Education Courses",
                2000,
                "Online Learning Platform",
                2000,
                "Explore Our Courses Now",
                2000,
              ]}
              wrapper="span"
              speed={50}
              style={{ display: "inline-block" }}
              repeat={Infinity}
            ></TypeAnimation>
          </h1>
        </div>
      </div>
    </div>
  );
};
