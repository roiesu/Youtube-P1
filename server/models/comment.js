const mongoose = require("mongoose");
const User = require("./user");
const Video = require("./video");
const ObjectId = mongoose.Schema.Types.ObjectId;

const CommentSchema = new mongoose.Schema({
  user: { type: ObjectId, required: true, ref: "User" },
  video: { type: ObjectId, required: true, ref: "Video" },
  text: { type: String, required: true },
  date: { type: Date, default: Date.now },
  edited: { type: Boolean, default: false },
});

CommentSchema.pre("deleteOne", { document: true, query: false }, async (next, document) => {
  console.log("YES", document);
  try {
    await User.findByIdAndUpdate(document.user, { $pull: { comments: document._id } });
    await Video.findByIdAndUpdate(document.video, { $pull: { comments: document._id } });
    next();
  } catch (err) {
    next(err);
  }
});
const Comment = mongoose.model("Comment", CommentSchema);
module.exports = Comment;
