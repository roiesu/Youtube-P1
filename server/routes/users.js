const express = require("express");
const User = require("../models/user");
const router = express.Router();

router.get("/", (req, res) => {
  res.send("users");
});

router.get("/test", async (req, res) => {
  const all = await User.find({});
  
});
module.exports = router;
