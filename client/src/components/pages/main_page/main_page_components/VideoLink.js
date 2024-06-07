import React from "react";
import { Link } from "react-router-dom";
import { secondsToTime, dateDifference } from "../../../../utilities";

function VideoLink({ name, uploader, date_time, length, views, id, src }) {
  return (
    <Link className="video-link" to={`/watch?v=${id}`}>
      <div className="video-card">
        <div className="video-container">
          <video muted onMouseOver={(e) => e.target.play()} onMouseOut={(e) => e.target.pause()}>
            <source src={src} type="video/mp4" />
          </video>
          <span className="video-length">{secondsToTime(length)}</span>
        </div>
        <div className="video-details">
          <div className="video-name">{name}</div>
          <div className="minor-details">
            <div>Uploaded by: {uploader}</div>
            <div>Uploaded {dateDifference(date_time)}</div>
            <div>
              {new Intl.NumberFormat({ maximumSignificantDiginits: 3 }).format(views)} views
            </div>
          </div>
        </div>
      </div>
    </Link>
  );
}

export default VideoLink;
