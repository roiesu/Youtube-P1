import React, { useEffect, useState } from "react";
import axios from "axios";
import VideoLink from "../main_page/main_page_components/VideoLink";
import { useParams, useNavigate } from "react-router-dom";
import "./channel_page.css";
import { getMediaFromServer } from "../../../utilities";
import { useTheme } from "../general_components/ThemeContext";

const ChannelPage = () => {
  const { id } = useParams();
  const { theme } = useTheme();
  const [user, setUser] = useState();
  const [videos, setVideos] = useState([]);
  const [error, setError] = useState();

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

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className={`channel-page page ${theme}`}>
      {user ? (
        <>
          <div className="channel-page-header">
            <div className="user-details">
              <img
                className="profile-pic"
                src={getMediaFromServer("image", user.image)}
                alt={user.name}
              />
              <h1 className="user-name">{user.name}{"'s chnnel"}</h1>
            </div>
          </div>
          <div className="video-list">
            {videos.length === 0 ? (
              <p>No videos found.</p>
            ) : (
              videos.map((video) => <VideoLink key={video._id} {...video} />)
            )}
          </div>
        </>
      ) : (
        ""
      )}
    </div>
  );
};

export default ChannelPage;
