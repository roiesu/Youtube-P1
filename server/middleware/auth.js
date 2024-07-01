const jwt = require('jsonwebtoken');
require('dotenv').config();

const JWT_SECRET = process.env.JWT_SECRET; 

function authenticateToken(req, res, next) {
  const token = req.header('Authorization')?.replace('Bearer ', '');
  if (!token) {
    return res.status(401).send('Access Denied');
  }

  try {
    const verified = jwt.verify(token, JWT_SECRET);
    req.user = verified; 
    next(); 
  } catch (err) {
    res.status(400).send('Invalid Token');
  }
}

module.exports = authenticateToken;
