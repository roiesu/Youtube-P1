const express = require("express");
const { autoLogin, loginUser } = require("../controllers/tokens");
const { authenticateToken } = require("../middleware/auth");
const router = express.Router();

router.get("/", authenticateToken, autoLogin);
router.post("/", loginUser);
module.exports = router;
