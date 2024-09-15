const express = require("express");

const { addComment, deleteComment, editComment } = require("../controllers/comments");
const { authenticateToken } = require("../middleware/auth");

const router = express.Router({ mergeParams: true });

// CRUD for comment
router.post("/", authenticateToken, addComment);
router.patch("/:cid", authenticateToken, editComment);
router.delete("/:cid", authenticateToken, deleteComment);

module.exports = router;
