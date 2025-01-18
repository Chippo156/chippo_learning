export const InfoContact = () => {
  return (
    <div className="col-lg-5 mb-5 mb-lg-0">
      <div
        className="bg-light d-flex flex-column justify-content-center px-5 ads-info-section"
        style={{ height: "520px" }}
      >
        {" "}
        <div className="d-flex align-items-center mb-5">
          <div className="btn-icon ads-icon bg-primary mr-4">
            <i className="fa fa-2x fa-map-marker-alt text-white"></i>
          </div>
          <div className="mt-n1">
            <h4>Our Location</h4>
            <p className="m-0">Ngu Hanh Son, Da Nang, VN</p>
          </div>
        </div>
        <div className="d-flex align-items-center mb-5">
          <div className="btn-icon ads-icon bg-secondary mr-4">
            <i className="fa fa-2x fa-phone-alt text-white"></i>
          </div>
          <div className="mt-n1">
            <h4>Call Us</h4>
            <p className="m-0">+038888888</p>
          </div>
        </div>
        <div className="d-flex align-items-center">
          <div className="btn-icon ads-icon bg-warning mr-4">
            <i className="fa fa-2x fa-envelope text-white"></i>
          </div>
          <div className="mt-n1">
            <h4>Email Us</h4>
            <p className="m-0">hiepcode@gmail.com</p>
          </div>
        </div>
      </div>
    </div>
  );
};
