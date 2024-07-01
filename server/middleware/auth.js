const jwt = require("jsonwebtoken");

function authenticateTokenIfGot(req, res, next) {
  const token = req.header("Authorization")?.replace("Bearer ", "");
  if (token) {
    try {
      const verified = jwt.verify(token, process.env.JWT_SECRET);
      console.log(verified);
      req.user = verified.id;
    } catch (err) {
      return res.status(400).send("Invalid Token");
    }
  }
  next();
}

function authenticateToken(req, res, next) {
  const token = req.header("Authorization")?.replace("Bearer ", "");
  if (!token) {
    return res.status(401).send("Access Denied");
  }
  try {
    const verified = jwt.verify(token, process.env.JWT_SECRET);
    req.user = verified.id;
    next();
  } catch (err) {
    return res.status(400).send("Invalid Token");
  }
}

module.exports = { authenticateToken, authenticateTokenIfGot };
