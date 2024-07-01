const User = require("../models/user");
const Comment = require("../models/comment");
const Video = require("../models/video");

  async function getComments(req, res) {
    const { videoId } = req.params;
    let comments = [];
    try {
      comments = await Comment.find({ video: videoId })
        .select(["_id", "uploader", "text", "date"])
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
    const { text, user } = req.body;
    if (!text || !user) {
      return res.status(400).send("Invalid input");
    }
    try {
      const comment = new Comment({ user, video: pid, text});
      const video = await Video.findOneAndUpdate({uploader: id , _id: pid}, { $addToSet: { comments: comment.id } });
      if(!video){
        return res.status(404).send("Video not found")
      }
      await comment.save();
      await User.findByIdAndUpdate(id ,{ $addToSet: {comments :comment}});
      return res.status(201).send("OK");
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
      const comment = await Comment.findOneAndUpdate(
        { _id: cid, user: id, video: pid },
        { $set: { text, edited: true } },
        { new: true }
      );
      if (!comment) {
        return res.status(404).send("Comment not found");
      }
      return res.status(201).send("OK");
    } catch (err) {
      console.log(err.message);
    }
    return res.status(400).send("Invalid");
  }
  
  
  async function deleteComment(req, res) {
    const { id, pid, cid} = req.params;
    try {
      const comment = await Comment.findOne({ _id: cid, user: id, video: pid});
      if (!comment) {
        return res.status(404).send("Comment not found");
      }
      await Video.findByIdAndUpdate(comment.video, { $pull: { comments: comment.id } });
      await User.findByIdAndUpdate(comment.user, { $pull: { comments: comment.id } });
      await Comment.findByIdAndDelete({ _id: cid, user: id, video: pid})
      return res.status(200).send("OK");
    
    } catch (err) {
      console.log(err.message);
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