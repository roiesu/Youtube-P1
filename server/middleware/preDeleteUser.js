const Video = require("../models/video");
const { deletePublicFile } = require("../utils");
async function preDeleteUser(next, document) {
  try {
    // remove likes
    for (video of document.likes) {
      await video.updateOne({ $pull: { likes: document._id } });
      await video.save();
    }
    console.log("comments");
    for (comment of document.comments) {
      await comment.deleteOne();
    }
    console.log("videos");
    for (videoId of document.videos) {
      const video = await Video.findById(videoId).populate("uploader", ["videos"]);
      if (video) {
        await video.deleteOne();
      }
    }
    deletePublicFile("image", document.image);
    next();
  } catch (err) {
    next(err);
  }
}
module.exports = preDeleteUser;
