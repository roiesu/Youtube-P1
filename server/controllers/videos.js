const User = require("../models/user");
const Video = require("../models/video");
const { write64FileWithCopies, deletePublicFile, override64File } = require("../utils");

async function videoIndex(req, res) {
  try {
    const videos = await Video.aggregate([
      {
        $project: {
          likes: "$likes",
          video: {
            _id: "$_id",
            name: "$name",
            uploaderId: "$uploader",
            src: "$src",
            likesNum: { $size: "$likes" },
            thumbnail: "$thumbnail",
            duration: "$duration",
            views: "$views",
            date: "$date",
            description: "$description",
            tags: "$tags",
          },
        },
      },
    ]);
    return res.send(videos);
  } catch (err) {
    return res.status(400).send(err.message);
  }
}

async function getVideos(req, res) {
  const name = req.query.name || "";
  try {
    const filterValues = { name: { $regex: name, $options: "i" } };
    const topVideos = await Video.find(filterValues)
      .select(["_id", "name", "uploader", "views", "thumbnail", "date", "duration"])
      .sort({ views: "desc" })
      .limit(10)
      .populate("uploader", ["name", "username", "image"]);

    let restVideos = [];
    if (topVideos.length > 0) {
      let viewsBar = topVideos[topVideos.length - 1].views;
      restVideos = await Video.aggregate([
        { $match: { ...filterValues, views: { $lt: viewsBar } } },
        { $sample: { size: 10 } },
        {
          $lookup: {
            from: "users",
            localField: "uploader",
            foreignField: "_id",
            as: "uploader",
          },
        },
        { $unwind: "$uploader" },
        {
          $project: {
            name: 1,
            views: 1,
            thumbnail: 1,
            date: 1,
            duration: 1,
            "uploader.name": 1,
            "uploader.image": 1,
            "uploader.username": 1,
          },
        },
      ]);
    }
    return res.status(200).send(topVideos.concat(restVideos));
  } catch (err) {
    return res.status(400).send(err.message);
  }
}
async function getMinimalVideoDetails(req, res) {
  const { id, pid } = req.params;
  try {
    const video = await Video.findById(pid)
      .select(["name", "description", "tags", "thumbnail", "src"])
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
      select: ["-video"],
      populate: { path: "user", select: ["_id", "name", "username", "image"] },
    });

    let likedVideo = false;
    if (req.user && video.likes.find((likedUser) => likedUser == req.user)) {
      likedVideo = true;
    }
    return res.status(200).send({ ...video.toJSON(), likes: video.likes.length, likedVideo });
  } catch (err) {
    if (err.kind == "ObjectId") {
      return res.sendStatus(404);
    } else {
      return res.status(400).send(err.message);
    }
  }
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
    return res.sendStatus(200);
  } catch (err) {
    if (err.kind == "ObjectId") {
      return res.sendStatus(404);
    } else {
      return res.status(400).send(err.message);
    }
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
      if (req.body.thumbnail) {
        override64File("image", video.thumbnail, req.body.thumbnail);
      }
      await video.updateOne(updateObj);
      return res.sendStatus(201);
    }
    return res.sendStatus(404);
  } catch (err) {
    if (err.kind == "ObjectId") {
      return res.sendStatus(404);
    } else {
      return res.status(400).send(err.message);
    }
  }
}

async function addVideo(req, res) {
  const { id } = req.params;
  const { name, description, tags, src, thumbnail, duration } = req.body;
  if (!name || !src || !description || !thumbnail || !duration) {
    return res.status(400).send("Name, description, video, thumbnail and duration are required");
  }
  let videoFile, imageFile;
  try {
    const user = await User.findOne({ username: id });
    if (!user) {
      return res.status(404).send("User not found");
    } else if (user._id != req.user) {
      return res.status(401).send("Invalid user");
    }
    videoFile = write64FileWithCopies(name, src);
    imageFile = write64FileWithCopies(name + " thumbnail", thumbnail);
    if (!videoFile) {
      return res.status(400).send("Invalid video file");
    } else if (!imageFile) {
      res.status(400).send("Invalid image");
    }
    const video = new Video({
      name,
      uploader: user._id,
      description,
      tags,
      src: videoFile,
      thumbnail: imageFile,
      duration,
    });
    await video.save();
    user.videos.push(video._id);
    await user.save();
    return res.sendStatus(201);
  } catch (err) {
    if (videoFile) {
      deletePublicFile("video", videoFile);
    }
    if (imageFile) {
      deletePublicFile("image", imageFile);
    }
    if (err.kind == "ObjectId") {
      return res.sendStatus(404);
    } else {
      return res.status(400).send(err.message);
    }
  }
}

async function likeVideo(req, res) {
  const { id, pid } = req.params;
  try {
    const video = await Video.findById(pid).populate("uploader", ["username"]);
    if (video && video.uploader.username === id) {
      await User.findByIdAndUpdate(req.user, { $addToSet: { likes: pid } });
      video.likes.addToSet(req.user);
      await video.save();
      console.log("Like", req.user);
      return res.status(201).send(req.user);
    } else {
      return res.sendStatus(404);
    }
  } catch (err) {
    if (err.kind == "ObjectId") {
      return res.sendStatus(404);
    } else {
      return res.status(400).send(err.message);
    }
  }
}

async function dislikeVideo(req, res) {
  const { id, pid } = req.params;
  try {
    const video = await Video.findById(pid).populate("uploader", ["username"]);
    if (video && video.uploader.username === id) {
      await User.findByIdAndUpdate(req.user, { $pull: { likes: pid } });
      video.likes.pull(req.user);
      await video.save();
      console.log("dislike", req.user);
      return res.status(201).send(req.user);
    } else {
      return res.sendStatus(404);
    }
  } catch (err) {
    if (err.kind == "ObjectId") {
      return res.sendStatus(404);
    } else {
      return res.status(400).send(err.message);
    }
  }
}

// my videos
async function getVideosDetailsByUserId(req, res) {
  const { id } = req.params;
  const user = await User.findOne({ username: id }).select("_id");
  if (!user) {
    return res.sendStatus(404);
  } else if (user._id != req.user) {
    return res.sendStatus(401);
  }
  try {
    const users = await User.aggregate([
      { $match: { username: id } },
      { $project: { _id: 1, videos: 1 } },
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
                views: 1,
                thumbnail: 1,
                date: 1,
                duration: 1,
                likesNum: { $size: "$likes" },
                commentsNum: { $size: "$comments" },
              },
            },
            { $addFields: { uploader: id } },
          ],
        },
      },
    ]);
    if (users.length === 0) {
      return res.sendStatus(404);
    }
    return res.status(200).send(users[0].videos);
  } catch (err) {
    return res.status(400).send(err.message);
  }
}

async function getVideosByUserId(req, res) {
  const { id } = req.params;
  try {
    const user = await User.findOne({ username: id })
      .select(["_id", "videos"])
      .populate({
        path: "videos",
        select: ["name", "views", "date", "thumbnail", "uploader", "duration"],
        populate: { path: "uploader", select: ["username", "name", "image"] },
        options: {
          sort: { views: "desc" },
        },
      });
    if (!user) {
      return res.sendStatus(404);
    }
    return res.status(200).send(user.videos);
  } catch (err) {
    return res.status(500).send(err.message);
  }
}

async function increaseViews(req, res) {
  const { pid, id } = req.params;
  const video = await Video.findOneAndUpdate(
    { _id: pid, uploader: id },
    { $inc: { views: 1 } },
    { new: true }
  ).select(["views"]);
  if (!video) {
    return res.status(404).send("Video not found");
  }
  return res.status(200).send(video);
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
  getVideosByUserId,
  videoIndex,
  increaseViews,
};
