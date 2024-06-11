import React, { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "./SignIn.css";
import PopUpMessage from "../general_components/popup_message/PopUpMessage";

function SignIn(props) {
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

  function validateSignIn() {
    const user = props.users.find((user) => user.username === usernameInput);
    if (user && user.username === usernameInput && user.password === passwordInput) {
      props.setCurrentUser(user);
      navigate("/");
    } else {
      setErrorMessage(true);
    }
  }

  return (
    <div className="page signin-page">
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
