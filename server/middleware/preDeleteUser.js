const Video = require("../models/video");
async function preDeleteUser(next, document) {
  try {
    // remove likes
    for (video of document.likes) {
      await video.updateOne({ $pull: { likes: document._id } });
      await video.save();
    }

    for (comment of document.comments) {
      await comment.deleteOne();
      await comment.save();
    }

    for (videoId of document.videos) {
      await Video.findById(videoId).deleteOne();
    }
    deletePublicFile("image", document.src);
    next();
  } catch (err) {
    next(err);
  }
}
module.exports = preDeleteUser;
