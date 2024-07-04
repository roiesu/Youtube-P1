const usernameValidator = {
  validator: (value) => {
    return value.match(/^[\w\d!@#$%^&*-_]{6,}$/);
  },
  message: "Invalid username",
};
const passwordValidator = {
  validator: (value) => {
    return value.match(
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!?#$%&*+,\-./@^_{}|~])[\w\d!?#$%&*+,\-./@^{}|~]{8,}$/
    );
  },
  message: "Invalid password",
};
const nameValidator = {
  validator: (value) => {
    return value.match(/^[\w\d-_!@#$%^&*]+( [\w\d-_!@#$%^&*]+)*$/);
  },
  message: "Invalid name",
};
module.exports = { usernameValidator, passwordValidator, nameValidator };
