import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import VideoLink from "../main_page/main_page_components/VideoLink";
import "./MyVideos.css";

function MyVideos({ videos, currentUser, setVideos }) {
  const [userVideos, setUserVideos] = useState([]);

  useEffect(() => {
    const filteredVideos = videos.filter(
      (video) => video.uploader == currentUser.username
    );
    setUserVideos(filteredVideos);
  }, [videos]);

  function deleteVideo(videoId) {
    const tempVidoes = videos.filter((video) => video.id != videoId);
    setVideos(tempVidoes);
  }

  return (
    <div className="container">
      <Link to="/UploadVideosPage">
        <button className="upload-button">Upload New Video</button>
      </Link>
      {userVideos.map((video) => (
        <div className="video-item" key={video.id}>
          <VideoLink {...video} />
          <button
            className="delete-button"
            onClick={() => deleteVideo(video.id)}
          >
            Delete Video
          </button>
          <Link to={`/VideoEdit?v=${video.id}`}>
            <button className="edit-button">Edit Video</button>
          </Link>
        </div>
      ))}
    </div>
  );
}

export default MyVideos;
