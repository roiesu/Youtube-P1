const User = require("../models/user");
const Comment = require("../models/comment");
const Video = require("../models/video");

async function getComments(req, res) {
  const { videoId } = req.params;
  let comments = [];
  try {
    comments = await Comment.find({ video: videoId })
      .select(["_id", "-password"])
      .sort({ date: "desc" });
  } catch (err) {
    console.log(err.message);
  }
  return res.status(200).send(comments);
}

async function getComment(req, res) {
  const { id, pid, cid } = req.params;
  let comment;
  try {
    comment = await Comment.findOne({ _id: cid, user: id, video: pid });
    if (comment) {
      return res.status(200).send(comment);
    }
    return res.status(404).send("Comment not found");
  } catch (err) {
    console.log(err.message);
  }
}

async function addComment(req, res) {
  const { id, pid } = req.params;
  const { text } = req.body;
  if (!text) {
    return res.status(400).send("Invalid input");
  }
  try {
    const video = await Video.findById(pid).populate("uploader", ["username"]);
    if (!video || video.uploader.username !== id) {
      return res.sendStatus(404);
    }
    const comment = new Comment({ user: req.user, video: pid, text });
    await comment.save();
    video.comments.push(comment._id);
    await video.save();
    await User.findByIdAndUpdate(req.user, { $addToSet: { comments: comment._id } });
    await comment.populate("user", ["name", "image", "username", "-_id"]);
    return res.status(201).send(comment);
  } catch (err) {
    console.log(err.message);
  }
  return res.status(400).send("Couldn't add comment");
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
      return res.sendStatus(404);
    } else if (comment.user._id != req.user) {
      return res.sendStatus(401);
    }
    await comment.updateOne({ $set: { text, edited: true } });
    await comment.save();
    return res.sendStatus(201);
  } catch (err) {
    console.log(err.message);
  }
  return res.status(400).send("Couldn't edit comment");
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
      return res.sendStatus(404);
    } else if (comment.user._id != req.user) {
      return res.sendStatus(401);
    }
    await comment.deleteOne();
    return res.sendStatus(201);
  } catch (err) {
    console.log(err);
  }
  return res.status(400).send("Couldn't delete comment");
}

module.exports = {
  getComments,
  getComment,
  addComment,
  editComment,
  deleteComment,
};
