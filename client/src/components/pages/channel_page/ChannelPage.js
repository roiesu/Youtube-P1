import React, { useEffect, useState } from "react";
import axios from "axios";
import VideoLink from "../main_page/main_page_components/video_link/VideoLink";
import { useParams, useNavigate } from "react-router-dom";
import "./channel_page.css";
import { getMediaFromServer, simpleErrorCatcher } from "../../../utilities";
import { useTheme } from "../general_components/ThemeContext";

const ChannelPage = ({ showToast, handleExpiredToken }) => {
  const { id } = useParams();
  const { theme } = useTheme();
  const [user, setUser] = useState();
  const [videos, setVideos] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    const getUserAndVideos = async () => {
      try {
        const userResponse = await axios.get(`/api/users/${id}`);
        const videosResponse = await axios.get(`/api/users/${id}/videos`);
        setUser(userResponse.data);
        setVideos(videosResponse.data);
      } catch (err) {
        simpleErrorCatcher(err, handleExpiredToken, navigate, showToast);
      }
    };
    getUserAndVideos();
  }, [id]);

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
              <h1 className="user-name">
                {user.name}
                {"'s channel"}
              </h1>
            </div>
          </div>
          <div className="most-viewed-header">Most viewed:</div>
          {videos[0] && (
            <div className="most-viewed-video">
              <VideoLink {...videos[0]} />
            </div>
          )}
          <div className="video-list">
            <div className="user-videos-header">
              {user.name}
              {"'s videos:"}
            </div>
            {videos.length <= 1 ? <p>No videos found.</p> : renderVideosInRows()}
          </div>
        </>
      ) : (
        ""
      )}
    </div>
  );
};

export default ChannelPage;
