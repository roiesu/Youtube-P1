import React, { useEffect, useState } from "react";
import "./SignUp.css";
import ValidationInput from "./sign_up_components/validation_input/ValidationInput";
import inputs from "../../../data/inputs.json";
import PopUpMessage from "../general_components/popup_message/PopUpMessage";
import { readFileIntoState } from "../../../utilities";
import { useNavigate, Link } from "react-router-dom";
import { useTheme } from "../general_components/ThemeContext";
import axios from "axios";

function SignUp(props) {
  const { theme } = useTheme();
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

  const navigate = useNavigate();

  useEffect(() => {
    if (generalError != null) {
      setTimeout(() => {
        setGeneralError(null);
      }, [5000]);
    }
  }, [generalError]);

  useEffect(() => {
    if (imageInput) {
      readFileIntoState(imageInput, setImage);
    }
  }, [imageInput]);

  function isValid(regex, inputValue) {
    let reg = new RegExp(regex);
    return inputValue.match(reg) != null;
  }

  async function submit() {
    if (!isValid(inputs.username.regexValidationString, usernameInput)) {
      setUsernameError(true);
      return;
    } else if (!isValid(inputs.password.regexValidationString, passwordInput)) {
      setPasswordError(true);
      return;
    } else if (passwordValidationInput != passwordInput) {
      setVerifyPasswordError(true);
      return;
    } else if (!isValid(inputs.name.regexValidationString, nameInput)) {
      setNameError(true);
      return;
    } else if (!imageInput) {
      setGeneralError("No profile picture selected");
      return;
    }
    const user = {
      username: usernameInput,
      password: passwordInput,
      name: nameInput,
      image: image,
    };
    try {
      const response = await axios.post("/api/users", user);
      if (response.status === 200) {
        const { token } = response.data;
        localStorage.setItem("token", token);
        props.setCurrentUser(usernameInput);
        navigate("/");
      }
    } catch (error) {
      if (error.response) {
        if (error.response.status === 400) {
          setGeneralError("Invalid input");
        } else if (error.response.status === 409) {
          setGeneralError("User with that username already exists");
        } else {
          setGeneralError("Couldn't create this user");
        }
      }
    }
  }

  return (
    <div className={`page signup-page ${theme}`}>
      <div className="main-component">
        <div className="header-div">
          {image ? <img className={"preview-picture"} src={image} /> : "No image uploaded"}
          <div>
            Already have an account? sign in <Link to="/sign-in">here!</Link>
          </div>
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
          <label className="choose-file">
            Choose File
            <input
              type="file"
              onChange={(e) => setImageInput(e.target.files[0])}
              accept=".jpg,.png,.jpeg"
            />
          </label>
          <button className="submit" onClick={submit}>
            submit
          </button>
        </div>
      </div>
    </div>
  );
}

export default SignUp;
