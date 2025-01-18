import { useCallback } from "react";
import { useState } from "react";

export const PromoModal = ({ onClose, ads }) => {
  const [currentPromoIndex, setCurrentPromoIndex] = useState(0);
  const handleNext = useCallback(() => {
    setCurrentPromoIndex((prev) => (prev + 1) % ads.length);
  }, [ads.length]);
};
