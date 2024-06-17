import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from "react-router-dom";
import './VideoEdit.css';  // Import the new CSS file

function VideoEdit({ videos, currentUser }) {
    const [videoName, setVideoName] = useState('');
    const [description, setDescription] = useState('');
    const [video, setVideo] = useState(null); 
    const [isUploader, setIsUploader] = useState(false);
    const location = useLocation();
    const navigate = useNavigate();

    const changeName = (event) => {
        setVideoName(event.target.value);
    };

    const changeDescription = (event) => {
        setDescription(event.target.value);
    };

    const submit = () => {
        if(videoName === "" || description === ""){
            return;
        }
        video.name = videoName;
        video.description = description;
        navigate("/my-videos");
    };

    useEffect(() => {
        const query = new URLSearchParams(location.search).get('v');
        if (!query) return;

        const found = videos.find((v) => v.id.toString() === query);
        if (!found) {
            setVideo(null); 
            setIsUploader(false); 
            return;
        }

        setVideo(found);
        setVideoName(found.name);
        setDescription(found.description);

        setIsUploader(currentUser && found.uploader === currentUser.username);
    }, [location]);

    if (!video) {
        return <div className="loading-message">Loading video details...</div>; 
    }

    if (!isUploader) {
        return <div className="error-message">You do not have permission to edit this video.</div>;
    }

    return (
        <div className='video-edit-container'>
            <h1>Edit Video</h1>
            <div className="input-group">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Enter a new video name"
                    aria-label="video-name"
                    value={videoName}
                    onChange={changeName} />
            </div>
            <div className="input-group">
                <textarea
                    className="form-control"
                    placeholder="Enter a new video description"
                    aria-label="description"
                    value={description}
                    onChange={changeDescription}
                />
            </div>
            <button className="submit-button" onClick={submit}>Update Video</button>
        </div>
    );
}

export default VideoEdit;
