import React, { useState, useEffect, useRef } from "react";
import { Link } from "react-router-dom";
import { useLocation } from "react-router-dom";
import VideoBlock from "./watch_video_page_components/video_block/VideoBlock";
import Comments from "./comments/Comments";
function WatchVideoPage({ videos, currentUser }) {
  const [video, setVideo] = useState();
  const commentInput = useRef(null);

  const location = useLocation();
  useEffect(() => {
    const query = location.search.match(/v=(.*)/);
    if (!query) return;
    const found = videos.find((video) => video.id == query[1]);
    setVideo(found);
  }, [, videos]);

  function addComment() {
    if (!currentUser || commentInput.current.value == "") return;
    const newComment = {
      user: currentUser.username,
      text: commentInput.current.value,
      date_time: new Date().toLocaleString(),
    };
    const tempVideo = { ...video };
    tempVideo.comments.push(newComment);
    setVideo(tempVideo);
    commentInput.current.value = "";
  }

  return (
    <div className="video-watching-page page">
      <h1>WatchVideoPage</h1>
      {video ? (
        <div className="video-page-main-component">
          <VideoBlock {...video} />
          <Comments comments={video.comments} addComment={addComment} commentInput={commentInput} />
        </div>
      ) : (
        "Video not found"
      )}
    </div>
  );
}

export default WatchVideoPage;
