import React from "react";

function PopUpMessage(props) {
  return <>{props.isActive ? <div className="popup-message">{props.message}</div> : ""}</>;
}

export default PopUpMessage;
