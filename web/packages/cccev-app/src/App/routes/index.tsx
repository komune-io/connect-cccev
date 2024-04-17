import { Router } from "@komune-io/g2-providers";
import CertificateFiller from "App/Pages/CertificateFiller";
import { ReactFlowTest } from "App/Pages/ReactFlowTest";
import { Route } from "react-router-dom";

export const AppRouter = () => {
  return (
    <Router>
      <Route path="/" element={<CertificateFiller/>} />
      <Route path="/flow" element={<ReactFlowTest />} />
    </Router>
  );
};
