import React, { useEffect, useState } from "react";
import { useLocation, Link, useNavigate } from "react-router-dom";
import "../bar.css";
import IconHouseDoorFill from "../../../icons/IconHouseDoorFill";
import IconVideoCamera from "../../../icons/IconVideoCamera";
import IconLogout from "../../../icons/IconLogout";
import IconLogin from "../../../icons/Iconlogin";

function Bar({ logout, loggedIn }) {
  const location = useLocation();
  const [visible, setVisible] = useState(false);
  const navigate = useNavigate();
  useEffect(() => {
    if (!location.pathname.match("sign")) {
      setVisible(true);
    } else {
      setVisible(false);
    }
  }, [location]);

  const toggleSidebar = () => {
    setVisible(!visible);
  };

  return (
    <div className="sidebar-container">
      <button className="sidebar-toggle" onClick={toggleSidebar}>
        {visible ? "Close" : "Open"}
      </button>
      {visible && (
        <div className="sidebar">
          <Link to="/">
            <div className="sidebar-link">
              <IconHouseDoorFill /> Home
            </div>
          </Link>
          <Link to="/upload">
            <div className="sidebar-link">
              <IconVideoCamera /> MyVideos
            </div>
          </Link>
          {loggedIn ? (
            <div
              className="sidebar-link"
              onClick={() => {
                logout();
                navigate("/sign-in");
              }}
            >
              <IconLogout /> Sign out
            </div>
          ) : (
            <div className="sidebar-link">
              <Link to="/sign-up">
                <IconLogin /> Sign Up
              </Link>
            </div>
          )}
        </div>
      )}
    </div>
  );
}

export default Bar;
