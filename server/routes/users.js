const express = require("express");
const User = require("../models/user");
const {
  getVideo,
  updateVideo,
  deleteVideo,
  addVideo,
  likeVideo,
  dislikeVideo,
} = require("../controllers/videos");
const router = express.Router();

router.get("/", (req, res) => {
  res.send("users");
});

router.post("/new", async (req, res) => {
  const { name, username, password } = req.body;
  if (!name || !username || !password) {
    console.log(name, username, password);
    return res.status(400).send("Can't create user");
  }
  let userDetails = { name, username, password };
  const user = new User(userDetails);
  await user.save();
  res.send("User " + name + " created");
});

router.get("/:id/videos/:pid", getVideo);
router.patch("/:id/videos/:pid", updateVideo);
router.delete("/:id/videos/:pid", deleteVideo);
router.post("/:id/videos", addVideo);
router.put("/:id/videos/:pid/like", likeVideo);
router.delete("/:id/videos/:pid/like", dislikeVideo);
module.exports = router;
