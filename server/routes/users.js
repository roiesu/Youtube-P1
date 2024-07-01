const express = require("express");
const {
  addUser,
  loginUser,
  getUserById,
  updateUserById,
  deleteUserById
} = require("../controllers/users"); 
const {
  getVideo,
  updateVideo,
  deleteVideo,
  addVideo,
  likeVideo,
  dislikeVideo,
  getVideosByUserId
} = require("../controllers/videos");
const {
  getComment,
  addComment,
  deleteComment,
  editComment
} = require("../controllers/comments");
const Video = require("../models/video");
const router = express.Router();

// add user
router.post("/", addUser);


// get user 
router.get("/:id", getUserById);

// update user 
router.put("/:id", updateUserById);
router.patch("/:id", updateUserById);

// delete user by ID
router.delete("/:id", deleteUserById);

// get videos by user ID
router.get("/:id/videos", getVideosByUserId);

// CRUD for videos
router.get("/:id/videos/:pid", getVideo);
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
