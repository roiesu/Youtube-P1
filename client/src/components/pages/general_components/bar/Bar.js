import React, { useEffect, useState } from "react";
import { useLocation, Link, useNavigate } from "react-router-dom";
import "./Bar.css";
import IconHouseDoorFill from "../../../icons/IconHouseDoorFill";
import IconVideoCamera from "../../../icons/IconVideoCamera";
import IconLogout from "../../../icons/IconLogout";
import IconLogin from "../../../icons/IconLogin"
import IconHamburgerMenu from "../../../icons/IconHamburgerMenu";

function Bar({ logout, loggedIn }) {
  const location = useLocation();
  const [visible, setVisible] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    setVisible(!location.pathname.includes("sign"));
    setIsOpen(false);
  }, [location]);

  const toggleSidebar = () => {
    setIsOpen(!isOpen);
  };

  return (
    visible && (
      <div className="sidebar-container">
        <button className="sidebar-toggle" onClick={toggleSidebar}>
          <IconHamburgerMenu />
        </button>
        {isOpen && (
          <div className="sidebar">
            <Link to="/">
              <div className="sidebar-link">
                <IconHouseDoorFill /> Home
              </div>
            </Link>
            <Link to="/MyVideos">
            <Link to="/my-videos">
              <div className="sidebar-link">
                <IconVideoCamera /> My Videos
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
              <>
                <Link to="/sign-up">
                  <div className="sidebar-link">
                    <IconLogin /> Sign Up
                  </div>
                </Link>
                <Link to="/sign-in">
                  <div className="sidebar-link">
                    <IconLogin /> Sign In
                  </div>
                </Link>
              </>
            )}
          </div>
        )}
      </div>
    )
  );
}

export default Bar;
