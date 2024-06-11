import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Page404.css";
function Page404() {
  const navigate = useNavigate();

  useEffect(() => {
    setTimeout(() => {
      navigate("/");
    }, 3000);
  }, [navigate]);

  return (
    <div className="page-404 page">
      <h1>404</h1>
      <p>Page not found!</p>
      <div className="redirect-message">Redirecting to main page...</div>
    </div>
  );
}

export default Page404;
