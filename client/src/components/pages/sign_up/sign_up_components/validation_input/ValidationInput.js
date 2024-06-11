import React, { useState, useEffect } from "react";
import PopUpMessage from "../../../general_components/popup_message/PopUpMessage";
import "./ValidationInput.css";
import IconQuestionCircle from "../../../../icons/IconQuestionCircle";

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
      <div className="input-div-header">
        <label for={props.name}>{props.name.replace(/[-_]/g, " ")}</label>
        {props.reqs && (
          <span
            className="reqs"
            onMouseMove={() => props.showMessage(true)}
            onMouseLeave={() => props.showMessage(false)}
          >
            <IconQuestionCircle />
          </span>
        )}
      </div>
      <div className="input-div-content">
        <input
          type={props.name.match("password") ? "password" : "text"}
          name={props.name}
          className="text-input"
          onChange={(e) => props.setValue(e.target.value)}
        />
        <PopUpMessage message={props.reqs} isActive={props.error} />
      </div>
    </div>
  );
}

export default ValidationInput;
