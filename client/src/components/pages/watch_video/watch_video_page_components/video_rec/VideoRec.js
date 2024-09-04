import React, { useState } from "react";
import { Link } from "react-router-dom";
import {
  secondsToTime,
  dateDifference,
  shortFormatter,
  getMediaFromServer,
} from "../../../../../utilities";
import "./VideoRec.css";

function VideoRec({ name, uploader, date, views, _id, thumbnail, duration, reloadVideo }) {
  return (
    <div className="video-rec">
      <div className="video-container" onClick={() => reloadVideo(uploader.username, _id)}>
        <img src={getMediaFromServer("image", thumbnail)} />
        <span className="video-length">{secondsToTime(duration)}</span>
      </div>
      <div className="video-details">
        <div className="video-name">{name}</div>
        <div className="minor-details">
          <Link to={`/channel/${uploader.username}`}>
            <div className="user-details">{uploader.name}</div>
          </Link>
          <div>
            {dateDifference(date)} - {shortFormatter.format(views)} views
          </div>
        </div>
      </div>
    </div>
  );
}

export default VideoRec;
