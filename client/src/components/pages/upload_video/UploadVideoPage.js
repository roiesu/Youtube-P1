import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './UploadVideoPage.css';
import inputs from '../../../data/inputs.json';
import { readFileIntoState } from "../../../utilities"
import PopUpMessage from '../general_components/popup_message/PopUpMessage';

function UploadVideo({videos, setVideos,currentUser}) {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [videoFile, setVideoFile] = useState(null);
  const [videoPreview, setVideoPreview] = useState(null);
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    if (videoFile) {
      readFileIntoState(videoFile, setVideoPreview);
    }
  }, [videoFile]);

  function validateInput(input) {
    return input!="";
  }

  function handleSubmit() {
    let newErrors = {};
    if (!validateInput(title)||!validateInput(videoFile)||!validateInput(description)){
      return;
    }
    const id=videos.length==0?1:videos[videos.length-1].id+1;
    const newVideo={
      id,
      name:title,
      uploader:currentUser.username,
      displayUploader:currentUser.name,
      src:videoPreview,
      likes:[],
      views:0,
      date_time:new Date(),
      description,
      tags:[],
      comments:[]
    }
    setVideos([...videos,newVideo]);
    navigate('/my-videos');
  }

  return (
    <div className="video-upload-container">
      <h1>Upload Video</h1>
      <input
        type="text"
        className="input-field"
        placeholder="Enter video title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      {errors.title && <div className="error-message">{errors.title}</div>}

      <textarea
        className="input-field"
        placeholder="Video description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />
      {errors.description && <div className="error-message">{errors.description}</div>}
      <input
        type="file"
        className="input-field"
        accept="video/*"
        onChange={(e) => setVideoFile(e.target.files[0])}
      />
      {errors.video && <div className="error-message">{errors.video}</div>}

      <button className="submit-button" onClick={handleSubmit}>
        Upload Video
      </button>
      {videoPreview ? <video width={300}><source src={videoPreview} type="video/mp4" /></video> : "no video"}
      {errors.general && <PopUpMessage message={errors.general} />}
    </div>
  );
}

export default UploadVideo;
