const express = require("express");
const comments = require("./comments");
const {
  getVideo,
  updateVideo,
  deleteVideo,
  addVideo,
  likeVideo,
  dislikeVideo,
  getVideosDetailsByUserId,
  getMinimalVideoDetails,
  getVideosByUserId,
  increaseViews,
  getVideoRecommendations,
} = require("../controllers/videos");
const { authenticateTokenIfGot, authenticateToken } = require("../middleware/auth");

const router = express.Router({ mergeParams: true });
router.use("/:pid/comments", comments);

// Like and dislike video
router.put("/:pid/like", authenticateToken, likeVideo);
router.delete("/:pid/like", authenticateToken, dislikeVideo);

// Add a view
router.patch("/:pid/view", increaseViews);

// Getting videos by user
router.get("/", getVideosByUserId);
router.get("/details", authenticateToken, getVideosDetailsByUserId);
router.get("/details/:pid", getMinimalVideoDetails);

// CRUD for a single video
router.post("/", authenticateToken, addVideo);
router.get("/:pid", authenticateTokenIfGot, getVideo);
router.patch("/:pid", authenticateToken, updateVideo);
router.delete("/:pid", authenticateToken, deleteVideo);

// Getting video recommendations
router.get("/:pid/rec",authenticateTokenIfGot,getVideoRecommendations);

module.exports = router;
