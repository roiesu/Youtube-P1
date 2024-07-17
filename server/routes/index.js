const { userIndex } = require("../controllers/users");
const { videoIndex } = require("../controllers/videos");
const { commentIndex } = require("../controllers/comments");

const router = require("express").Router();
router.get("/users", userIndex);
router.get("/videos", videoIndex);
router.get("/comments", commentIndex);

module.exports = router;
