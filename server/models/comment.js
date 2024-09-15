const mongoose = require("mongoose");
const ObjectId = mongoose.Schema.Types.ObjectId;
const { preDeleteComment } = require("../middleware/preDelete");
const CommentSchema = new mongoose.Schema({
  user: { type: ObjectId, required: true, ref: "User" },
  video: { type: ObjectId, required: true, ref: "Video" },
  text: { type: String, required: true },
  date: { type: Date, default: Date.now },
  edited: { type: Boolean, default: false },
});

CommentSchema.pre("deleteOne", { document: true, query: false }, preDeleteComment);
const Comment = mongoose.model("Comment", CommentSchema);
module.exports = Comment;
