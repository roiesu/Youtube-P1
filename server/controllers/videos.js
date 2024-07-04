const User = require("../models/user");
const Video = require("../models/video");
const { write64FileWithCopies } = require("../utils");

async function getVideos(req, res) {
  const name = req.query.name || "";
  try {
    const filterValues = { name: { $regex: name, $options: "i" } };
    const topVideos = await Video.find(filterValues)
      .select(["_id", "name", "uploader", "views", "src", "date"])
      .sort({ views: "desc" })
      .limit(10)
      .populate("uploader", ["name", "image", "username", "-_id"]);
    let restVideos = [];
    if (topVideos.length > 0) {
      let viewsBar = topVideos[topVideos.length - 1].views;
      restVideos = await Video.find({ ...filterValues, views: { $lt: viewsBar } })
        .limit(10)
        .populate("uploader", ["name", "image", "username", "-_id"]);
    }
    return res.status(200).send({ topVideos, restVideos });
  } catch (err) {
    return res.status(400).send("Couldn't get videos");
  }
}
async function getMinimalVideoDetails(req, res) {
  const { id, pid } = req.params;

  try {
    const video = await Video.findById(pid)
      .select(["name", "description", "tags"])
      .populate("uploader", ["username", "_-id"]);
    if (!video || video.uploader.username != id) {
      return res.status(404).send("Video not found");
    }
    return res.status(200).send(video);
  } catch (err) {
    return res.status(400).send(err.message);
  }
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
      populate: { path: "user", select: ["-password", "-_id"] },
    });

    let likedVideo = false;
    if (req.user && video.likes.find((likedUser) => likedUser == req.user)) {
      likedVideo = true;
    }
    return res.status(200).send({ ...video.toJSON(), likes: video.likes.length, likedVideo });
  } catch (err) {}
  return res.sendStatus(404);
}

async function deleteVideo(req, res) {
  const { id, pid } = req.params;
  try {
    const video = await Video.findById(pid)
      .populate("uploader", ["username"])
      .populate("likes", ["_id"])
      .populate({
        path: "comments",
        select: ["_id", "user", "video"],
        populate: [
          { path: "user", select: ["_id"] },
          { path: "video", select: ["_id"] },
        ],
      });
    if (!video || video.uploader.username != id) {
      return res.sendStatus(404);
    } else if (video.uploader._id != req.user) {
      return res.sendStatus(401);
    }
    await video.deleteOne();
    return res.status(200).send("Video deleted");
  } catch (err) {
    return res.status(400).send("Couldn't delete video");
  }
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
    const video = await Video.findById(pid).populate("uploader", ["username"]);
    if (video.uploader._id != req.user) {
      return res.sendStatus(401);
    } else if (video && video.uploader.username === id) {
      await video.updateOne(updateObj);
      return res.sendStatus(201);
    }
    return res.sendStatus(404);
  } catch (err) {}
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
    } else if (user._id != req.user) {
      return res.status(401).send("Invalid user");
    }
    let fileName = write64FileWithCopies(name, src);
    const video = new Video({ name, uploader: user._id, description, tags, src: fileName });
    await video.save();
    user.videos.push(video._id);
    await user.save();
    return res.sendStatus(201);
  } catch (err) {
    return res.status(400).send("unexpected error acurred");
  }
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
  } catch (err) {}
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
  } catch (err) {}
  return res.status(400).send("Couldn't remove like from video");
}

// my videos
async function getVideosDetailsByUserId(req, res) {
  const { id } = req.params;
  try {
    const users = await User.aggregate([
      { $match: { username: id } },
      { $project: { _id: 0, password: 0 } },
      {
        $lookup: {
          from: "videos",
          localField: "videos",
          foreignField: "_id",
          as: "videos",
          pipeline: [
            {
              $project: {
                name: 1,
                uploader: 1,
                views: 1,
                src: 1,
                date: 1,
                likesCount: { $size: "$likes" },
                commentsCount: { $size: "$comments" },
              },
            },
            {
              $lookup: {
                from: "users",
                localField: "uploader",
                foreignField: "_id",
                as: "uploader",
                pipeline: [
                  {
                    $project: {
                      username: 1,
                      name: 1,
                      image: 1,
                      _id: 0,
                    },
                  },
                ],
              },
            },
            { $unwind: "$uploader" },
          ],
        },
      },
    ]);
    if (users.length === 0) {
      return res.sendStatus(404);
    }
    return res.status(200).send(users[0].videos);
  } catch (err) {
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
  getVideosDetailsByUserId,
  getMinimalVideoDetails,
};
