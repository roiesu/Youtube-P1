import React, { useState } from "react";

function ValidationInput(props) {
//   const [messageShown, showMessage] = useState(false);
  return (
    <div className="validation-input-div">
      <label for={props.name}>{props.name.replace(/[-_]/g, " ")}</label>
      {props.reqs && <span className="reqs">?</span>}
      <input
        type={props.name.match("password") ? "password" : "text"}
        name={props.name}
        className="text-input"
        onChange={(e) => props.setValue(e.target.value)}
      />
    </div>
  );
}

export default ValidationInput;
