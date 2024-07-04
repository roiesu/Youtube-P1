import React from "react";
import "./PopUpMessage.css";
function PopUpMessage({ isActive, message }) {
  return (
    <div className="message-anchor">
      <div className={isActive ? "popup-message visible" : "popup-message hidden"}>{message}</div>
    </div>
  );
}

export default PopUpMessage;
