import React, { useState, useEffect } from "react";
import PopUpMessage from "../popup_message/PopUpMessage";

function ValidationInput(props) {
  useEffect(() => {
    if (props.error) {
      setTimeout(() => {
        props.showMessage(false);
      }, 5000);
    }
  }, [props.error]);
  return (
    <div className="validation-input-div">
      <label for={props.name}>{props.name.replace(/[-_]/g, " ")}</label>
      {/* {props.reqs && (
        <span
          className="reqs"
          onMouseMove={() => props.showMessage(true)}
          onMouseLeave={() => props.showMessage(false)}
        >
          ?
        </span>
      )} */}
      <PopUpMessage message={props.reqs} isActive={props.error} />
      <input
        // type={props.name.match("password") ? "password" : "text"}
        name={props.name}
        className="text-input"
        onChange={(e) => props.setValue(e.target.value)}
      />
    </div>
  );
}

export default ValidationInput;
