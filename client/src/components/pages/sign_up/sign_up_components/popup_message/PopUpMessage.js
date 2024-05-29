import React from "react";
import "./PopUpMessage.css";
function PopUpMessage(props) {
  return (
    <div className="message-anchor">
      <div className={props.isActive ? "popup-message visible" : "popup-message hidden"}>
        {props.message}
      </div>
    </div>
  );
}

export default PopUpMessage;
