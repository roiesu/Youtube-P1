import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useTheme } from "../general_components/ThemeContext";
import "../upload_video/UploadVideoPage.css";
import axios from "axios";
import { getQuery } from "../../../utilities";
import PopUpMessage from "../general_components/popup_message/PopUpMessage";

function VideoEdit({ currentUser, showToast }) {
  const { theme } = useTheme();

  const [videoName, setVideoName] = useState("");
  const [description, setDescription] = useState("");
  const [tags, setTags] = useState("");
  const [video, setVideo] = useState(null);
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
      const token = localStorage.getItem("token");
      const newTags = tags.split(" ");
      const response = await axios.patch(
        `/api/users/${currentUser}/videos/${video._id}`,
        {
          name: videoName,
          description: description,
          tags: newTags,
        },
        { headers: { Authorization: "Bearer " + token } }
      );
      if (response.status === 201) {
        navigate("/my-videos");
      }
    } catch (error) {
      if (error.response) {
        if (error.response.status === 404) {
          showToast("Video not found");
        } else if (error.response.status === 500) {
          showToast("Internal server error");
        } else {
          showToast("An unexpected error occurred");
        }
      } else {
        showToast("An unexpected error occurred");
      }
    }
  };

  useEffect(() => {
    async function getVideo() {
      if (video) return;
      const { v, chanel } = getQuery(location.search);
      if (!v || !chanel) return;
      try {
        const response = await axios.get(`/api/users/${chanel}/videos/${v}/details`);
        const found = response.data;
        setVideo(found);
        setVideoName(found.name);
        setDescription(found.description);
        setTags(found.tags.join(" "));
      } catch (err) {
        if (err.response) {
          if (err.response.status === 404) {
            showToast("Video not found");
          } else if (err.response.status === 500) {
            showToast("Internal server error");
          } else {
            showToast("An unexpected error occurred");
          }
        } else {
          showToast("An unexpected error occurred");
        }
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
        </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
}

export default VideoEdit;
