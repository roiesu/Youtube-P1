const { deletePublicFile } = require("../utils");

async function preDeleteComment(next, document) {
  try {
    if (document.video) {
      await document.video.updateOne({ $pull: { comments: document._id } });
      await document.video.save();
    }

    if (document.user) {
      await document.user.updateOne({ $pull: { comments: document._id } });
      await document.user.save();
    }
    next();
  } catch (err) {
    next(err);
  }
}

async function preDeleteVideo(next, document) {
  try {
    // Remove likes
    for (user of document.likes) {
      await user.updateOne({ $pull: { likes: document._id } });
      await user.save;
    }

    for (comment of document.comments) {
      await comment.deleteOne();
    }
    await document.uploader.updateOne({ $pull: { videos: document._id } });
    await document.uploader.save();
    // Remove the file
    deletePublicFile("video", document.src);
    next();
  } catch (err) {
    next(err);
  }
}

module.exports = { preDeleteVideo, preDeleteComment };
