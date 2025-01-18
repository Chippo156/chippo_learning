export const ContactSection = () => {
  return (
    <div className="col-lg-7">
      <div className="section-title position-relative mb-4">
        <h6 className="d-inline-block position-relative text-secondary text-uppercase pb-2">
          Need Help?
        </h6>
        <h1 className="display-4">Send Us A Message</h1>
      </div>
      <div className="contact-form">
        <form>
          <div className="row">
            <div className="col-6 form-group">
              <input
                type="text"
                className="form-control border-top-0 border-right-0 border-left-0 p-0"
                placeholder="Your Name"
                required="required"
              />
            </div>
            <div className="col-6 form-group">
              <input
                type="email"
                className="form-control border-top-0 border-right-0 border-left-0 p-0"
                placeholder="Your Email"
                required="required"
              />
            </div>
          </div>
          <div className="form-group">
            <input
              type="text"
              className="form-control border-top-0 border-right-0 border-left-0 p-0"
              placeholder="Subject"
              required="required"
            />
          </div>
          <div className="form-group">
            <textarea
              className="form-control border-top-0 border-right-0 border-left-0 p-0"
              rows="5"
              placeholder="Message"
              required="required"
            ></textarea>
          </div>
          <div>
            <button className="btn btn-primary py-3 px-5" type="submit">
              Send Message
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
