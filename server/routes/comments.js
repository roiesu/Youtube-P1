const express = require("express");
const Comment = require("../models/comment");
const Video = require("../models/video");
const {
  getComment,
  addComment,
  deleteComment,
  editComment
} = require("../controllers/comments");
const router = express.Router();

router.get("/", (req, res) => {
  res.send("comments");
});

router.post("/new", async (req, res) => {
  const { user, video, text} = req.body;n
  if (!user || !video || !text) {
    return res.status(400).send("Can't add comment");
  }
  let commentDetails = { user, video, text};
  const comment = new Comment(commentDetails);
  await comment.save();
  res.send("Comment added");
});

// CRUD for cooment
router.get("/:id/comments/:pid", getComment);
router.patch("/:id/comments/:pid", editComment);
router.delete("/:id/comments/:pid", deleteComment);
router.post("/:id/comments", addComment);

module.exports = router;