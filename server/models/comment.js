const mongoose = require("mongoose");
const ObjectId = mongoose.Schema.Types.ObjectId;

const CommentSchema = new mongoose.Schema({
  user: { type: ObjectId, required: true, ref: "User" },
  video: { type: ObjectId, required: true, ref: "Video" },
  text: { type: String, required: true },
  date: { type: Date, default: Date.now },
  edited: { type: Boolean, default: false },
});

CommentSchema.post("deleteOne", { document: true, query: false }, async (document, next) => {
  try {
    await document.video.updateOne({ $pull: { comments: document._id } });
    await document.user.updateOne({ $pull: { comments: document._id } });
    await document.user.save();
    await document.video.save();
    next();
  } catch (err) {
    next(err);
  }
});
const Comment = mongoose.model("Comment", CommentSchema);
module.exports = Comment;
