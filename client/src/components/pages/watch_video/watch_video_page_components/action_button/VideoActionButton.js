import React from "react";
import "./VideoActionButton.css";
function VideoActionButton({ name, content, callback, active }) {
  return (
    <div
      onClick={callback}
      className={active ? "video-action-button active" : "video-action-button"}
    >
      {name} {content}
    </div>
  );
}

export default VideoActionButton;
