const express = require("express");
const { addUser, getUser, updateUser, deleteUser } = require("../controllers/users");
const {
  getVideo,
  updateVideo,
  deleteVideo,
  addVideo,
  likeVideo,
  dislikeVideo,
  getVideosByUserId,
} = require("../controllers/videos");
const { getComment, addComment, deleteComment, editComment } = require("../controllers/comments");
const { authenticateTokenIfGot } = require("../middleware/auth");
const router = express.Router();

// CRUD for users
router.post("/", addUser);

router.get("/:id", getUser);
router.patch("/:id", updateUser);
router.delete("/:id", deleteUser);

// get videos by user ID
router.get("/:id/videos", getVideosByUserId);

// CRUD for videos
router.get("/:id/videos/:pid", authenticateTokenIfGot, getVideo);
router.patch("/:id/videos/:pid", updateVideo);
router.delete("/:id/videos/:pid", deleteVideo);
router.post("/:id/videos", addVideo);

// Like and dislike
router.put("/:id/videos/:pid/like", likeVideo);
router.delete("/:id/videos/:pid/like", dislikeVideo);

// CRUD for comment
router.get("/:id/videos/:pid/comments/:cid", getComment);
router.patch("/:id/videos/:pid/comments/:cid", editComment);
router.delete("/:id/videos/:pid/comments/:cid", deleteComment);
router.post("/:id/videos/:pid/comments", addComment);

module.exports = router;
