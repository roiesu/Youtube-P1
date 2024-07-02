import React, { useEffect, useRef, useState } from "react";
import axios from "axios";
import VideoLink from "./main_page_components/VideoLink";
import { callWithEnter, getMediaFromServer } from "../../../utilities";
import "./MainPage.css";
import { Link } from "react-router-dom";
import { useTheme } from "../general_components/ThemeContext";
import { useNavigate } from 'react-router-dom';
import IconSun from "../../icons/IconSun";
import IconMoon from "../../icons/IconMoon";


function MainPage({ currentUser }) {
  const navigate = useNavigate(); 
  const { theme, changeTheme } = useTheme();
  const searchInputRef = useRef(null);
  const [filteredVideos, setFilteredVideos] = useState([]);
  const [userDetails, setUserDetails] = useState();

  const handleUserClick = (username) => {
    navigate(`/users/${username}/channel`);
  };

  async function getVideos() {
    try {
      const searchValue = searchInputRef.current.value ? searchInputRef.current.value : "";
      const response = await axios.get(`/api/videos?name=${searchValue}`);
      setFilteredVideos(response.data);
    } catch (err) {
      console.log(err.response);
    }
  }
  async function getUserDetails() {
    if (!currentUser) {
      return;
    }
    try {
      const response = await axios.get("/api/users/" + currentUser);
      setUserDetails(response.data);
    } catch (err) {}
  }
  useEffect(() => {
    getVideos();
  }, []);
  useEffect(() => {
    getUserDetails();
  }, [currentUser]);

  function search() {
    getVideos();
    searchInputRef.current.value = "";
  }

  return (
    <div className={`main-page page ${theme}`}>
      <div className="main-page-header">
        <div className="user-details">
          {userDetails ? (
            <>
              <img
                className="profile-pic"
                src={getMediaFromServer("image", userDetails.image)}
                onClick={() => handleUserClick(userDetails.username)} // profile pic clickable
                style={{ cursor: 'pointer' }} 
              />
              <span
                className="user-name"
                onClick={() => handleUserClick(userDetails.username)} // username clickable
                style={{ cursor: 'pointer' }} 
              >
                Welcome back {userDetails.name}
              </span>
            </>
          ) : (
            <span className="user-name">
              Welcome Guest. To sign up click <Link to={"/sign-up"}>Here</Link>
            </span>
          )}
        </div>
      </div>
      <div className="search-input-div">
        <span className="change-theme-button" onClick={changeTheme}>
          {theme == "light" ? <IconSun /> : <IconMoon />}
        </span>
        <input
          className="search-input"
          ref={searchInputRef}
          onKeyDown={(e) => {
            callWithEnter(e, search);
          }}
          placeholder="Search Videos"
        />
        <button className="search-button" onClick={search}>
          search
        </button>
      </div>
      <div className="video-list">
        {filteredVideos.map((video) => (
          <VideoLink key={video._id} {...video} />
        ))}
      </div>
    </div>
  );
}

export default MainPage;