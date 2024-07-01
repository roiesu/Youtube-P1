const mongoose = require("mongoose");
const ObjectId = mongoose.Schema.Types.ObjectId;

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

VideoSchema.pre("remove", async function (next) {
  try {
    await Comment.deleteMany({ video: this._id });
    next();
  } catch (err) {
    next(err);
  }
});

const Video = mongoose.model("Video", VideoSchema);
module.exports = Video;
