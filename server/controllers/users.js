const express = require("express");
const mongoose = require("mongoose");
const User = require("../models/user");
const jwt = require("jsonwebtoken");
const { write64FileWithCopies } = require("../utils");
const router = express.Router();
const JWT_SECRET = process.env.JWT_SECRET;

//new user and JWT token
async function addUser(req, res) {
  const { username, password, name, image } = req.body;
  if (!username || !password || !name || !image) {
    return res.status(400).send("Username, password, name, and image are required!");
  }
  try {
    const found = await User.findOne({ username });
    if (found) {
      return res.sendStatus(409);
    }
    const imagePath = write64FileWithCopies(username + "-profile-pic", image);
    if (!imagePath) {
      return res.status(400).send("Image is not URI");
    }
    const user = new User({ username, password, name, image: imagePath });
    // user -> database
    await user.save();
    return res.sendStatus(200);
  } catch (err) {
    console.log(err.message);
    return res.sendStatus(400);
  }
}

async function loginUser(req, res) {
  const { username, password } = req.body;
  if (!username) {
    return res.status(400).send("Username is required!");
  }
  if (!password) {
    return res.status(400).send("password is required!");
  }
  try {
    const user = await User.findOne({ username });
    if (!user) {
      return res.status(404).send("Invalid username!");
    }
    if (user.password !== password) {
      return res.status(404).send("Invalid password!");
    }

    const token = jwt.sign({ id: user._id }, JWT_SECRET, { expiresIn: "1h" });

    return res.status(200).send({ message: "Login successful!", token });
  } catch (err) {
    console.log(err.message);
    return res.status(500).send("Couldn't log in this user!");
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
    console.log(err.message);
    return res.status(500).send("Error fetching user details");
  }
}

async function updateUser(req, res) {
  const { id } = req.params;
  const { name, password, image } = req.body;

  const updateFields = {};
  if (name) updateFields.name = name;
  if (password) updateFields.password = password;
  if (image) updateFields.image = image;

  try {
    const user = await User.findOneAndUpdate({ username: id }, updateFields, { new: true }).select(
      "-password"
    );
    if (!user) {
      return res.status(404).send("User not found");
    }
    return res.status(200).send({ message: `User ${id} updated!`, user });
  } catch (err) {
    console.log(err.message);
    return res.status(500).send("Error updating user details");
  }
}

async function deleteUser(req, res) {
  const { id } = req.params;
  try {
    const user = await User.findOneAndDelete({ username: id });
    if (!user) {
      return res.status(404).send("User not found");
    }
    return res.status(200).send({ message: `User ${id} deleted!` });
  } catch (err) {
    console.log(err.message);
    return res.status(500).send("Error deleting user");
  }
}

module.exports = {
  addUser,
  loginUser,
  getUser,
  updateUser,
  deleteUser,
};
