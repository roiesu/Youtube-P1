import React, { useEffect, useRef, useState } from "react";
import axios from "axios";
import VideoLink from "./main_page_components/VideoLink";
import { callWithEnter } from "../../../utilities";
import "./MainPage.css";
import { Link } from "react-router-dom";
import { useTheme } from "../general_components/ThemeContext";
import IconSun from "../../icons/IconSun";
import IconMoon from "../../icons/IconMoon";

function MainPage({ videos, currentUser }) {
  const { theme, changeTheme } = useTheme();
  const searchInputRef = useRef(null);
  const [filteredVideos, setFilteredVideos] = useState([]);
  async function getVideos() {
    try {
      const searchValue = searchInputRef.current.value ? searchInputRef.current.value : "";

      const response = await axios.get(`/api/videos?name=${searchValue}`);
      console.log(response.data);
      setFilteredVideos(response.data);
    } catch (err) {
      console.log(err.response);
    }
  }
  useEffect(getVideos, []);

  function search() {
    getVideos();
    searchInputRef.current.value = "";
  }

  return (
    <div className={`main-page page ${theme}`}>
      <div className="main-page-header">
        <div className="user-details">
          {currentUser ? (
            <>
              <img className="profile-pic-small" src={currentUser.image} />
              <span className="user-name">Welcome back {currentUser.name}</span>
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
          <VideoLink key={video.id} {...video} />
        ))}
      </div>
    </div>
  );
}

export default MainPage;
