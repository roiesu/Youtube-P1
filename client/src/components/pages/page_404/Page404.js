import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useTheme } from "../general_components/ThemeContext";
import "./Page404.css";
function Page404() {
  const navigate = useNavigate();
  const { theme } = useTheme();

  useEffect(() => {
    const timeout = setTimeout(() => {
      navigate("/");
    }, 3000);
    return () => {
      clearTimeout(timeout);
    };
  }, []);

  return (
    <div className={`page-404 page ${theme}`}>
      <h1>404</h1>
      <p>Page not found!</p>
      <div className="redirect-message">Redirecting to main page...</div>
    </div>
  );
}

export default Page404;
