import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import VideoBlock from "./watch_video_page_components/video_block/VideoBlock";
import Comments from "./comments/Comments";
function WatchVideoPage({ videos }) {
  const [video, setVideo] = useState();
  const location = useLocation();
  useEffect(() => {
    const query = location.search.match(/v=(.*)/);
    if (!query) return;
    const found = videos.find((video) => video.id == query[1]);
    setVideo(found);
  }, []);

  return (
    <div className="video-watching-page page">
      <h1>WatchVideoPage</h1>
      {video ? (
        <div className="video-page-main-component">
          <VideoBlock {...video} />
          <Comments comments={video.comments} />
        </div>
      ) : (
        "Video not found"
      )}
    </div>
  );
}

export default WatchVideoPage;
