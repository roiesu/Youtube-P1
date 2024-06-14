import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from "react-router-dom";


function VideoEdit({ videos, currentUser }) {
    const [videoName, setVideoName] = useState('');
    const [description, setDescription] = useState('');
    const [video, setVideo] = useState(null); 
    const [isUploader, setIsUploader] = useState(false);
    const location = useLocation();
    const navigate = useNavigate()

    const changeName = (event) => {
        setVideoName(event.target.value);
    };

    const changeDescription = (event) => {
        setDescription(event.target.value);
    };

    const submit = () => {
        if(videoName!=""||description!=""){
            return;
        }
        video.name=videoName;
        video.description=description;
        navigate("/myvideos");
        console.log("Submitted Video Name: ", videoName);
        console.log("Submitted Description: ", description);
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
    }, [videos, currentUser, location.search]);

    if (!video) {
        return <div>Loading video details...</div>; 
    }

    if (!isUploader) {
        return <div>You do not have permission to edit this video.</div>;
    }

    return (
        <form className='video-details' onSubmit={submit}>
            <div className="name">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Enter a new video name"
                    aria-label="video-name"
                    value={videoName}
                    onChange={changeName} />
            </div>

            <div className="description">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Enter a new video description"
                    aria-label="description"
                    value={description}
                    onChange={changeDescription}
                />
            </div>
            <button>Update Video</button>
        </form>
    );
}

export default VideoEdit;
