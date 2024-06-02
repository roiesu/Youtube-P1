import React, { useState } from 'react';

function SignIn(props) {
  const [usernameInput, setUsernameInput] = useState("");
  const [passwordInput, setPasswordInput] = useState("");
  const [errorMessage, setErrorMessage] = useState(false);

  function validateSignIn() {
    const user = props.users.find(user => user.username === usernameInput);
    if (user && user.username === usernameInput && user.password === passwordInput) {
      props.setCurrentUser(user);
      console.log("Successfully signed in");
    } else {
      setErrorMessage(true)
      console.log("Invalid username or password");
    }
  }

  return (
    <div className="page signin-page">
      <div className="main-component">
        <div className="header-div">
          <h1>Sign In</h1>
        </div>
        <div className="input-div">
        <label>username</label>
          {errorMessage && <div className="error-message">{errorMessage}</div>}
          <input
            type="text"
            name="Username"
            value={usernameInput}
            onChange={(e) => setUsernameInput(e.target.value)}
          />
          <label>password</label>
          <input
            type="text"
            name="Password"
            value={passwordInput}
            onChange={(e) => setPasswordInput(e.target.value)}
          />
          <button onClick={validateSignIn}>Sign In</button>
        </div>
      </div>
    </div>
  );
}

export default SignIn