import React, { useState } from "react";
import PopUpMessage from "../../../general_components/popup_message/PopUpMessage";
import "./ValidationInput.css";
import IconQuestionCircle from "../../../../icons/IconQuestionCircle";

function ValidationInput({ name, reqs, setValue }) {
  const [showing, setShowing] = useState(false);
  return (
    <div className="validation-input-div">
      <div className="input-div-header">
        <label htmlFor={name}>{name.replace(/[-_]/g, " ")}</label>
        {reqs && (
          <span
            className="reqs"
            onMouseMove={() => setShowing(true)}
            onMouseLeave={() => setShowing(false)}
          >
            <IconQuestionCircle />
          </span>
        )}
      </div>
      <div className="input-div-content">
        <input
          type={name.match("password") ? "password" : "text"}
          name={name}
          className="text-input"
          onChange={(e) => setValue(e.target.value)}
        />
        <PopUpMessage message={reqs} isActive={showing} />
      </div>
    </div>
  );
}

export default ValidationInput;
