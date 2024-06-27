const mongoose = require("mongoose");
const ObjectId = mongoose.Schema.Types.ObjectId;

const CommentSchema = new mongoose.Schema({
  user: { type: ObjectId, required: true },
  video: { type: ObjectId, required: true },
  text: { type: String, required: true },
  date: { type: Date, default: Date.now },
  edited: { type: Boolean, default: false },
});

const Comment = mongoose.model("Comment", CommentSchema);
module.exports = Comment;
