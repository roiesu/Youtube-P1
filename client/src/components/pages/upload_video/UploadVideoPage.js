import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import "./UploadVideoPage.css";
import { readFileIntoState, simpleErrorCatcher } from "../../../utilities";
import { useTheme } from "../general_components/ThemeContext";
import axios from "axios";

function UploadVideo({ currentUser, showToast, handleExpiredToken }) {
  const { theme } = useTheme();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [tags, setTags] = useState("");
  const [videoFile, setVideoFile] = useState(null);
  const [videoPreview, setVideoPreview] = useState(null);
  const [thumbnail, setThumbnail] = useState(null);
  const canvasRef = useRef(null);
  const videoRef = useRef(null);
  const navigate = useNavigate();
  useEffect(() => {
    if (videoFile) {
      readFileIntoState(videoFile, setVideoPreview);
    }
  }, [videoFile]);

  function validateInput(input) {
    return input != "" && input != null;
  }
  function captureFrame() {
    let heightRatio = videoRef.current.videoHeight / 150;
    canvasRef.current.width = videoRef.current.videoWidth / heightRatio;
    canvasRef.current.height = videoRef.current.videoHeight / heightRatio;
    const context = canvasRef.current.getContext("2d");
    console.log(videoRef);
    context.drawImage(videoRef.current, 0, 0, canvasRef.current.width, canvasRef.current.height);
    // context.rect(0, 0, 200, 300);
    // context.fillStyle = "black";
    // context.fill();
    // console.log(canvasRef.current.toDataURL());
    // canvasRef.current.toBlob()=(blob)=>{
    //   setThumbnail(window.URL.createObjectUrl(blob))
    // }
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
      simpleErrorCatcher(err, handleExpiredToken, navigate, showToast);
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
          <>
            <div className="video-container">
              <video controls loop key={videoPreview} ref={videoRef}>
                <source src={videoPreview} type="video/mp4" />
              </video>
            </div>
            <button onClick={captureFrame}>capture frame for thumbnail</button>
            <canvas className="thumb-canvas" height={200} ref={canvasRef} />
          </>
        ) : (
          "no video"
        )}
      </div>
    </div>
  );
}

export default UploadVideo;
