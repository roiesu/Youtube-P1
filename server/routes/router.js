const express = require('express');
const router = express.Router();
const videos = require('./videos');
const users = require('./users'); 
const comments = require('./comments');
const authenticateToken = require('../middleware/auth'); 

router.use('/videos', authenticateToken, videos);
router.use('/users', authenticateToken, users);
router.use('/comments', authenticateToken, comments);

router.get('/', (req, res) => {
  res.send('router');
});

module.exports = router;
