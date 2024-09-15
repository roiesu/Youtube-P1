const express = require("express");
const router = express.Router();
const users = require("./users");
const tokens = require("./tokens");
const index = require("./index");
const { getVideos } = require("../controllers/videos");
router.get("/videos", getVideos);
router.use("/users", users);
router.use("/tokens", tokens);
router.use("/index", index);

module.exports = router;
