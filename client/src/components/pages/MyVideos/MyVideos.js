import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import "./MyVideos.css";
import { useTheme } from "../general_components/ThemeContext";
import axios from "axios";
import MyVideoItem from "./MyVideoItem";
import { simpleErrorCatcher } from "../../../utilities";

function MyVideos({ currentUser, showToast, handleExpiredToken }) {
  const { theme } = useTheme();
  const [userVideos, setUserVideos] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    async function displayUserVideos() {
      try {
        const response = await axios.get(`/api/users/${currentUser}/videos/details`, {
          headers: { Authorization: localStorage.getItem("token") },
        });
        if (response.status === 200) {
          setUserVideos(response.data);
        }
      } catch (err) {
        simpleErrorCatcher(err, handleExpiredToken, navigate, showToast);
      }
    }
    displayUserVideos();
  }, [currentUser]);

  async function deleteVideo(videoId, videoName) {
    const answer = prompt(`Type '${videoName}' to confirm deletion`);
    if (answer != videoName) {
      return;
    }
    try {
      const token = localStorage.getItem("token");
      const response = await axios.delete(`/api/users/${currentUser}/videos/${videoId}`, {
        headers: { Authorization: "Bearer " + token },
      });
      if (response.status === 200) {
        const tempVideos = userVideos.filter((video) => video._id !== videoId);
        setUserVideos(tempVideos);
      }
    } catch (err) {
      simpleErrorCatcher(err, handleExpiredToken, navigate, showToast);
    }
  }

  return (
    <div className={`page my-videos-page ${theme}`}>
      <div className="container">
        <div className="buttons">
          <Link to="/video/upload">
            <span className="upload-button">Upload New Video</span>
          </Link>
          <Link to="/user/edit">
            <span className="edit-user-button">Edit User Details</span>
          </Link>
        </div>
        <table>
          <thead>
            <tr>
              <th>Video thumbnail</th>
              <th>Name</th>
              <th>Duration</th>
              <th>Uploading date</th>
              <th>Views</th>
              <th>Likes</th>
              <th>Comments</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {userVideos.map((video) => (
              <MyVideoItem
                key={video._id}
                {...video}
                deleteVideo={() => deleteVideo(video._id, video.name)}
              />
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default MyVideos;
