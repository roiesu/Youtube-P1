import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import "./MyVideos.css";
import { useTheme } from "../general_components/ThemeContext";
import axios from "axios";
import MyVideoItem from "./MyVideoItem";

function MyVideos({ videos, currentUser, setVideos }) {
  const { theme } = useTheme();
  const [userVideos, setUserVideos] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  console.log(userVideos);

  useEffect(() => {
    async function displayUserVideos() {
      try {
        const response = await axios.get(`/api/users/${currentUser}/videos`);
        if (response.status === 200) {
          setUserVideos(response.data);
        }
      } catch (error) {
        if (error.response) {
          if (error.response.status === 404) {
            setErrorMessage("User not found");
          } else if (error.response.status === 500) {
            setErrorMessage("Internal server error");
          } else {
            setErrorMessage("An unexpected error occurred");
          }
        } else {
          setErrorMessage("An unexpected error occurred");
        }
      }
    }

    displayUserVideos();
  }, [currentUser]);

  async function deleteVideo(videoId) {
    try {
      console.log(videoId);
      const token = localStorage.getItem("token");
      const response = await axios.delete(`/api/users/${currentUser}/videos/${videoId}`,
        {headers: {Authorization: "Bearer" + token }}
      );
      if (response.status === 200) {
        const tempVideos = userVideos.filter((video) => video.id !== videoId);
        setUserVideos(tempVideos);
        setVideos(tempVideos);
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
  }


  return (
    <div className={`page my-videos-page ${theme}`}>
      <div className="container">
        <Link to="/upload">
          <button className="upload-button">Upload New Video</button>
        </Link>
        <Link to="/edit-user">
            <button className="edit-user-button">Edit User Details</button>
          </Link>
        {userVideos.map((video) => (
          <div className="video-item" key={video._id}>
            <MyVideoItem {...video} />
            <button className="delete-button" onClick={() => deleteVideo(video._id)}>
              Delete Video
            </button>
            <Link to={`/edit?v=${video._id}&chanel=${currentUser}`}>
              <button className="edit-button">Edit Video</button>
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MyVideos;
