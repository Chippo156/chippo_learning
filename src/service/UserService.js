import axios from "../utils/CustomizeAxios";

export const sendOtpRegister = async (email) => {
  try {
    const response = await axios.post(`api/v1/send-otp-register`, {
      email: email,
    });
    console.log(response);
    return response.data;
  } catch (error) {
    console.log("Error sendOtpRegister", error);
    throw error;
  }
};
export const checkUserExists = async (email) => {
  try {
    const response = await axios.post(`api/v1/check-exists-user`, {
      email: email,
    });
    console.log(response);
    return response.data;
  } catch (error) {
    console.error("User not existed", error);
    throw error;
  }
};

export const registerUser = async (otp, userData) => {
  try {
    const response = await axios.post(`api/v1/register`, userData, {
      params: {
        otp: otp,
      },
    });
    console.log(response);
    return response.data;
  } catch (error) {
    console.error("Error create user", error);
    throw error;
  }
};
