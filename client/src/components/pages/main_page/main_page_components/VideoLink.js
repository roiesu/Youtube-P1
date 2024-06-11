import React, { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import { secondsToTime, dateDifference, shortFormatter } from "../../../../utilities";

function VideoLink({ name, uploader, displayUploader, date_time, views, id, src }) {
  const [playing, setPlaying] = useState(false);
  const [duration, setDuration] = useState(0);

  return (
    <Link className="video-link" to={`/watch?v=${id}`}>
      <div className="video-card">
        <div className="video-container">
          <video
            loop
            muted
            onDurationChange={(e) => {
              setDuration(Math.floor(e.target.duration));
            }}
            onMouseOver={(e) => {
              if (playing) return;
              e.target.play().then(() => setPlaying(true));
            }}
            onMouseOut={(e) => {
              if (!playing) return;
              e.target.pause();
              setPlaying(false);
            }}
          >
            <source src={src} type="video/mp4" />
          </video>
          <span className="video-length">{secondsToTime(duration)}</span>
        </div>
        <div className="video-details">
          <div className="video-name">{name}</div>
          <div className="minor-details">
            <div>Uploaded by {displayUploader}</div>
            <div>
              {dateDifference(date_time)} - {shortFormatter.format(views)} views
            </div>
          </div>
        </div>
      </div>
    </Link>
  );
}

export default VideoLink;
