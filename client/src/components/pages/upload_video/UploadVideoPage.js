import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./UploadVideoPage.css";
import { readFileIntoState } from "../../../utilities";
import PopUpMessage from "../general_components/popup_message/PopUpMessage";
import { useTheme } from "../general_components/ThemeContext";

function UploadVideo({ videos, setVideos, currentUser }) {
  const { theme } = useTheme();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [videoFile, setVideoFile] = useState(null);
  const [videoPreview, setVideoPreview] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    if (videoFile) {
      readFileIntoState(videoFile, setVideoPreview);
    }
  }, [videoFile]);

  function validateInput(input) {
    return input != "";
  }

  function handleSubmit() {
    if (!validateInput(title) || !validateInput(videoFile) || !validateInput(description)) {
      return;
    }
    const id = videos.length == 0 ? 1 : videos[videos.length - 1].id + 1;
    const newVideo = {
      id,
      name: title,
      uploader: currentUser.username,
      displayUploader: currentUser.name,
      src: videoPreview,
      likes: [],
      views: 0,
      date_time: new Date(),
      description,
      tags: [],
      comments: [],
    };
    setVideos([...videos, newVideo]);
    navigate("/my-videos");
  }

  return (
    <div className={`page upload-video-page ${theme}`}>
      <div className="video-upload-container">
        <h1>Upload Video</h1>
        <input
          type="text"
          className="input-field"
          placeholder="Enter video title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />

        <textarea
          className="input-field"
          placeholder="Video description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
        <input
          type="file"
          className="input-field"
          accept="video/*"
          onChange={(e) => setVideoFile(e.target.files[0])}
        />

        <button className="submit-button" onClick={handleSubmit}>
          Upload Video
        </button>
        {videoPreview ? (
          <video width={300}>
            <source src={videoPreview} type="video/mp4" />
          </video>
        ) : (
          "no video"
        )}
      </div>
    </div>
  );
}

export default UploadVideo;
