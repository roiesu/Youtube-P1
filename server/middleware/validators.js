const usernameValidator = {
  validator: (value) => {
    return value.match(/^[\w\d!@#$%^&*-_]{6,}$/);
  },
  message: "Must be above 6 letters, only letters numbers and special symbols.",
};
const passwordValidator = {
  validator: (value) => {
    return value.match(
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!?#$%&*+,\-./@^_{}|~])[\w\d!?#$%&*+,\-./@^{}|~]{8,}$/
    );
  },
  message:
    "Must be above 8 letters, must contain a capital and a small letter, a number and a special symbol.",
};
const nameValidator = {
  validator: (value) => {
    return value.match(/^[\w\d-_!@#$%^&*]+( [\w\d-_!@#$%^&*]+)*$/);
  },
  message: "Contains only letters, numbers, spaces and special symbols.",
};
module.exports = { usernameValidator, passwordValidator, nameValidator };
