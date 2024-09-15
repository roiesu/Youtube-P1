const express = require("express");
const videos = require("./videos");
const {
  addUser,
  getUser,
  updateUser,
  deleteUser,
  getFullUserDetails,
} = require("../controllers/users");
const { authenticateToken } = require("../middleware/auth");

const router = express.Router();
router.use("/:id/videos", videos);

// CRUD for users
router.post("/", addUser);
router.get("/:id", getUser);
router.patch("/:id", authenticateToken, updateUser);
router.delete("/:id", authenticateToken, deleteUser);

// Get user's full details
router.get("/details/:id", authenticateToken, getFullUserDetails);

module.exports = router;
