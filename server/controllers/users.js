const User = require("../models/user");
const jwt = require("jsonwebtoken");
const { write64FileWithCopies, override64File, deletePublicFile } = require("../utils");

async function addUser(req, res) {
  const { username, password, name, image } = req.body;
  if (!username || !password || !name || !image) {
    return res.status(400).send("All fields are required");
  }
  let imagePath;
  try {
    const found = await User.findOne({ username });
    if (found) {
      return res.status(409).send("Username already exists");
    }
    imagePath = write64FileWithCopies(username + "-profile-pic", image);
    if (!imagePath) {
      return res.status(400).send("Invalid image");
    }

    const user = new User({ username, password, name, image: imagePath });

    await user.save();
    return res.sendStatus(200);
  } catch (err) {
    if (imagePath) {
      deletePublicFile("image", imagePath);
    }
    if (err.message.match(/.*User validation.*/)) {
      return res
        .status(400)
        .send(err.message.replace(/\., /g, ".\n").replace(/^User validation failed: /, ""));
    }
    return res.status(400).send(err.message);
  }
}

async function loginUser(req, res) {
  const { username, password } = req.body;
  if (!username) {
    return res.status(400).send("Username is required");
  }
  if (!password) {
    return res.status(400).send("password is required");
  }
  try {
    const user = await User.findOne({ username });
    if (!user) {
      return res.status(404).send("User not found");
    }
    if (user.password !== password) {
      return res.status(404).send("Wrong password");
    }
    const token = jwt.sign({ id: user._id }, process.env.JWT_SECRET, { expiresIn: "1h" });
    return res.status(200).send(token);
  } catch (err) {
    return res.status(err.status || 400).send(err.message);
  }
}

async function getUser(req, res) {
  const { id } = req.params;
  try {
    const user = await User.findOne({ username: id }).select("-password");
    if (!user) {
      return res.status(404).send("User not found");
    }
    return res.status(200).send(user);
  } catch (err) {
    return res.status(500).send(err.message);
  }
}

// For user details editing and removing
async function getFullUserDetails(req, res) {
  const { id } = req.params;
  try {
    const user = await User.findOne({ username: id }).select(["-comments", "-likes", "-videos"]);
    if (!user) {
      return res.status(404).send("User not found");
    } else if (user._id != req.user) {
      return res.status(401).send("Unauthorized");
    }
    return res.status(200).send(user);
  } catch (err) {
    return res.status(err.status).send(err.message);
  }
}

async function updateUser(req, res) {
  const { id } = req.params;
  const { name, password, image } = req.body;

  const updateFields = {};
  if (name) updateFields.name = name;
  if (password) updateFields.password = password;

  try {
    const user = await User.findOne({ username: id });
    if (!user) {
      return res.status(404).send("User not found");
    } else if (user._id != req.user) {
      return res.sendStatus(401);
    }
    if (image) {
      override64File("image", user.image, image);
    }
    await user.updateOne(updateFields, { runValidators: true });
    await user.save();
    return res.sendStatus(200);
  } catch (err) {
    return res.status(400).send(err.message);
  }
}

async function deleteUser(req, res) {
  const { id } = req.params;
  try {
    const user = await User.findOne({ username: id });
    if (!user) {
      return res.status(404).send("User not found");
    } else if (user._id != req.user) {
      return res.sendStatus(401);
    }
    await user.populate({
      path: "comments",
      select: ["_id", "user", "video"],
      populate: [
        { path: "user", select: ["_id"] },
        { path: "video", select: ["_id"] },
      ],
    });
    await user.populate({ path: "likes", select: ["_id"] });
    await user.deleteOne();
    return res.status(200).send(`User ${id} deleted`);
  } catch (err) {
    return res.status(400).send(err.message);
  }
}

// channel page
async function getVideosByUserId(req, res) {
  const { id } = req.params;
  try {
    const user = await User.findOne({ username: id })
      .select("-password")
      .populate({
        path: "videos",
        select: ["name", "views", "date", "thumbnail", "uploader"],
        populate: { path: "uploader", select: ["username", "name", "image"] },
      });
    if (!user) {
      return res.sendStatus(404);
    }
    return res.status(200).send(user.videos);
  } catch (err) {
    return res.status(500).send(err.message);
  }
}

module.exports = {
  addUser,
  loginUser,
  getUser,
  updateUser,
  deleteUser,
  getVideosByUserId,
  getFullUserDetails,
};
