import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Page404() {
  const navigate = useNavigate();
  useEffect(() => {
    setTimeout(() => {
      navigate("/");
    }, 3000);
  });
  return (
    <div className="page-404 page">
      Page not found!
      <br /> Redirecting to main page.
    </div>
  );
}

export default Page404;
