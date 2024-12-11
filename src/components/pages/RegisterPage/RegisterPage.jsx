import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { RegisterForm } from "./components/RegisterForm";
import {
  checkUserExists,
  registerUser,
  sendOtpRegister,
} from "../../../service/UserService";

export const RegisterPage = () => {
  useEffect(() => {
    document.title = "Register page";
  }, []);

  const [formData, setFormData] = useState({
    email: "",
    password: "",
    firstName: "",
    lastName: "",
    dob: "",
    otp: "",
  });
  const [formErrors, setFormError] = useState({
    email: "",
    password: "",
    firstName: "",
    lastName: "",
    dob: "",
    otp: "",
  });

  const [isOtpSent, setIsOtpSent] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  //Xử lý thay đổi khi giá trị các input
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    setFormError({ ...formErrors, [name]: value ? "" : formErrors[name] });
  };

  //Xử lý lỗi khi để trống
  const handleInputBlur = (e) => {
    const { name, value } = e.target;
    if (!value) {
      setFormError({
        ...formErrors,
        [name]: "This field cannot be left blank",
      });
    }
  };
  const handleRegisterSubmit = async (e) => {
    e.preventDefault();
    if (formData.password.length < 6) {
      setErrorMessage("Password must be at least 6 characters");
      return;
    }
    try {
      //Kiểm tra email đã tồn tại chưa
      const data = await checkUserExists(formData.email);
      if (data.result) {
        setErrorMessage("Email already exists , please try another email");
        return;
      } else {
        //Gửi mã OTP
        const otpData = await sendOtpRegister(formData.email);
        if (otpData.code === 200) {
          setIsOtpSent(true);
          setErrorMessage("");
        } else {
          setErrorMessage("Failed to send OTP , please try again");
        }
      }
    } catch (error) {
      console.error("Error during register:", error);
      setErrorMessage("An error occurred while checking email.");
    }
  };
  const handleOtpSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = await registerUser(formData.otp, {
        email: formData.email,
        password: formData.password,
        firstName: formData.firstName,
        lastName: formData.lastName,
        dob: formData.dob,
      });
      if (data.result) {
        console.log("Register success");
        navigate("/login");
      } else {
        setErrorMessage("Invalid OTP , please try again");
        console.error("Error during registration:", data.message);
      }
    } catch (error) {
      console.error("Error during registration:", error);
      setErrorMessage("An error occurred while registering.");
    }
  };

  return (
    <motion.div
      initial={{ opacity: 0, x: 100 }} // Hiệu ứng ban đầu: ẩn và dịch phải
      animate={{ opacity: 1, x: 0 }} // Hiệu ứng khi hiển thị: hiện và dịch về vị trí gốc
      exit={{ opacity: 0, x: -100 }} // Hiệu ứng khi thoát: ẩn và dịch trái
      transition={{ duration: 0.5 }} // Thời gian chuyển động
      className="content-page"
    >
      <section className="py-3 py-md-5 py-xl-8">
        <div className="container">
          <div className="row">
            <div className="col-12">
              <div className="mb-5">
                <h2 className="display-5 fw-bold text-center">Register</h2>
                <p className="text-center m-0">
                  Already have an account? <Link to="/login">Sign in</Link>
                </p>
              </div>
            </div>
          </div>

          <RegisterForm
            handleRegisterSubmit={handleRegisterSubmit}
            errorMessage={errorMessage}
            handleOtpSubmit={handleOtpSubmit}
            formData={formData}
            handleInputChange={handleInputChange}
            handleInputBlur={handleInputBlur}
            formErrors={formErrors}
            isOtpSent={isOtpSent}
          />
        </div>
      </section>
    </motion.div>
  );
};
