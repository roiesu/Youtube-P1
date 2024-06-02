import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { findVideoById } from "../../../utilities";
function WatchVideoPage() {
  const [video, setVideo] = useState();
  const { id } = useParams();
  useEffect(() => {
    if (!id) return;
    const found = findVideoById(id);
    console.log(found);
    setVideo(found);
  }, [id]);

  return (
    <div>
      <h1>WatchVideoPage</h1>
      {video ? (
        <video controls>
          <source src={video.src} type="video/mp4" />
        </video>
      ) : (
        "Video not found"
      )}
    </div>
  );
}

export default WatchVideoPage;
