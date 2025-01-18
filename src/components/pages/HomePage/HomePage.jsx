import { motion } from "framer-motion";
import { EducationHighlights } from "./components/EducationHighlight";
import { IntroSection } from "./components/IntroSection";
import { InstructorSection } from "./components/InstructorSection";
import { FeedbackSection } from "./components/FeedbackSection";
import { InfoContact } from "./components/InfoContact";
import { ContactSection } from "./components/ContactSection";
import { ToastContainer } from "react-toastify";
import { useState } from "react";
import { useEffect } from "react";
import { getAdsActive } from "../../../service/AdsService";

export const HomePage = () => {
  const [showPromoModal, setShowPromoModal] = useState(false);
  const [ads, setAds] = useState([]);
  const handleCloseModal = () => {
    setShowPromoModal(false);
  };
  useEffect(() => {
    const fetchAdsActive = async () => {
      try {
        const response = await getAdsActive();
        if (response.data.result && Array.isArray(response.data.result)) {
          setAds(response.data.result);
        } else {
          setAds([]);
        }
      } catch (error) {
        console.log(error);
      }
    };
    fetchAdsActive();
  }, []);
  useEffect(() => {
    const timer = setTimeout(() => {
      setShowPromoModal(true);
    }, 700);
    return () => clearTimeout(timer);
  }, []);
  return (
    <motion.div
      initial={{ opacity: 0, y: 50 }}
      animate={{ opacity: 1, y: 0 }}
      exit={{ opacity: 0, y: 50 }}
      transition={{ duration: 0.5 }}
      className="content-page"
    >
      {showPromoModal && <PromoModal onClose={handleCloseModal} ads={ads} />}

      <EducationHighlights />
      <IntroSection />
      <InstructorSection />
      <FeedbackSection />
      <div className="container-fluid py-5">
        <div className="container py-5">
          <div className="row align-items-center">
            <InfoContact />
            <ContactSection />
          </div>
        </div>
      </div>
      <ToastContainer
        position="top-right"
        autoClose={3000}
        className="add-favorite-toast"
      ></ToastContainer>
    </motion.div>
  );
};
