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
const router = express.Router();

// add user
router.post("/", addUser);


// get user 
router.get("/:id", getUserById);

// update user 
router.put("/:id", updateUserById);
router.patch("/:id", updateUserById);

// delet user by ID
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

module.exports = router;
