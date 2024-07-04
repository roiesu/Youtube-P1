import React, { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import { secondsToTime, longFormatter, getMediaFromServer } from "../../../utilities";
import IconTrash from "../../icons/IconTrash";
import IconEdit from "../../icons/IconEdit";

function MyVideoItem({
  _id,
  name,
  uploader,
  date,
  views,
  likesCount,
  commentsCount,
  src,
  deleteVideo,
}) {
  const [duration, setDuration] = useState(0);

  return (
    <tr className="my-video-item">
      <td className="video-container">
        <Link to={`/watch?v=${_id}&channel=${uploader.username}`}>
          <video
            onDurationChange={(e) => {
              setDuration(Math.floor(e.target.duration));
            }}
          >
            <source src={getMediaFromServer("video", src)} type="video/mp4" />
          </video>
        </Link>
      </td>
      <td>{name}</td>
      <td>{secondsToTime(duration)}</td>
      <td>
        {new Date(date).toLocaleDateString("en", {
          year: "numeric",
          month: "short",
          day: "numeric",
        })}
      </td>
      <td>{longFormatter.format(views)}</td>
      <td>{longFormatter.format(likesCount)}</td>
      <td>{longFormatter.format(commentsCount)}</td>
      <td className="video-actions">
        <IconTrash onClick={deleteVideo} />
        <Link to={`/video/edit?v=${_id}&chanel=${uploader.username}`}>
          <IconEdit />
        </Link>
      </td>
    </tr>
  );
}

export default MyVideoItem;
