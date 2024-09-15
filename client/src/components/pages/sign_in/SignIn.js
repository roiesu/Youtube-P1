import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "./SignIn.css";
import { useTheme } from "../general_components/ThemeContext";
import axios from "axios";
import { callWithEnter, simpleErrorCatcher } from "../../../utilities";

function SignIn({ setCurrentUser, showToast }) {
  const { theme } = useTheme();

  const [usernameInput, setUsernameInput] = useState("");
  const [passwordInput, setPasswordInput] = useState("");
  const navigate = useNavigate();

  async function validateSignIn() {
    if (!usernameInput || !passwordInput) {
      showToast("Username and password are required!");
      return;
    }

    try {
      const response = await axios.post("/api/tokens", {
        username: usernameInput,
        password: passwordInput,
      });

      if (response.status === 201) {
        const token = response.data;
        localStorage.setItem("token", token);
        setCurrentUser(usernameInput);
        navigate("/");
      }
    } catch (err) {
      simpleErrorCatcher(err, null, navigate, showToast);
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
        <div className="input-div" onKeyDown={(e) => callWithEnter(e, validateSignIn)}>
          <div className="validation-input-div">
            <label>username</label>
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
