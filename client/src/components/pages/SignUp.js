import React, { useState } from "react";
import ValidationInput from "../ValidationInput";
import inputs from "../../data/inputs";
function SignUp(props) {
  const [nameInput, setNameInput] = useState("");
  const [passwordInput, setPasswordInput] = useState("");
  const [passwordValidationInput, setPasswordValidationInput] = useState("");
  const [emailInput, setEmailInput] = useState("");
  const [nameValid, setNameValid] = useState(true);
  const [passwordValid, setPasswordValid] = useState(true);
  const [emailValid, setEmailValid] = useState(true);
  const [verifyPasswordValid, setVerifyPasswordValid] = useState(true);

  function isValid(regex, inputValue) {
    let reg = new RegExp(regex);
    return inputValue.match(reg) != null;
  }

  function addUser(user) {
    props.setUsers([...props.users, user]);
  }
  function submit() {
    if (!isValid(inputs.email.regexValidationString, emailInput)) {
      setEmailValid(false);
      return;
    } else if (!isValid(inputs.password.regexValidationString, passwordInput)) {
      setPasswordValid(false);
      return;
    } else if (passwordValidationInput != passwordInput) {
      console.log(passwordValidationInput, passwordInput);
      setVerifyPasswordValid(false);
      return;
    } else if (!isValid(inputs.name.regexValidationString, nameInput)) {
      setNameValid(false);
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
        isValid={emailValid}
        hideMessage={() => setEmailValid(true)}
        setValue={setEmailInput}
      />
      <ValidationInput
        name={inputs.password.name}
        reqs={inputs.password.reqs}
        value={passwordInput}
        isValid={passwordValid}
        hideMessage={() => setPasswordValid(true)}
        setValue={setPasswordInput}
      />
      <ValidationInput
        name={inputs.validate_password.name}
        reqs={inputs.validate_password.reqs}
        isValid={verifyPasswordValid}
        hideMessage={() => setVerifyPasswordValid(true)}
        value={passwordValidationInput}
        setValue={setPasswordValidationInput}
      />
      <ValidationInput
        name={inputs.name.name}
        reqs={inputs.name.reqs}
        isValid={nameValid}
        hideMessage={() => setNameValid(true)}
        value={nameInput}
        setValue={setNameInput}
      />
      <button onClick={submit}>submit</button>
    </div>
  );
}

export default SignUp;
