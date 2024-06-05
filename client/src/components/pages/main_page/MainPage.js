import React from "react";
import VideoLink from "./main_page_components/VideoLink";
import "./MainPage.css";


function MainPage({ videos }) {
  return (
    <div>
      <h1>Main Page</h1>
      
      <div className="video-list">
      
        {videos.map((video) => (
          <VideoLink key={video.id} {...video} />
        ))}
      </div>
    </div>
  );
}

export default MainPage;


