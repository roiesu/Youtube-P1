import React, { useState } from "react";
import ValidationInput from "../ValidationInput";
import inputs from "../../data/inputs";
function SignUp() {
  const [nameInput, setNameInput] = useState("");
  const [passwordInput, setPasswordInput] = useState("");
  const [passwordValidationInput, setPasswordValidationInput] = useState("");
  const [emailInput, setEmailInput] = useState("");
  const [nameValid, setNameValid] = useState(false);
  const [passwordValid, setPasswordValid] = useState(false);
  const [emailValid, setEmailValid] = useState(false);
  const [verifyPasswordValid, setVerifyPasswordValid] = useState(false);

  function isValid(regex, inputValue) {
    let reg = new RegExp(regex);
    return inputValue.match(reg) != null;
  }

  function submit() {
    if (!isValid(inputs.email.regexValidationString, emailInput)) {
      setEmailValid(false);
      return;
    }
    else if (!isValid(inputs.password.regexValidationString, passwordInput)) {
      setPasswordValid(false);
      return;
    }
    else if (!isValid(inputs.password_validation.regexValidationString, passwordValid)) {
      setPasswordValid(false);
      return;
    }
    else if (!isValid(inputs.name.regexValidationString, nameInput)) {
      setNameValid(false);
      return;
    }
    
  }
  return (
    <div className="page signup-page">
      <ValidationInput
        name={inputs.email.name}
        reqs={inputs.email.reqs}
        isValid={emailValid}
        value={emailInput}
        setValue={setEmailInput}
      />
      <ValidationInput
        name={inputs.password.name}
        reqs={inputs.password.reqs}
        isValid={passwordValid}
        value={passwordInput}
        setValue={setPasswordInput}
      />
      <ValidationInput
        name={inputs.validate_password.name}
        reqs={inputs.validate_password.reqs}
        isValid={verifyPasswordValid}
        value={passwordValidationInput}
        setValue={setPasswordValidationInput}
      />
      <ValidationInput
        name={inputs.name.name}
        reqs={inputs.name.reqs}
        isValid={nameValid}
        value={nameInput}
        setValue={setNameInput}
      />
      <button onClick={submit}>submit</button>
    </div>
  );
}

export default SignUp;
