const express = require("express");
const router = express.Router();
const videos = require('./videos');
const users = require('./users'); 
const {loginUser} = require('../controllers/users')
const comments = require('./comments');

router.use("/videos", videos);
router.use("/users", users);
router.use("/comments", comments);
router.post("/tokens", loginUser);


module.exports = router;
