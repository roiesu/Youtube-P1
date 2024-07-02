const User = require("../models/user");
const Video = require("../models/video");
const Comment = require("../models/comment");
const { write64FileWithCopies } = require("../utils");

async function getVideos(req, res) {
  const name = req.query.name || "";
  try {
    const filterValues = { name: { $regex: name, $options: "i" } };
    let videos = await Video.find(filterValues)
      .select(["_id", "name", "uploader", "views", "src", "date"])
      .sort({ views: "desc" })
      // .limit(10)
      .populate("uploader", ["name", "image", "username", "-_id"]);
    return res.status(200).send(videos);
  } catch (err) {
    console.log(err);
  }
  return res.status(200).send([]);
}

async function getVideo(req, res) {
  const { id, pid } = req.params;
  try {
    const video = await Video.findById(pid).populate("uploader", [
      "name",
      "username",
      "image",
      "-_id",
    ]);

    if (!video || video.uploader.username !== id) {
      return res.sendStatus(404);
    }
    video.views++;
    await video.save();
    await video.populate({
      path: "comments",
      select: { video: false },
      populate: { path: "user", select: ["-password", "-_id"] },
    });
    let likedVideo = false;
    if (req.user && video.likes.find((likedUser) => likedUser == req.user)) {
      likedVideo = true;
    }
    return res.status(200).send({ ...video.toJSON(), likes: video.likes.length, likedVideo });
  } catch (err) {
    console.log(err.message);
  }
  return res.sendStatus(404);
}

async function deleteVideo(req, res) {
  const { id, pid } = req.params;
  try {
    const user = await User.findOne({ username: id }).populate({
      path: "videos",
      match: { _id: pid },
    });
    if (!user || user.videos.length !== 1) {
      return res.sendStatus(404);
    }
    const video = user.videos[0];
    // // Removing likes
    // await Promise.all(
    //   video.likes.map(async (userId) => {
    //     try {
    //       await User.findByIdAndUpdate(userId, { $pull: { likes: pid } });
    //     } catch (err) {
    //       console.log(err.message);
    //     }
    //   })
    // );

    // // Removing comments
    // await Promise.all(
    //   video.comments.map(async (commentId) => {
    //     try {
    //       const comment = await Comment.findByIdAndDelete(commentId);
    //       await User.findByIdAndUpdate(comment.user, { $pull: { comments: commentId } });
    //     } catch (err) {
    //       console.log(err.message);
    //     }
    //   })
    // );

    // // Deleting video file
    // deletePublicFile("video", video.src);

    // // Remove from user videos
    await video.deleteOne();
    // await user.updateOne({ $pull: { videos: video._id } });
    return res.status(200).send("Video deleted");
  } catch (err) {
    console.log(err);
  }
  return res.status(400).send("Couldn't delete video");
}

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
    const video = await Video.findById(pid).populate("uploader", ["username", "-_id"]);
    if (video && video.uploader.username === id) {
      await video.updateOne(updateObj);
      return res.sendStatus(201);
    }
    return res.sendStatus(404);
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
    const user = await User.findOne({ username: id });
    if (!user) {
      return res.status(404).send("User not found");
    }
    let fileName = write64FileWithCopies(name, src);
    const video = new Video({ name, uploader: user._id, description, tags, src: fileName });
    video.save();
    user.videos.push(video._id);
    user.save();
    return res.status(201).send("OK");
  } catch (err) {
    console.log(err.message);
  }
  return res.status(400).send("Couldn't upload video");
}

async function likeVideo(req, res) {
  const { id, pid } = req.params;
  try {
    const video = await Video.findById(pid).populate("uploader", ["username", "-_id"]);
    if (video && video.uploader.username === id) {
      await User.findByIdAndUpdate(req.user, { $addToSet: { likes: pid } });
      video.likes.addToSet(req.user);
      await video.save();
      return res.sendStatus(201);
    } else {
      return res.sendStatus(404);
    }
  } catch (err) {
    console.log(err.message);
  }
  return res.status(400).send("Couldn't like video");
}

async function dislikeVideo(req, res) {
  const { id, pid } = req.params;
  try {
    const video = await Video.findById(pid).populate("uploader", ["username", "-_id"]);
    if (video && video.uploader.username === id) {
      await User.findByIdAndUpdate(req.user, { $pull: { likes: pid } });
      video.likes.pull(req.user);
      await video.save();
      return res.sendStatus(201);
    } else {
      return res.sendStatus(404);
    }
  } catch (err) {
    console.log(err.message);
  }
  return res.status(400).send("Couldn't remove like from video");
}

// my videos
async function getVideosByUserId(req, res) {
  const { id } = req.params;
  try {
    const user = await User.findOne({ username: id })
      .select(["-_id", "-_password"])
      .populate({ path: "videos", fields: ["name", "views", "date", "src"] });
    if (!user) {
      return res.sendStatus(404);
    }
    return res.status(200).send(user.videos);
  } catch (err) {
    console.log(err.message);
    return res.status(500).send(" Error displaying user's videos");
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
