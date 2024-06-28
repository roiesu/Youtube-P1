const express = require('express');
const mongoose = require('mongoose');
const User = require('../models/user');
const jwt = require('jsonwebtoken');
const router = express.Router();
const JWT_SECRET = 'my_jwt_secret'; 

//new user and JWT token
async function addUser(req, res) {
  const { username, password, name, image } = req.body;
  if (!username || !password || !name || !image) {
    return res.status(400).send("Username, password, name, and image are required!");
  }
  try {
    const user = new User({ username, password, name, image });
     // user -> database
    await user.save();

    // jwt token for the new user
    const token = jwt.sign({ id: user._id }, JWT_SECRET, { expiresIn: '1h' });

    return res.status(201).send({ message: `User ${name} created!`, token });
  } catch (err) {
    console.log(err.message); 
    return res.status(500).send("Couldn't create this user!"); 
}

async function loginUser(req, res) {
  const { username, password } = req.body;
  if (!username || !password) {
    return res.status(400).send("Username and password are required!");
  }
  try {
    const user = await User.findOne({ username });
    if (!user) {
      return res.status(400).send("Invalid username or password!");
    }
    if (user.password !== password) {
      return res.status(400).send("Invalid username or password!");
    }

    const token = jwt.sign({ id: user._id }, JWT_SECRET, { expiresIn: '1h' });

    return res.status(200).send({ message: "Login successful!", token });
  } catch (err) {
    console.log(err.message); 
    return res.status(500).send("Couldn't log in this user!"); 
  }

}

router.post('/new', addUser);

router.post('/tokens', loginUser);

module.exports = router;

};