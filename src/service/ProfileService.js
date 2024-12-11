import { a } from "framer-motion/client";
import axios from "../utils/CustomizeAxios";

export const getAvatar = async () => {
  try {
    const response = await axios.get("api/v1/get-avatar");
    return response.data;
  } catch (error) {
    console.error("Error getting avatar: ", error);
    throw new Error(error);
  }
};

export const getPointsByCurrentLogin = async () => {
  try {
    const response = await axios.get(`api/v1/info-user`);
    return response.data;
  } catch (error) {
    console.error("Error getting points: ", error);
    throw new Error(error);
  }
};
