import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useTheme } from "../general_components/ThemeContext";
import "../upload_video/UploadVideoPage.css";

function VideoEdit({ videos, currentUser }) {
  const { theme } = useTheme();

  const [videoName, setVideoName] = useState("");
  const [description, setDescription] = useState("");
  const [video, setVideo] = useState(null);
  const location = useLocation();
  const navigate = useNavigate();

  const changeName = (event) => {
    setVideoName(event.target.value);
  };

  const changeDescription = (event) => {
    setDescription(event.target.value);
  };

  const submit = () => {
    if (videoName === "" || description === "") {
      return;
    }
    video.name = videoName;
    video.description = description;
    navigate("/my-videos");
  };

  useEffect(() => {
    const query = new URLSearchParams(location.search).get("v");
    if (!query) return;

    const found = videos.find((v) => v.id.toString() === query);
    if (!found || found.uploader != currentUser.username) {
      setVideo(null);
      return;
    }

    setVideo(found);
    setVideoName(found.name);
    setDescription(found.description);
  }, [location]);

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
          </div>
          <div className="input-group">
            <textarea
              className="input-field"
              placeholder="Enter a new video description"
              aria-label="description"
              value={description}
              onChange={changeDescription}
            />
          </div>
          <button className="submit-button" onClick={submit}>
            Update Video
          </button>
        </div>
      ) : (
        "Video not found"
      )}
    </div>
  );
}

export default VideoEdit;
