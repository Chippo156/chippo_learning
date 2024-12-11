import { Outlet } from "react-router-dom";
import { Header } from "../components/layouts/Header";
import { Footer } from "../components/layouts/Footer";
import { TopBar } from "../components/layouts/TopBar";

export const HeaderAndFooterRouter = () => {
  return (
    <div>
      <TopBar />
      <Header />
      <Outlet />
      <Footer />
    </div>
  );
};
