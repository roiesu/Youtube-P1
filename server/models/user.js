const mongoose = require("mongoose");
const ObjectId = mongoose.Schema.Types.ObjectId;

const UserSchema = new mongoose.Schema({
  username: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  name: { type: String, required: true },
  image: { type: String, required: true },
  comments: { type: [ObjectId], default: [] },
  // videos: { type: [ObjectId], default: [] }, 
  videos: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Video' }],
  likes: { type: [ObjectId], default: [] },
});

const User = mongoose.model("User", UserSchema);
module.exports = User;
