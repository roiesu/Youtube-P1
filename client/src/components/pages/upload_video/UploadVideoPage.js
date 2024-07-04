import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./UploadVideoPage.css";
import { readFileIntoState } from "../../../utilities";
import { useTheme } from "../general_components/ThemeContext";
import axios from "axios";

function UploadVideo({ currentUser, showToast }) {
  const { theme } = useTheme();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [tags, setTags] = useState("");
  const [videoFile, setVideoFile] = useState(null);
  const [videoPreview, setVideoPreview] = useState(null);
  const navigate = useNavigate();
  useEffect(() => {
    if (videoFile) {
      readFileIntoState(videoFile, setVideoPreview);
    }
  }, [videoFile]);

  function validateInput(input) {
    return input != "" && input != null;
  }

  async function handleSubmit() {
    if (!validateInput(title) || !validateInput(videoFile) || !validateInput(description)) {
      showToast("Name, Description and a video file are required");
      return;
    }
    let tagsToSend = [];
    if (tags != "") {
      tagsToSend = tags.split(" ");
    }
    const newVideo = {
      name: title,
      src: videoPreview,
      description,
      tags: tagsToSend,
    };
    try {
      const response = await axios.post(`/api/users/${currentUser}/videos`, newVideo, {
        headers: { authorization: localStorage.getItem("token") },
      });
      if (response.status == 201) {
        navigate("/my-videos");
      }
    } catch (err) {
      if (err.status === 404) {
        showToast("User not found");
      } else if (err.status === 400) {
        showToast(err.message);
      } else if (err.status === 401) {
        showToast("Token needed");
      } else if (err.status === 403) {
        showToast("Invalid token");
      }
    }
  }

  return (
    <div className={`page upload-video-page ${theme}`}>
      <div className="video-upload-container">
        <h1>Upload Video</h1>
        <input
          type="text"
          className="input-field"
          placeholder="Enter video title"
          onChange={(e) => setTitle(e.target.value)}
        />

        <textarea
          className="input-field"
          placeholder="Video description"
          onChange={(e) => setDescription(e.target.value)}
        />
        <input
          type="tags"
          className="input-field"
          placeholder="Video tags separated by spaces (Optional)"
          onChange={(e) => setTags(e.target.value)}
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
