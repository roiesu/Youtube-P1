import React, { useEffect, useState } from "react";
import "./SignUp.css";
import ValidationInput from "./sign_up_components/validation_input/ValidationInput";
import inputs from "../../../settings/inputs.json";
import { readFileIntoState, callWithEnter, simpleErrorCatcher } from "../../../utilities";
import { useNavigate, Link } from "react-router-dom";
import { useTheme } from "../general_components/ThemeContext";
import axios from "axios";

function SignUp({ setCurrentUser, showToast }) {
  const { theme } = useTheme();
  const [usernameInput, setUsernameInput] = useState("");
  const [passwordInput, setPasswordInput] = useState("");
  const [passwordValidationInput, setPasswordValidationInput] = useState("");
  const [nameInput, setNameInput] = useState("");
  const [imageInput, setImageInput] = useState();

  const [image, setImage] = useState();
  const navigate = useNavigate();

  useEffect(() => {
    if (imageInput) {
      readFileIntoState(imageInput, setImage);
    }
  }, [imageInput]);

  async function submit() {
    if (!usernameInput || !passwordInput || !passwordValidationInput || !nameInput) {
      showToast("All fields are required");
      return;
    } else if (!imageInput) {
      showToast("No profile picture selected");
      return;
    } else if (passwordValidationInput != passwordInput) {
      showToast("Password validation must be equal to password");
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
        setCurrentUser(usernameInput);
        navigate("/");
      }
    } catch (err) {
      simpleErrorCatcher(err, null, navigate, showToast);
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
        <div className="input-div" onKeyDown={(e) => callWithEnter(e, submit)}>
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
