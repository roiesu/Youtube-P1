const express = require("express");
const {
  addUser,
  getUser,
  updateUser,
  deleteUser,
  getFullUserDetails,
} = require("../controllers/users");
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
const { authenticateTokenIfGot, authenticateToken } = require("../middleware/auth");
const router = express.Router();

// CRUD for users
router.post("/", addUser);

router.get("/:id", getUser);
router.get("/details/:id", authenticateToken, getFullUserDetails);
router.patch("/:id", authenticateToken, updateUser);
router.delete("/:id", authenticateToken, deleteUser);
router.get("/:id/videos", getVideosByUserId);

// CRUD for videos
router.get("/:id/videos/:pid", authenticateTokenIfGot, getVideo);
router.patch("/:id/videos/:pid", authenticateToken, updateVideo);
router.delete("/:id/videos/:pid", authenticateToken, deleteVideo);
router.post("/:id/videos", authenticateToken, addVideo);

// Like and dislike
router.put("/:id/videos/:pid/like", authenticateToken, likeVideo);
router.delete("/:id/videos/:pid/like", authenticateToken, dislikeVideo);

// CRUD for comment
router.get("/:id/videos/:pid/comments/:cid", authenticateToken, getComment);
router.patch("/:id/videos/:pid/comments/:cid", authenticateToken, editComment);
router.delete("/:id/videos/:pid/comments/:cid", authenticateToken, deleteComment);
router.post("/:id/videos/:pid/comments", authenticateToken, addComment);

module.exports = router;
