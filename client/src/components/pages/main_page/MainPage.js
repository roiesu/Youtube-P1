import React, { useEffect, useRef, useState } from "react";
import VideoLink from "./main_page_components/VideoLink";
import { callWithEnter } from "../../../utilities";
import "./MainPage.css";

function MainPage({ videos }) {
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
    <div>
      <h1>Main Page</h1>
      <input
        ref={searchInputRef}
        onKeyDown={(e) => {
          callWithEnter(e, search);
        }}
        placeholder="Search Videos"
      />
      <button onClick={search}>search</button>
      <div className="video-list">
        {filteredVideos.map((video) => (
          <VideoLink key={video.id} {...video} />
        ))}
      </div>
    </div>
  );
}

export default MainPage;
