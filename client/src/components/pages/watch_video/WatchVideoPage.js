import React, { useState, useEffect, useRef } from "react";
import { useLocation } from "react-router-dom";
import VideoBlock from "./watch_video_page_components/video_block/VideoBlock";
import Comments from "./watch_video_page_components/comments/Comments";
import "./WatchVideoPage.css";
import { useTheme } from "../general_components/ThemeContext";
import axios from "axios";
import { getQuery } from "../../../utilities";
function WatchVideoPage({ videos, currentUser }) {
  const { theme } = useTheme();
  const [video, setVideo] = useState();
  const [likedVideo, setLikedVideo] = useState(false);
  const commentInput = useRef(null);
  const location = useLocation();

  function addComment() {
    if (!currentUser || commentInput.current.value == "") return;
    const newComment = {
      user: currentUser.username,
      displayName: currentUser.name,
      text: commentInput.current.value,
      date_time: new Date().toISOString(),
      edited: false,
    };
    const tempVideo = { ...video };
    tempVideo.comments.push(newComment);
    setVideo(tempVideo);
    commentInput.current.value = "";
  }

  function deleteComment(commentDate) {
    const tempVideo = { ...video };
    tempVideo.comments = video.comments.filter(
      (comment) => comment.date_time != commentDate || comment.user != currentUser.username
    );
    setVideo(tempVideo);
    const found = videos.find((item) => item.id == video.id);
    found.comments = tempVideo.comments;
  }

  function editComment(commentDate, newContent) {
    const tempVideo = { ...video };
    const found = tempVideo.comments.find(
      (comment) => comment.user == currentUser.username && comment.date_time == commentDate
    );
    found.text = newContent;
    found.edited = true;
    setVideo(tempVideo);
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
    async function getVideo() {
      // Finds the video by query params
      if (video) return;
      const { v, chanel } = getQuery(location.search);
      if (!v || !chanel) return;
      try {
        const found = await axios.get(`/api/users/${chanel}/videos/${v}`);
        if (!found) {
          return;
        }
        setVideo(found.data);
        if (!currentUser) return;
        // Set if liked the video
        const user = found.likes.find((user) => user === currentUser.username);
        if (user) setLikedVideo(true);
      } catch (err) {}
    }
    getVideo();
  }, [video]);

  return (
    <div className={`video-watching-page page ${theme}`}>
      {video ? (
        <div className="video-page-main-component">
          <VideoBlock
            {...video}
            likes={video.likes.length}
            commentInput={commentInput}
            like={like}
            likedVideo={likedVideo}
            loggedIn={currentUser != null}
          />
          <Comments
            currentUser={currentUser ? currentUser.username : null}
            comments={video.comments}
            addComment={addComment}
            deleteComment={deleteComment}
            commentInput={commentInput}
            editComment={editComment}
          />
        </div>
      ) : (
        "Video not found"
      )}
    </div>
  );
}

export default WatchVideoPage;
