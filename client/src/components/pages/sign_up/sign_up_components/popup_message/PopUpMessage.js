import React from "react";
import "./PopUpMessage.css";
function PopUpMessage(props) {
  return (
    <div className="message-anchor">
      {props.isActive ? <div className="popup-message">{props.message}</div> : ""}
    </div>
  );
  // return (
  //   <div className="message-anchor">
  //     <div className="popup-message">{props.message}</div>
  //   </div>
  // );
}

export default PopUpMessage;
