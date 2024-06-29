const User = require("../models/user");
const Video = require("../models/video");
const Comment = require("../models/comment");
const { write64FileWithCopies, deletePublicFile } = require("../utils");

async function getVideos(req, res) {
  const name = req.query.name || "";
  try {
    const filterValues = { name: { $regex: name, $options: "i" } };
    let videos = await Video.find(filterValues)
      .select(["_id", "name", "uploader", "views", "src", "date"])
      .sort({ views: "desc" })
      .limit(10)
      .populate("uploader", ["name", "image"]);
    return res.status(200).send(videos);
  } catch (err) {
    console.log(err);
  }
  return res.status(200).send([]);
}

async function getVideo(req, res) {
  const { id, pid } = req.params;
  let video;
  try {
    video = await Video.findOneAndUpdate(
      { _id: pid, uploader: id },
      { $inc: { views: 1 } },
      { new: true }
    )
      .populate("uploader", ["name", "image"])
      .populate({
        path: "comments",
        select: { video: false },
        populate: { path: "user", select: ["name", "image"] },
      });

    if (video) {
      return res.status(200).send(video);
    }
  } catch (err) {
    console.log(err.message);
  }
  return res.status(404).send("No video found");
}

async function deleteVideo(req, res) {
  const { id, pid } = req.params;
  try {
    const video = await Video.findOneAndDelete({ _id: pid, uploader: id });
    if (video) {
      // Removing likes
      await Promise.all(
        video.likes.map(async (userId) => {
          try {
            await User.findByIdAndUpdate(userId, { $pull: { likes: pid } });
          } catch (err) {
            console.log(err.message);
          }
        })
      );

      // Removing comments
      await Promise.all(
        video.comments.forEach(async (commentId) => {
          try {
            const comment = await Comment.findByIdAndDelete(commentId);
            await User.findByIdAndUpdate(comment.user, { $pull: { comments: commentId } });
          } catch (err) {
            console.log(err.message);
          }
        })
      );
      // Remove from user videos
      try {
        await User.findByIdAndUpdate(id, { $pull: { videos: pid } });
      } catch (err) {
        console.log(err.message);
      }
      // Deleting video file
      deletePublicFile("video", video.src);
      return res.status(200).send("Video deleted");
    }
    return res.status(404).send("Video not found");
  } catch (err) {
    console.log(err.message);
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
    const user = await User.findById(id);
    if (!user) {
      return res.status(404).send("User not found");
    }
    let fileName = write64FileWithCopies(name, src);
    const video = new Video({ name, uploader: id, description, tags, src: fileName });
    await video.save();
    user.videos.push(video);
    await user.save();
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

module.exports = {
  getVideos,
  getVideo,
  deleteVideo,
  updateVideo,
  addVideo,
  likeVideo,
  dislikeVideo,
};
