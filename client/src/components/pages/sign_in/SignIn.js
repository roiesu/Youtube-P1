import React, { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "./SignIn.css";
import PopUpMessage from "../general_components/popup_message/PopUpMessage";
import { useTheme } from "../general_components/ThemeContext";
import axios from "axios";

function SignIn(props) {
  const { theme } = useTheme();

  const [usernameInput, setUsernameInput] = useState("");
  const [passwordInput, setPasswordInput] = useState("");
  const [errorMessage, setErrorMessage] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (errorMessage)
      setTimeout(() => {
        setErrorMessage(false);
      }, 4000);
  }, [errorMessage]);

  async function validateSignIn() {
    if (!usernameInput || !passwordInput) {
      setErrorMessage("Username and password are required!");
      return;
    }

    try {
      const response = await axios.post("/api/tokens", {
        username: usernameInput,
        password: passwordInput,
      });

      if (response.status === 200) {
        const token = response.data;
        localStorage.setItem("token", token);
        props.setCurrentUser(usernameInput);
        navigate("/");
      }
    } catch (error) {
      if (error.response) {
        if (error.response.status === 404) {
          setErrorMessage("Invalid username or password");
        } else {
          setErrorMessage("An unexpected error occurred");
        }
      } else {
        setErrorMessage("An unexpected error occurred");
      }
    }
  }

  return (
    <div className={`page signin-page ${theme}`}>
      <div className="main-component">
        <div className="header-div">
          <h1>Sign In</h1>
          <div>
            First time? sign up <Link to="/sign-up">here!</Link>
          </div>
        </div>
        <div className="input-div">
          <div className="validation-input-div">
            <PopUpMessage message="Username or password are incorrect" isActive={errorMessage} />
            <label>username</label>
            {errorMessage && <div className="error-message">{errorMessage}</div>}
            <input
              className="text-input"
              type="text"
              name="username"
              value={usernameInput}
              onChange={(e) => setUsernameInput(e.target.value)}
            />
          </div>
          <div className="validation-input-div">
            <label>password</label>
            <input
              className="text-input"
              type="password"
              name="password"
              value={passwordInput}
              onChange={(e) => setPasswordInput(e.target.value)}
            />
          </div>
          <button className="submit" onClick={validateSignIn}>
            Sign In
          </button>
        </div>
      </div>
    </div>
  );
}

export default SignIn;
