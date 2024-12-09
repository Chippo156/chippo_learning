import { useContext, useEffect, useRef } from "react";
import { NavLink, useLocation } from "react-router-dom";
import { NavigationMenu } from "../widgets/NavigationMenu";
import AuthContext from "../context/AuthProvider";
import LoadingSpinner from "../../utils/LoadingSpinner";

export const Header = () => {
  const authContext = useContext(AuthContext);
  const location = useLocation();
  const underlineRef = useRef(null);

  // const { handleLogout } = null;

  // const { role, loading: authLoading } = useAuthData();
  // const {
  //   notifications,
  //   unreadCount,
  //   markAsRead,
  //   loading: notificationLoading,
  // } = useNotification(wsClient);
  // const { avatar, points, loading: profileLoading } = useUserProfile();

  // // Kiểm tra trạng thái loading
  // const loading = authLoading || notificationLoading || profileLoading;

  // if (loading) {
  //   return <LoadingSpinner />;
  // }

  useEffect(() => {
    const activeLink = document.querySelector(`.nav-item.active`);
    if (activeLink && underlineRef.current) {
      underlineRef.current.style.left = `${activeLink.offsetLeft}px`;
      underlineRef.current.style.width = `${activeLink.offsetWidth}px`;
    }
  }, [location.pathname]);
  return (
    <div className="header-page">
      <div className="container-fluid p-0">
        <nav className="navbar navbar-expand-lg bg-white navbar-light py-3 py-lg-0 px-lg-5">
          <NavLink to="/home" className="navbar-brand ml-lg-3">
            <h1 className="m-0 text-uppercase text-primary rounded">
              <i className="fa fa-book-reader mr-3"></i>D-LEARNING
            </h1>
          </NavLink>
          {/* <button
            type="button"
            className="navbar-toggler rounded"
            data-bs-toggle="collapse"
            data-bs-target="#navbarCollapse"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div
            className="collapse navbar-collapse justify-content-between px-lg-3"
            id="navbarCollapse"
          >
            <NavigationMenu
              isActive={(path) => location.pathname === path}
              underlineRef={underlineRef}
            ></NavigationMenu>
            <div className="navbar-nav ml-auto d-flex align-items-center">
              <div className="nav-item d-flex align-items-center mx-3">
                <span className="points-display text-primary">
                  <i className="fa fa-coins"></i> {points}
                </span>
              </div>

              <NotificationDropdown
                notifications={notifications}
                unreadCount={unreadCount}
                markAsRead={markAsRead}
              />
              <Advertisement />
              <Favorites role={role} />
              <ViewRevenue role={role} />
              <ProfileDropdown
                avatar={avatar || avatarDefault}
                isTokenValid={authContext.authenticated}
                role={role}
                handleLogout={handleLogout}
              />
            </div>
          </div> */}
        </nav>
      </div>
    </div>
  );
};
