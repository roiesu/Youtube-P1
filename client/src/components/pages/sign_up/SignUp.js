import React, { useEffect, useState } from "react";
import "./SignUp.css";
import ValidationInput from "./sign_up_components/validation_input/ValidationInput";
import inputs from "../../../settings/inputs.json";
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

  async function submit() {
    if (!usernameInput || !passwordInput || !passwordValidationInput || !nameInput) {
      setGeneralError("All fields are required");
      return;
    } else if (!imageInput) {
      setGeneralError("No profile picture selected");
      return;
    } else if (passwordValidationInput != passwordInput) {
      setGeneralError("Password validation must be equal to password");
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
        const loginResponse = await axios.post(`/api/tokens`, {
          username: usernameInput,
          password: passwordInput,
        });
        localStorage.setItem("token", loginResponse.data);
        props.setCurrentUser(usernameInput);
        navigate("/");
      }
    } catch (error) {
      console.log(error);
      if (error.response) {
        setGeneralError(error.response.data);
      } else {
        setGeneralError("Couldn't create user");
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
            setValue={setUsernameInput}
          />
          <ValidationInput
            name={inputs.password.name}
            reqs={inputs.password.reqs}
            setValue={setPasswordInput}
          />
          <ValidationInput
            name={inputs.validate_password.name}
            reqs={inputs.validate_password.reqs}
            setValue={setPasswordValidationInput}
          />
          <ValidationInput
            name={inputs.name.name}
            reqs={inputs.name.reqs}
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
