const User = require("../models/user");
const Video = require("../models/video");
const { write64FileWithCopies } = require("../utils");
async function getVideos(req, res) {
  let videos = [];
  try {
    videos = await Video.find({})
      .select(["_id", "uploader", "views", "src", "date"])
      .sort({ views: "desc" })
      .limit(10);
  } catch (err) {}
  return res.status(200).send(videos);
}
async function getVideo(req, res) {
  const { id, pid } = req.params;
  let video;
  try {
    video = await Video.findOneAndUpdate(
      { _id: pid, uploader: id },
      { $inc: { views: 1 } },
      { new: true }
    );
    if (video) {
      return res.status(200).send(video);
    }
  } catch (err) {
    console.log(err.message);
  }
  return res.status(404).send("No video found");
}
async function deleteVideo(req, res) {}

async function updateVideo(req, res) {
  const { id, pid } = req.params;
  let updateObj = {};
  if (req.body.name) {
    updateObj.name = req.body.name;
  }
  if (req.body.description) {
    updateObj.description = req.body.description;
  }
  if (req.body.tags) {
    updateObj.tags = req.body.tags;
  }
  try {
    const video = await Video.findOneAndUpdate({ _id: pid, uploader: id }, { $set: updateObj });
    if (video) {
      return res.status(201).send("OK");
    }
    return res.status(404).send("Video not found");
  } catch (err) {
    console.log(err.message);
  }
  return res.status(400).send("Invalid");
}
async function addVideo(req, res) {
  const { id } = req.params;
  const { name, description, tags, src } = req.body;
  if (!name || !src) {
    return res.status(400).send("Name and video are required");
  }
  try {
    let fileName = write64FileWithCopies(name, src);
    const video = new Video({ name, uploader: id, description, tags, src: fileName });
    await video.save();
    await User.findByIdAndUpdate(id, { $push: { videos: video.id } });
    return res.status(201).send("OK");
  } catch (err) {
    console.log(err.message);
  }
  return res.status(400).send("Couldn't upload video");
}
async function likeVideo(req, res) {
  const { id, pid } = req.params;
  try {
    const video = await Video.findOneAndUpdate(
      { _id: pid, uploader: id },
      { $addToSet: { likes: id } }
    );
    if (video) {
      return res.status(201).send("OK");
    } else {
      return res.status(404).send("Video not found");
    }
  } catch (err) {
    console.log(err.message);
  }
  return res.status(400).send("Couldn't like video");
}
async function dislikeVideo(req, res) {
  const { id, pid } = req.params;
  try {
    const video = await Video.findOneAndUpdate(
      { _id: pid, uploader: id },
      { $pull: { likes: id } }
    );
    if (video) {
      return res.status(201).send("OK");
    } else {
      return res.status(404).send("Video not found");
    }
  } catch (err) {
    console.log(err.message);
  }
  return res.status(400).send("Couldn't remove like from video");
}

// my viedos
async function getVideosByUserId(req, res) {
  const { id } = req.params;
  try {
    const user = await User.findById(id).populate('videos');
    if (!user) {
      return res.status(404).send("User not found");
    }
    return res.status(200).send(user.videos);
  } catch (err) {
    console.log(err.message);
    return res.status(500).send(" Erroe displaying user's videos");
  }
}

module.exports = {
  getVideos,
  getVideo,
  deleteVideo,
  updateVideo,
  addVideo,
  likeVideo,
  dislikeVideo,
  getVideosByUserId,
};
