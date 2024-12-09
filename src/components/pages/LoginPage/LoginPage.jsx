import { useContext, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { introspect, login } from "../../../service/AuthenticationService";
import LoadingSpinner from "../../../utils/LoadingSpinner";
import { motion } from "framer-motion";
import { ToastContainer } from "react-toastify";
import { LoginForm } from "./components/LoginForm";
import AuthContext from "../../context/AuthProvider";

export const LoginPage = () => {
  const location = useLocation();
  const authContext = useContext(AuthContext);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [showToast, setShowToast] = useState(false);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    document.title = "Login Page";
  }, []);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      setLoading(false);
      return;
    }
    if (token) {
      introspect(token)
        .then((data) => {
          if (data.valid) {
            navigate("/home");
          }
        })
        .catch((error) => {
          setLoading(false);
          console.error("Error introspecting token:", error);
        })
        .finally(() => {
          setLoading(false);
        });
    }
  }, [navigate]);
  useEffect(() => {
    if (showToast) {
      const timer = setTimeout(() => setShowToast(false), 3000);
      return () => clearTimeout(timer);
    }
  }, [showToast]);
  const handleLogin = async (event) => {
    event.preventDefault();
    login(email, password).then((data) => {
      if (data && data.result && data.result.token) {
        const token = data.result.token;
        localStorage.setItem("token", token);
        authContext.refresh();
        introspect()
          .then((introspectData) => {
            if (introspectData && introspectData.valid) {
              if (role === "USER") {
                navigate("/home");
              } else if (role === "ADMIN") {
                navigate("/admin");
              } else if (role === "TEACHER") {
                navigate("/manager-courses");
              }
            } else {
              throw new Error("Invalid token.");
            }
          })
          .catch((error) => {
            console.error("Error during introspect:", error);
            setError(error.message);
            setShowToast(true);
            setTimeout(() => setShowToast(false), 4000);
          });
      }
    });
  };
  if (loading) {
    return <LoadingSpinner />;
  }
  if (error) {
    <div>{error}</div>;
  }
  return (
    <motion.div
      initial={{ opacity: 0, x: -100 }} // Hiệu ứng ban đầu: ẩn và dịch trái
      animate={{ opacity: 1, x: 0 }} // Hiệu ứng khi hiển thị: hiện và dịch về vị trí gốc
      exit={{ opacity: 0, x: 100 }} // Hiệu ứng khi thoát: ẩn và dịch phải
      transition={{ duration: 0.5 }} // Thời gian chuyển động
      className="content-page"
    >
      <section className="py-3 py-md-5 py-xl-8">
        <LoginForm
          email={email}
          setEmail={setEmail}
          password={password}
          handleLogin={handleLogin}
          setPassword={setPassword}
        ></LoginForm>
        <ToastContainer
          position="top-right"
          autoClose={3000}
          className="custom-toast-container"
        />
      </section>
    </motion.div>
  );
};
