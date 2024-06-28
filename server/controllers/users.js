const express = require('express');
const mongoose = require('mongoose');
const User = require('../models/user');
const jwt = require('jsonwebtoken');
const router = express.Router();

const JWT_SECRET = 'my_jwt_secret';  

async function addUser(req, res) {
  const { username, password, name, image } = req.body;
  if (!username || !password || !name || !image) {
    return res.status(400).send("Username, password, name, and image are required!");
  }
  try {
    const user = new User({ username, password, name, image });
    await user.save();

    // jwt
    const token = jwt.sign({ id: user._id }, JWT_SECRET, { expiresIn: '1h' });

    return res.status(201).send({ message: `User ${name} created!`, token });
  } catch (err) {
    console.log(err.message);
    return res.status(500).send("couldn't create this user!");
  }
}

router.post('/new', addUser);

module.exports = router;
