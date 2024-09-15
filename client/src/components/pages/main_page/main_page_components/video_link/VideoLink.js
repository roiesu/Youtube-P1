import React, { useState } from "react";
import { Link } from "react-router-dom";
import {
  secondsToTime,
  dateDifference,
  shortFormatter,
  getMediaFromServer,
} from "../../../../../utilities";
import "./VideoLink.css";

function VideoLink({ name, uploader, date, views, _id, thumbnail, duration }) {
  return (
    <div className="video-card">
      <div className="video-container">
        <Link className="video-link" to={`/watch?channel=${uploader.username}&v=${_id}`}>
          <img src={getMediaFromServer("image", thumbnail)} />
        </Link>

        <span className="video-length">{secondsToTime(duration)}</span>
      </div>
      <div className="video-details">
        <div className="video-name">{name}</div>
        <div className="minor-details">
          <Link to={`/channel/${uploader.username}`}>
            <div className="user-details">
              <img
                className="profile-pic small"
                src={getMediaFromServer("image", uploader.image)}
              />
              <div>{uploader.name}</div>
            </div>
          </Link>
          <div>
            {dateDifference(date)} - {shortFormatter.format(views)} views
          </div>
        </div>
      </div>
    </div>
  );
}

export default VideoLink;
