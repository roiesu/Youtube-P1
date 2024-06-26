const Video = require("../models/video");
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

module.exports = { getVideos };
