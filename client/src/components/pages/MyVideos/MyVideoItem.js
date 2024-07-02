import React, { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import {
  secondsToTime,
  dateDifference,
  shortFormatter,
  getMediaFromServer,
} from "../../../utilities";

function MyVideoItem({ name, uploader, date, views, _id, src }) {
  const [duration, setDuration] = useState(0);
    

  return (
    <div className="video-card">
      <div className="video-container">
        <video
          onDurationChange={(e) => {
            setDuration(Math.floor(e.target.duration));
          }}
        >
          <source src={getMediaFromServer("video", src)} type="video/mp4" />
        </video>
        <span className="video-length">{secondsToTime(duration)}</span>
      </div>
      <div className="video-details">
        <div className="video-name">{name}</div>
        <div className="minor-details">
          <div>
            {dateDifference(date)} - {shortFormatter.format(views)} views
          </div>
        </div>
      </div>
    </div>
  );
}

export default MyVideoItem;
