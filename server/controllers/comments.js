const User = require("../models/user");
const Comment = require("../models/comment");
const Video = require("../models/video");

async function addComment(req, res) {
  const { id, pid } = req.params;
  const { text } = req.body;
  if (!text) {
    return res.status(400).send("Invalid input");
  }
  try {
    const video = await Video.findById(pid).populate("uploader", ["username"]);
    if (!video || video.uploader.username !== id) {
      return res.status(404).send("Video not found");
    }
    const comment = new Comment({ user: req.user, video: pid, text }, { new: true });
    console.log(comment);
    await comment.save();
    video.comments.push(comment._id);
    await video.save();
    await User.findByIdAndUpdate(req.user, { $addToSet: { comments: comment._id } });
    await comment.populate("user", ["name", "image", "username", "_id"]);
    return res.status(201).send(comment);
  } catch (err) {
    if (err.kind == "ObjectId") {
      return res.status(404).send("Video not found");
    }
    return res.status(400).send(err.message);
  }
}

async function editComment(req, res) {
  const { id, pid, cid } = req.params;
  const { text } = req.body;
  if (!text) {
    return res.status(400).send("Invalid input");
  }
  try {
    const comment = await Comment.findOne({ _id: cid, video: pid })
      .populate({
        path: "video",
        select: ["_id", "uploader"],
        populate: { path: "uploader", select: ["username"] },
      })
      .populate("user", ["_id"]);
    if (!comment || comment.video.uploader.username != id) {
      return res.status(404).send("Comment not found");
    } else if (comment.user._id != req.user) {
      return res.sendStatus(401);
    }
    await comment.updateOne({ $set: { text, edited: true } });
    await comment.save();
    return res.sendStatus(201);
  } catch (err) {
    if (err.kind == "ObjectId") {
      return res.status(404).send("Comment not found");
    }
    return res.status(400).send(err.message);
  }
}

async function deleteComment(req, res) {
  const { id, pid, cid } = req.params;
  try {
    const comment = await Comment.findOne({ _id: cid, video: pid })
      .populate({
        path: "video",
        select: ["_id", "uploader"],
        populate: { path: "uploader", select: ["username"] },
      })
      .populate("user", ["_id"]);
    if (!comment || comment.video.uploader.username != id) {
      return res.status(404).send("Comment not found");
    } else if (comment.user._id != req.user) {
      return res.sendStatus(401);
    }
    await comment.deleteOne();
    return res.sendStatus(201);
  } catch (err) {
    if (err.kind == "ObjectId") {
      return res.status(404).send("Comment not found");
    }
    return res.status(400).send(err.message);
  }
}
async function commentIndex(req, res) {
  try {
    const comments = await Comment.aggregate([
      {
        $project: {
          _id: 1,
          userId: "$user",
          videoId: "$video",
          text: 1,
          date: 1,
          edited: 1,
        },
      },
    ]);
    return res.send(comments);
  } catch (err) {
    return res.status(400).send(err.message);
  }
}

module.exports = {
  addComment,
  editComment,
  deleteComment,
  commentIndex,
};
