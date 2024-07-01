const mongoose = require("mongoose");
const { deletePublicFile } = require("../utils");
const ObjectId = mongoose.Schema.Types.ObjectId;
const User = require("./user");
const Comment = require("./comment");

const VideoSchema = new mongoose.Schema({
  name: { type: String, required: true },
  uploader: { type: ObjectId, required: true, ref: "User" },
  src: { type: String, required: true },
  likes: [{ type: ObjectId, ref: "User" }],
  views: { type: Number, default: 0 },
  date: { type: Date, default: Date.now },
  description: { type: String, default: "" },
  tags: { type: [String], default: [] },
  comments: [{ type: ObjectId, ref: "Comment" }],
});

VideoSchema.pre("deleteOne", { document: true, query: false }, async (next, document) => {
  try {
    // Remove likes
    for (like of document.likes) {
      await User.findByIdAndUpdate(like, { $pull: { likes: document._id } });
    }
    // Remove the comments
    for (commentId of document.comments) {
      await Comment.findById(commentId).deleteOne();
    }

    // Remove from user
    await User.findByIdAndUpdate(document.uploader, { $pull: { videos: document._id } });

    // Remove the file
    deletePublicFile("video", document.src);
    next();
  } catch (err) {
    next(err);
  }
});

const Video = mongoose.model("Video", VideoSchema);
module.exports = Video;
