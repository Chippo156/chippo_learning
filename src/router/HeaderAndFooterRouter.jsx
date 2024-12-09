// import { Outlet } from "react-router-dom";
// import { TopBar } from "../layouts/TopBar";
// import { Footer } from "../layouts/Footer";
// import { Header } from "../layouts/Header";

import { Outlet } from "react-router-dom";
import { Header } from "../components/layouts/Header";

export const HeaderAndFooterRouter = () => {
  return (
    <div>
      {/* <TopBar /> */}
      <Header />
      <Outlet />
      {/* <Footer /> */}
    </div>
  );
};
