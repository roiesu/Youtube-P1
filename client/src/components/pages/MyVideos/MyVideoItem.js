import React, { useState } from "react";
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
  duration,
  likesNum,
  commentsNum,
  thumbnail,
  deleteVideo,
}) {
  return (
    <tr className="my-video-item">
      <td className="video-container">
        <Link to={`/watch?v=${_id}&channel=${uploader}`}>
          <img src={getMediaFromServer("image", thumbnail)} />
        </Link>
      </td>
      <td>{name}</td>
      <td>{secondsToTime(duration ? duration : 0)}</td>
      <td>
        {new Date(date).toLocaleDateString("en", {
          year: "numeric",
          month: "short",
          day: "numeric",
        })}
      </td>
      <td>{longFormatter.format(views)}</td>
      <td>{longFormatter.format(likesNum)}</td>
      <td>{longFormatter.format(commentsNum)}</td>
      <td className="video-actions">
        <IconTrash onClick={deleteVideo} />
        <Link to={`/video/edit?v=${_id}&channel=${uploader}`}>
          <IconEdit />
        </Link>
      </td>
    </tr>
  );
}

export default MyVideoItem;
