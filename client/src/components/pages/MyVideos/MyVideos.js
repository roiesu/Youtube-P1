import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import "./MyVideos.css";
import { useTheme } from "../general_components/ThemeContext";
import axios from "axios";
import MyVideoItem from "./MyVideoItem";

function MyVideos({ currentUser }) {
  const { theme } = useTheme();
  const [userVideos, setUserVideos] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

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
      const token = localStorage.getItem("token");
      const response = await axios.delete(`/api/users/${currentUser}/videos/${videoId}`, {
        headers: { Authorization: "Bearer " + token },
      });
      if (response.status === 200) {
        const tempVideos = userVideos.filter((video) => video._id !== videoId);
        setUserVideos(tempVideos);
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
        <Link to="/video/upload">
          <button className="upload-button">Upload New Video</button>
        </Link>
        <Link to="/user/edit">
          <button className="edit-user-button">Edit User Details</button>
        </Link>
        <table>
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
          {userVideos.map((video) => (
            <MyVideoItem key={video._id} {...video} deleteVideo={() => deleteVideo(video._id)} />
          ))}
        </table>
      </div>
    </div>
  );
}

export default MyVideos;
