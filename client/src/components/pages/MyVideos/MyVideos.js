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
    <div className="video-list">
              <Link to="/UploadVideosPage">
        <button className="upload-video">Upload New Video</button>
      </Link>
      {userVideos.map((video) => (
        <div key={video.id}>
          <VideoLink {...video} />
          <button
            className="delete-video"
            onClick={() => deleteVideo(video.id)}
          >
            Delete Video
          </button>
          <Link to={`/VideoEdit?v=${video.id}`}>
            <button className="edit-video">Edit Video</button>
          </Link>{" "}
        </div>
      ))}


    </div>
  );
}

export default MyVideos;
