import React from "react";
import { Header } from "../components/layouts/Header";
import { Outlet, useLocation } from "react-router-dom";
import { LoginPage } from "../components/pages/LoginPage/LoginPage";
import { TopBar } from "../components/layouts/TopBar";
import { Footer } from "../components/layouts/Footer";
import { Banner } from "../components/layouts/Banner";

export const MainLayout = () => {
  const location = useLocation();

  return (
    <div>
      <TopBar />
      <Header />
      <Banner />
      <Outlet />
      <Footer />
    </div>
  );
};
