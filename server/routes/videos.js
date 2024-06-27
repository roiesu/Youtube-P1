const express = require("express");
const { getVideos } = require("../controllers/videos");
const router = express.Router();

router.get("/", getVideos);

module.exports = router;
