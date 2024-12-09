import React from "react";
import { Header } from "../components/layouts/Header";
import { Outlet, useLocation } from "react-router-dom";
import { LoginPage } from "../components/pages/LoginPage/LoginPage";

export const MainLayout = () => {
  const location = useLocation();

  return (
    <div>
      {/* <Header /> */}
      <LoginPage />
      <Outlet />
    </div>
  );
};
