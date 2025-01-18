import testimonial1Image from "./../../../../img/testimonial-1.jpg";
import testimonial2Image from "./../../../../img/testimonial-2.jpg";

export const FeedbackSection = () => {
  return (
    <div className="container-fluid bg-image py-5" style={{ margin: "90px 0" }}>
      <div className="container py-5">
        <div className="row align-items-center">
          <div className="col-lg-5 mb-5 mb-lg-0">
            <div className="section-title position-relative mb-4">
              <h6 className="d-inline-block position-relative text-secondary text-uppercase pb-2">
                Testimonial
              </h6>
              <h1 className="display-4">What Say Our Students</h1>
            </div>
            <p className="m-0">
              Dolor est dolores et nonumy sit labore dolores est sed rebum amet,
              justo duo ipsum sanctus dolore magna rebum sit et. Diam lorem ea
              sea at. Nonumy et at at sed justo est nonumy tempor. Vero sea ea
              eirmod, elitr ea amet diam ipsum at amet. Erat sed stet eos ipsum
              diam
            </p>
          </div>
          <div className="col-lg-7">
            <div className="owl-carousel testimonial-carousel">
              <div className="bg-white p-5">
                <i className="fa fa-3x fa-quote-left text-primary mb-4"></i>
                <p>
                  Sed et elitr ipsum labore dolor diam, ipsum duo vero sed sit
                  est est ipsum eos clita est ipsum. Est nonumy tempor at kasd.
                  Sed at dolor duo ut dolor, et justo erat dolor magna sed stet
                  amet elitr duo lorem
                </p>
                <div className="d-flex flex-shrink-0 align-items-center mt-4">
                  <img
                    className="img-fluid mr-4"
                    src={testimonial1Image}
                    alt="Student 1"
                  />
                  <div>
                    <h5>Student Name</h5>
                    <span>Web Design</span>
                  </div>
                </div>
              </div>
              <div className="bg-white p-5">
                <i className="fa fa-3x fa-quote-left text-primary mb-4"></i>
                <p>
                  Sed et elitr ipsum labore dolor diam, ipsum duo vero sed sit
                  est est ipsum eos clita est ipsum. Est nonumy tempor at kasd.
                  Sed at dolor duo ut dolor, et justo erat dolor magna sed stet
                  amet elitr duo lorem
                </p>
                <div className="d-flex flex-shrink-0 align-items-center mt-4">
                  <img
                    className="img-fluid mr-4"
                    src={testimonial2Image}
                    alt="Student 2"
                  />
                  <div>
                    <h5>Student Name</h5>
                    <span>Web Design</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
