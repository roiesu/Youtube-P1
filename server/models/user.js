const mongoose = require("mongoose");
const preDeleteUser = require("../middleware/preDeleteUser");
const { nameValidator, passwordValidator, usernameValidator } = require("../middleware/validators");
const ObjectId = mongoose.Schema.Types.ObjectId;

const UserSchema = new mongoose.Schema({
  username: {
    type: String,
    required: true,
    unique: true,
    username: usernameValidator,
  },
  password: {
    type: String,
    required: true,
    validate: passwordValidator,
  },

  name: {
    type: String,
    required: true,
    validate: nameValidator,
  },
  image: { type: String, required: true },
  comments: [{ type: ObjectId, ref: "Comment" }],
  videos: [{ type: ObjectId, ref: "Video" }],
  likes: [{ type: ObjectId, ref: "Video" }],
});

UserSchema.post("deleteOne", { document: true }, preDeleteUser);
const User = mongoose.model("User", UserSchema);
module.exports = User;
