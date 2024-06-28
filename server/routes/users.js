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
  dislikeVideo
} = require("../controllers/videos");
const router = express.Router();

router.post("/new", addUser);

router.post("/tokens", loginUser);

// add user
router.post("/new", addUser);

// user login
router.post("/tokens", loginUser);

// get user 
router.get("/:id", getUserById);

// update user 
router.put("/:id", updateUserById);
router.patch("/:id", updateUserById);

// delet user by ID
router.delete("/:id", deleteUserById);

// CRUD for videos
router.get("/:id/videos/:pid", getVideo);
router.patch("/:id/videos/:pid", updateVideo);
router.delete("/:id/videos/:pid", deleteVideo);
router.post("/:id/videos", addVideo);

// Like and dislike
router.put("/:id/videos/:pid/like", likeVideo);
router.delete("/:id/videos/:pid/like", dislikeVideo);

module.exports = router;
