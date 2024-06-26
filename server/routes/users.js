const express = require("express");
const User = require("../models/user");
const router = express.Router();

router.get("/", (req, res) => {
  res.send("users");
});

router.get("/test", async (req, res) => {
  let userDetails = { name: "YES", username: "YESw2", password: "AYWA" };
  const user = new User(userDetails);
  await user.save();
  res.send("OK");
});
module.exports = router;
