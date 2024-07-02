const mongoose = require("mongoose");
const Video = require("./video");
const ObjectId = mongoose.Schema.Types.ObjectId;

const UserSchema = new mongoose.Schema({
  username: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  name: { type: String, required: true },
  image: { type: String, required: true },
  comments: [{ type: ObjectId, ref: "Comment" }],
  videos: [{ type: ObjectId, ref: "Video" }],
  likes: [{ type: ObjectId, ref: "Video" }],
});

UserSchema.post("deleteOne", { document: true }, async (document, next) => {
  for (videoId of document.likes) {
    await Video.findById(videoId).updateOne({ $pull: { likes: document._id } });
  }
  for (commentId of document.comments) {
    await Comment.findById(commentId).deleteOne();
  }

  for (videoId of document.videos) {
    await Video.findById(videoId).deleteOne();
  }
  next();
});
const User = mongoose.model("User", UserSchema);
module.exports = User;
