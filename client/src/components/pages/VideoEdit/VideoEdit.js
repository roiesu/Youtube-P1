import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useTheme } from "../general_components/ThemeContext";
import "../upload_video/UploadVideoPage.css";
import axios from "axios";
import { getQuery } from "../../../utilities";

function VideoEdit({ currentUser}) {
  const { theme } = useTheme();

  const [videoName, setVideoName] = useState("");
  const [description, setDescription] = useState("");
  const [video, setVideo] = useState(null);
  const location = useLocation();
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState("");

  const changeName = (event) => {
    setVideoName(event.target.value);
  };

  const changeDescription = (event) => {
    setDescription(event.target.value);
  };

  const submit = async () => {
    if (videoName === "" || description === "") {
      setErrorMessage("Both name and description are required");
      return;
    }

    try {
      const token = localStorage.getItem("token");
      const response = await axios.patch(
        `/api/users/${currentUser}/videos/${video._id}?restrict=1`,
        {
          name: videoName,
          description: description,
        },
        { headers: { Authorization: "Bearer " + token } }
      );
      if (response.status === 201) {
        navigate("/my-videos");
      } 
    } catch (error) {
      if (error.response) {
        if (error.response.status === 404) {
          setErrorMessage("Video not found");
        } else if (error.response.status === 500) {
          setErrorMessage("Internal server error");
        } else {
          setErrorMessage("An unexpected error occurred");
        }
      } else {
        setErrorMessage("An unexpected error occurred");
      }
    }
  };

  useEffect(() => {
    async function getVideo() {
      if (video) return;
      const { v, chanel } = getQuery(location.search);
      if (!v || !chanel) return;
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(`/api/users/${chanel}/videos/${v}`, {
          headers: { Authorization: "Bearer " + token },
        });
        const found = response.data;
        setVideo(found);
        setVideoName(found.name);
        setDescription(found.description);
      } catch (err) {
        if (err.response) {
          if (err.response.status === 404) {
            setErrorMessage("Video not found");
          } else if (err.response.status === 500) {
            setErrorMessage("Internal server error");
          } else {
            setErrorMessage("An unexpected error occurred");
          }
        } else {
          setErrorMessage("An unexpected error occurred");
        }
      }
    }
    getVideo();
  }, [currentUser]);

  return (
    <div className={`page video-upload-page ${theme}`}>
      {errorMessage && <p className="error-message">{errorMessage}</p>}
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
        !errorMessage && <p>Loading...</p>
      )}
    </div>
  );
}

export default VideoEdit;
