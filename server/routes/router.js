const express = require("express");
const router = express.Router();
const videos = require("./videos");
const users = require("./users");
const { loginUser } = require("../controllers/users");

router.use("/videos", videos);
router.use("/users", users);
router.post("/tokens", loginUser);

module.exports = router;
