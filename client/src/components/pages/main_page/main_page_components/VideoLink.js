import React from "react";
import { Link } from "react-router-dom";

function VideoLink({ name, uploader, date_time, length, views, id, src }) {
  return (
    <div className="video-card">
      <Link to="/">
        <video muted onMouseOver={(e) => e.target.play()} onMouseOut={(e) => e.target.pause()}>
          <source src={src} type="video/mp4" />
        </video>
      </Link>
      <div className="video-details">
        <h1>{name}</h1>
        <div>Uploaded by: {uploader}</div>
        <div>Upload Date: {date_time}</div>
        <div>Length: {length}</div>
        <div>Views: {views}</div>
      </div>
    </div>
  );
}

export default VideoLink;
