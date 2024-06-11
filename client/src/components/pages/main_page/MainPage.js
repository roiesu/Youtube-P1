import React, { useRef, useState } from "react";
import VideoLink from "./main_page_components/VideoLink";
import { callWithEnter } from "../../../utilities";
import "./MainPage.css";
import { Link } from "react-router-dom";
import { useTheme } from "../general_components/ThemeContext";

function MainPage({ videos, currentUser }) {
  const { theme, changeTheme } = useTheme();

  const searchInputRef = useRef(null);
  const [filteredVideos, setFilteredVideos] = useState(videos);

  function search() {
    if (searchInputRef.current.value === "") {
      setFilteredVideos(videos);
      return;
    }
    const reg = new RegExp(searchInputRef.current.value, "i");
    const tempFiltered = videos.filter((video) => video.name.match(reg));
    setFilteredVideos(tempFiltered);
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
        <button onClick={changeTheme}>change theme</button>
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
