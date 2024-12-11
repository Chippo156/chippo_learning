import { useContext, useEffect, useState } from "react";
import AuthContext from "../components/context/AuthProvider";
import { getAvatar, getPointsByCurrentLogin } from "../service/ProfileService";

export const useUserProfile = () => {
  const authContext = useContext(AuthContext);
  const [avatar, setAvatar] = useState(null);
  const [points, setPoints] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!authContext.authenticated) {
      setAvatar(null);
      setPoints(0);
      setLoading(false);
      return;
    }
    const fetchUserProfile = async () => {
      try {
        const avatarData = await getAvatar();
        setAvatar(avatarData.result);
        const pointsData = await getPointsByCurrentLogin();
        setPoints(pointsData.result.points);
      } catch (error) {
        console.error("Error fetching user profile:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchUserProfile();
  }, [authContext]);
  return { avatar, points, loading };
};
