const User = require("../models/user");
const jwt = require("jsonwebtoken");
async function autoLogin(req, res) {
  const id = req.user;
  try {
    const user = await User.findById(id).select("username");
    if (user) {
      return res.status(200).send(user.username);
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
module.exports = { autoLogin, loginUser };
