const express = require("express");
const bodyParser = require("body-parser");
const router = require("./routes/router");

const app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use("/api", router);

app.listen(8080);
