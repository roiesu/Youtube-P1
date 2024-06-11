import React, { useEffect, useState } from "react";
import "./VideoActionButton.css";
import PopUpMessage from "../../../general_components/popup_message/PopUpMessage";
function VideoActionButton({
  name,
  content,
  callback,
  active,
  canActivate,
  okMessage,
  badMessage,
}) {
  const [message, setMessage] = useState(null);
  useEffect(() => {
    if (message) {
      setTimeout(() => {
        setMessage(null);
      }, [3000]);
    }
  }, [message]);
  return (
    <div
      onClick={() => {
        if (canActivate) {
          setMessage(okMessage);
          callback();
        } else {
          setMessage(badMessage);
        }
      }}
      className={active ? "video-action-button button active" : "video-action-button button"}
    >
      {name} {content}
      <PopUpMessage message={message} isActive={message != null} />
    </div>
  );
}

export default VideoActionButton;
