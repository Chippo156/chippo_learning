import "./App.css";
import { Route, Routes } from "react-router-dom";
import { MainLayout } from "./router/MainLayout";
import { Header } from "./components/layouts/Header";
import { LoginPage } from "./components/pages/LoginPage/LoginPage";
import { HeaderAndFooterRouter } from "./router/HeaderAndFooterRouter";
import { RegisterPage } from "./components/pages/RegisterPage/RegisterPage";

function App() {
  return (
    <div className="App">
      <Routes>
        {/* <Route path="/" element={<MainLayout />}></Route> */}
        <Route element={<HeaderAndFooterRouter />}>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
