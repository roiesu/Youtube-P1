import React, { useState } from "react";
import ValidationInput from "./sign_up_components/validation_input/ValidationInput";
import inputs from "../../../data/inputs.json";
function SignUp(props) {
  const [nameInput, setNameInput] = useState("");
  const [passwordInput, setPasswordInput] = useState("");
  const [passwordValidationInput, setPasswordValidationInput] = useState("");
  const [emailInput, setEmailInput] = useState("");
  const [nameError, setNameError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [emailError, setEmailError] = useState(false);
  const [verifyPasswordError, setVerifyPasswordError] = useState(false);

  function isValid(regex, inputValue) {
    let reg = new RegExp(regex);
    return inputValue.match(reg) != null;
  }

  function addUser(user) {
    props.setUsers([...props.users, user]);
  }
  function submit() {
    if (!isValid(inputs.email.regexValidationString, emailInput)) {
      setEmailError(true);
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
    }
    const exists = props.users.find((user) => user.email === emailInput);
    if (exists) {
      console.log("User with that email address already exists");
      return;
    }
    const user = {};
    user[inputs.email.name] = emailInput;
    user[inputs.password.name] = passwordInput;
    user[inputs.name.name] = nameInput;
    addUser(user);
    props.setCurrentUser(user);
  }
  return (
    <div className="page signup-page">
      <ValidationInput
        name={inputs.email.name}
        reqs={inputs.email.reqs}
        value={emailInput}
        error={emailError}
        showMessage={setEmailError}
        setValue={setEmailInput}
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
      <button onClick={submit}>submit</button>
    </div>
  );
}

export default SignUp;
