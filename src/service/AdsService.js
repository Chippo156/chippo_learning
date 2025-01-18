import axios from "../utils/CustomizeAxios";

export const getAdsActive = async () => {
  return axios.get(`api/v1/get-ads-active`);
};
