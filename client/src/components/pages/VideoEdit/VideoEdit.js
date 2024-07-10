import React, { useState, useEffect, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useTheme } from "../general_components/ThemeContext";
import "../upload_video/UploadVideoPage.css";
import axios from "axios";
import "./VideoEdit.css";
import { getQuery, simpleErrorCatcher, getMediaFromServer } from "../../../utilities";
import ImagePicker from "../general_components/image_picker/ImagePicker";

function VideoEdit({ currentUser, showToast, handleExpiredToken }) {
  const { theme } = useTheme();

  const [videoName, setVideoName] = useState("");
  const [description, setDescription] = useState("");
  const [tags, setTags] = useState("");
  const [thumbnail, setThumbnail] = useState(null);
  const [videoPreview, setVideoPreview] = useState(null);
  const [video, setVideo] = useState(null);
  const videoRef = useRef(null);
  const location = useLocation();
  const navigate = useNavigate();
  const changeName = (event) => {
    setVideoName(event.target.value);
  };

  const changeDescription = (event) => {
    setDescription(event.target.value);
  };
  const changeTags = (event) => {
    setTags(event.target.value);
  };

  const submit = async () => {
    if (videoName === "" || description === "") {
      showToast("Both name and description are required");
      return;
    }
    try {
      const newTags = tags.split(" ");
      const data = {
        name: videoName,
        description: description,
        tags: newTags,
      };
      if (!thumbnail.startsWith("http")) {
        data.thumbnail = thumbnail;
      }
      console.log(data);
      const token = localStorage.getItem("token");
      const response = await axios.patch(`/api/users/${currentUser}/videos/${video._id}`, data, {
        headers: { Authorization: "Bearer " + token },
      });
      if (response.status === 201) {
        navigate("/my-videos");
      }
    } catch (err) {
      simpleErrorCatcher(err, handleExpiredToken, navigate, showToast);
    }
  };

  useEffect(() => {
    async function getVideo() {
      if (video) return;
      const { v, channel } = getQuery(location.search);
      if (!v || !channel) return;
      try {
        const response = await axios.get(`/api/users/${channel}/videos/${v}/details`);
        const found = response.data;
        setVideo(found);
        setVideoName(found.name);
        setDescription(found.description);
        setVideoPreview(getMediaFromServer("video", found.src));
        setThumbnail(getMediaFromServer("image", found.thumbnail));
        setTags(found.tags.join(" "));
      } catch (err) {
        console.log(err);
        simpleErrorCatcher(err, handleExpiredToken, navigate, showToast);
      }
    }
    getVideo();
  }, [currentUser]);

  if (!video) {
    return "Video not found";
  }
  return (
    <div className={`page video-upload-page ${theme}`}>
      {video ? (
        <div className="video-upload-container">
          <h1>Edit Video</h1>
          <div className="input-group">
            <input
              type="text"
              className="input-field"
              placeholder="Enter a new video name"
              aria-label="video-name"
              value={videoName}
              onChange={changeName}
            />
            <textarea
              className="input-field"
              placeholder="Enter a new video description"
              aria-label="description"
              value={description}
              onChange={changeDescription}
            />
            <input
              type="text"
              className="input-field"
              placeholder="Enter tags separated by spaces"
              aria-label="tags"
              value={tags}
              onChange={changeTags}
            />
          </div>
          <button className="submit-button" onClick={submit}>
            Update Video
          </button>
          {videoPreview ? (
            <ImagePicker
              thumbnail={thumbnail}
              videoPreview={videoPreview}
              setThumbnail={setThumbnail}
              videoRef={videoRef}
            />
          ) : (
            ""
          )}
        </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
}

export default VideoEdit;
