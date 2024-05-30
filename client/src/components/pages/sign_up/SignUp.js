import React, { useEffect, useState } from "react";
import "./SignUp.css";
import ValidationInput from "./sign_up_components/validation_input/ValidationInput";
import inputs from "../../../data/inputs.json";
import PopUpMessage from "./sign_up_components/popup_message/PopUpMessage";
import { readImageIntoState } from "../../../utilities";
function SignUp(props) {
  const [usernameInput, setUsernameInput] = useState("");
  const [passwordInput, setPasswordInput] = useState("");
  const [passwordValidationInput, setPasswordValidationInput] = useState("");
  const [nameInput, setNameInput] = useState("");
  const [imageInput, setImageInput] = useState();
  const [image, setImage] = useState();
  const [nameError, setNameError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [usernameError, setUsernameError] = useState(false);
  const [verifyPasswordError, setVerifyPasswordError] = useState(false);
  const [generalError, setGeneralError] = useState();

  useEffect(() => {
    if (generalError != null) {
      setTimeout(() => {
        setGeneralError(null);
      }, [5000]);
    }
  }, [generalError]);

  useEffect(() => {
    if (imageInput) {
      readImageIntoState(imageInput, setImage);
    }
  }, [imageInput]);

  function isValid(regex, inputValue) {
    let reg = new RegExp(regex);
    return inputValue.match(reg) != null;
  }

  function addUser(user) {
    props.setUsers([...props.users, user]);
  }
  function submit() {
    if (!isValid(inputs.username.regexValidationString, usernameInput)) {
      setUsernameError(true);
      return;
    } else if (!isValid(inputs.password.regexValidationString, passwordInput)) {
      setPasswordError(true);
      return;
    } else if (passwordValidationInput != passwordInput) {
      console.log(passwordValidationInput, passwordInput);
      setVerifyPasswordError(true);
      return;
    } else if (!isValid(inputs.name.regexValidationString, nameInput)) {
      setNameError(true);
      return;
    } else if (!imageInput) {
      setGeneralError("No profile picture selected");
      return;
    }
    const exists = props.users.find((user) => user.username === usernameInput);
    if (exists) {
      setGeneralError("User with that username address already exists");
      return;
    }
    const user = {};
    user[inputs.username.name] = usernameInput;
    user[inputs.password.name] = passwordInput;
    user[inputs.name.name] = nameInput;
    user.image = image;
    addUser(user);
    props.setCurrentUser(user);
  }

  return (
    <div className="page signup-page">
      <div className="main-component">
        <div className="header-div">
          {image ? <img className={"profile-pic"} src={image} /> : "Hello"}
        </div>
        <div className="input-div">
          <PopUpMessage message={generalError} isActive={generalError != null} />

          <ValidationInput
            name={inputs.username.name}
            reqs={inputs.username.reqs}
            value={usernameInput}
            error={usernameError}
            showMessage={setUsernameError}
            setValue={setUsernameInput}
          />
          <ValidationInput
            name={inputs.password.name}
            reqs={inputs.password.reqs}
            value={passwordInput}
            error={passwordError}
            showMessage={setPasswordError}
            setValue={setPasswordInput}
          />
          <ValidationInput
            name={inputs.validate_password.name}
            reqs={inputs.validate_password.reqs}
            error={verifyPasswordError}
            showMessage={setVerifyPasswordError}
            value={passwordValidationInput}
            setValue={setPasswordValidationInput}
          />
          <ValidationInput
            name={inputs.name.name}
            reqs={inputs.name.reqs}
            error={nameError}
            showMessage={setNameError}
            value={nameInput}
            setValue={setNameInput}
          />
          <input
            type="file"
            onChange={(e) => setImageInput(e.target.files[0])}
            accept=".jpg,.png,.jpeg"
          />
          <button onClick={submit}>submit</button>
        </div>
      </div>
    </div>
  );
}

export default SignUp;
