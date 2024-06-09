import React, { useState, useEffect, useRef } from "react";
import { useLocation } from "react-router-dom";
import VideoBlock from "./watch_video_page_components/video_block/VideoBlock";
import Comments from "./watch_video_page_components/comments/Comments";

function WatchVideoPage({ videos, currentUser }) {
  const [video, setVideo] = useState();
  const [likedVideo, setLikedVideo] = useState(false);
  const commentInput = useRef(null);
  const location = useLocation();

  function addComment() {
    if (!currentUser || commentInput.current.value == "") return;
    const newComment = {
      user: currentUser.username,
      text: commentInput.current.value,
      date_time: new Date(),
    };
    const tempVideo = { ...video };
    tempVideo.comments.push(newComment);
    setVideo(tempVideo);
    commentInput.current.value = "";
  }

  function like() {
    if (!currentUser) return;
    const tempVideo = { ...video };
    if (likedVideo) {
      tempVideo.likes = video.likes.filter((user) => user != currentUser.username);
    } else {
      tempVideo.likes.push(currentUser.username);
    }
    video.likes = [...tempVideo.likes];
    setVideo(tempVideo);
    setLikedVideo(!likedVideo);
  }

  useEffect(() => {
    // Finds the video by query params
    if (video) return;
    const query = location.search.match(/v=(.*)/);
    if (!query) return;
    const found = videos.find((video) => video.id == query[1]);
    found.views++;
    setVideo(found);
    if (!currentUser) return;

    // Set if liked the video
    const user = found.likes.find((user) => user === currentUser.username);
    if (user) setLikedVideo(true);
  }, [video]);

  return (
    <div className="video-watching-page page">
      <h1>WatchVideoPage</h1>
      {video ? (
        <div className="video-page-main-component">
          <VideoBlock
            {...video}
            likes={video.likes.length}
            commentInput={commentInput}
            like={like}
            likedVideo={likedVideo}
          />
          <Comments comments={video.comments} addComment={addComment} commentInput={commentInput} />
        </div>
      ) : (
        "Video not found"
      )}
    </div>
  );
}

export default WatchVideoPage;
