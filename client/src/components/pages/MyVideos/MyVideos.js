import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import VideoLink from "../main_page/main_page_components/VideoLink";
import "./MyVideos.css";
import { useTheme } from "../general_components/ThemeContext";

function MyVideos({ videos, currentUser, setVideos }) {
  const { theme } = useTheme();

  const [userVideos, setUserVideos] = useState([]);

  useEffect(() => {
    const filteredVideos = videos.filter((video) => video.uploader == currentUser.username);
    setUserVideos(filteredVideos);
  }, [videos]);

  function deleteVideo(videoId) {
    const tempVideos = videos.filter((video) => video.id != videoId);
    setVideos(tempVideos);
  }

  return (
    <div className={`page my-videos-page ${theme}`}>
      <div className="container">
        <Link to="/upload">
          <button className="upload-button">Upload New Video</button>
        </Link>
        {userVideos.map((video) => (
          <div className="video-item" key={video.id}>
            <VideoLink {...video} />
            <button className="delete-button" onClick={() => deleteVideo(video.id)}>
              Delete Video
            </button>
            <Link to={`/edit?v=${video.id}`}>
              <button className="edit-button">Edit Video</button>
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MyVideos;
