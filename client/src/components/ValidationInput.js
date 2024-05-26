import React, { useState, useEffect } from "react";

function ValidationInput(props) {
  useEffect(() => {
    if (!props.isValid) {
      setTimeout(() => {
        props.hideMessage();
      }, 5000);
    }
  }, [props.isValid]);
  return (
    <div className="validation-input-div">
      <label for={props.name}>{props.name.replace(/[-_]/g, " ")}</label>
      {props.reqs && <span className="reqs">?</span>}
      {!props.isValid && <span className="invalid-message">{props.reqs}</span>}
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
