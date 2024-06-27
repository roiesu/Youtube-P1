const mongoose = require("mongoose");
const ObjectId = mongoose.Schema.Types.ObjectId;

const VideoSchema = new mongoose.Schema({
  name: { type: String, required: true },
  uploader: { type: ObjectId, required: true },
  src: { type: String, required: true },
  likes: { type: [ObjectId], default: [] },
  views: { type: Number, default: 0 },
  date: { type: Date, default: Date.now },
  description: { type: String, default: "" },
  tags: { type: [String], default: [] },
  comments: { type: [ObjectId], default: [] },
});

const Video = mongoose.model("Video", VideoSchema);
module.exports = Video;
