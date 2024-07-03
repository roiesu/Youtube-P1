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

  const renderVideosInRows = () => {
    const rows = [];
    for (let i = 1; i < videos.length; i += 3) {
      rows.push(
        <div className="video-row" key={`row-${i}`}>
          {videos.slice(i, i + 3).map((video) => (
            <VideoLink key={video._id} {...video} />
          ))}
        </div>
      );
    }
    return rows;
  };

  const mostViewedVideo = videos.reduce((max, video) => video.views > max.views ? video : max, videos[0] || {});

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
          <div className="most-viewed-header">Most viewed:</div>
          {mostViewedVideo._id && (
            <div className="most-viewed-video">
              <VideoLink {...mostViewedVideo} />
            </div>
          )}
          <div className="video-list">
          <div className="user-videos-header">{user.name}{"'s videos:"}</div>
            {videos.length === 0 ? (
              <p>No videos found.</p>
            ) : (
              renderVideosInRows()
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
