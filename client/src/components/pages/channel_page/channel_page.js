import React, { useEffect, useState } from "react";
import axios from "axios";
import VideoLink from "../main_page/main_page_components/VideoLink";
import { useParams, useNavigate } from 'react-router-dom';
import "./channel_page.css"; 

// import IconSun from "../../icons/IconSun";
// import IconMoon from "../../icons/IconMoon";

const ChannelPage = () => {
  const { id } = useParams(); 
  const [user, setUser] = useState(null); 
  const [videos, setVideos] = useState([]);
  const [error, setError] = useState(null); 
  const navigate = useNavigate(); 

    //user and video data from the API
    useEffect(() => {
    const getUserAndVideos = async () => {
      try {
        const userResponse = await axios.get(`/api/users/${id}`);
        const videosResponse = await axios.get(`/api/users/${id}/videos`); 
        setUser(userResponse.data); 
        setVideos(videosResponse.data); 
      } catch (err) {
        setError(err.message); 
      }
    };
    getUserAndVideos(); 
  }, [id]); 

  const handleUserClick = (username) => {
    navigate(`/users/${username}/channel`);
  };

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="channle-page page">
      <div className="channle-page-header">
        <div className="user-details">
          <img className="profile-pic" src={`/media/${user.image}`} alt={user.name} /> {/* profile picture */}
          <h1 className="user-name">{user.name}</h1> {/*user's name */}
        </div>
      </div>
      <div className="video-list">
        {videos.length === 0 ? ( 
          <p>No videos found.</p> 
        ) : (
          videos.map((video) => ( 
            <VideoLink key={video._id} {...video} /> 
          ))
        )}
      </div>
    </div>
  );
};

export default ChannelPage; 







  



