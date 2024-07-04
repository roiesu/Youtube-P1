import React, { useState, useEffect, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import VideoBlock from "./watch_video_page_components/video_block/VideoBlock";
import Comments from "./watch_video_page_components/comments/Comments";
import "./WatchVideoPage.css";
import { useTheme } from "../general_components/ThemeContext";
import axios from "axios";
import { getQuery, simpleErrorCatcher } from "../../../utilities";

function WatchVideoPage({ currentUser, showToast, handleExpiredToken }) {
  const { theme } = useTheme();
  const [video, setVideo] = useState();
  const [likedVideo, setLikedVideo] = useState(false);
  const commentInput = useRef(null);
  const location = useLocation();
  const AuthHeader = { Authorization: "Bearer " + localStorage.getItem("token") };

  async function addComment() {
    if (!currentUser) {
      showToast("Can't comment if not singed in");
      return;
    } else if (commentInput == "") {
      showToast("Can't comment an empty text");
      return;
    }

    try {
      const response = await axios.post(
        `/api/users/${video.uploader.username}/videos/${video._id}/comments`,
        { text: commentInput.current.value },
        { headers: AuthHeader }
      );
      const tempVideo = { ...video };
      tempVideo.comments.push(response.data);
      setVideo(tempVideo);
      commentInput.current.value = "";
    } catch (err) {
      simpleErrorCatcher(err, handleExpiredToken, navigate, showToast);
    }
  }

  async function deleteComment(commentId) {
    try {
      const response = await axios.delete(
        `/api/users/${video.uploader.username}/videos/${video._id}/comments/${commentId}`,
        { headers: AuthHeader }
      );
      if (response.status === 201) {
        const tempVideo = { ...video };
        tempVideo.comments = tempVideo.comments.filter((comment) => comment._id != commentId);
        setVideo(tempVideo);
      }
    } catch (err) {
      simpleErrorCatcher(err, handleExpiredToken, navigate, showToast);
    }
  }

  async function editComment(commentId, newContent) {
    try {
      const response = await axios.patch(
        `/api/users/${video.uploader.username}/videos/${video._id}/comments/${commentId}`,
        { text: newContent },
        { headers: AuthHeader }
      );
      if (response.status === 201) {
        const tempVideo = { ...video };
        const found = tempVideo.comments.find((comment) => comment._id == commentId);
        found.text = newContent;
        found.edited = true;
        setVideo(tempVideo);
      }
    } catch (err) {
      simpleErrorCatcher(err, handleExpiredToken, navigate, showToast);
    }
  }

  async function like() {
    if (!currentUser) return;
    const url = `/api/users/${video.uploader.username}/videos/${video._id}/like`;
    let addition = 1;
    try {
      if (likedVideo) {
        await axios.delete(url, { headers: AuthHeader });
        addition = -1;
      } else {
        await axios.put(url, { data: "" }, { headers: AuthHeader });
      }
      setLikedVideo(!likedVideo);
      video.likes += addition;
    } catch (err) {
      simpleErrorCatcher(err, handleExpiredToken, navigate, showToast);
    }
  }

  useEffect(() => {
    async function getVideo() {
      // Finds the video by query params
      if (video) return;
      const { v, channel } = getQuery(location.search);
      if (!v || !channel) return;
      try {
        const headers = {};
        const token = localStorage.getItem("token");
        if (token) headers.Authorization = "Bearer " + token;
        const found = await axios.get(`/api/users/${channel}/videos/${v}`, {
          headers,
        });
        if (!found) {
          return;
        }
        setVideo(found.data);
        setLikedVideo(found.data.likedVideo);
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
            likes={video.likes}
            commentInput={commentInput}
            like={like}
            likedVideo={likedVideo}
            loggedIn={currentUser != null}
            showToast={showToast}
          />
          <Comments
            currentUser={currentUser}
            comments={video.comments}
            addComment={addComment}
            deleteComment={deleteComment}
            commentInput={commentInput}
            editComment={editComment}
            showToast={showToast}
          />
        </div>
      ) : (
        "Video not found"
      )}
    </div>
  );
}

export default WatchVideoPage;
