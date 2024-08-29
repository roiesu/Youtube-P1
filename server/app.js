const express = require("express");
const bodyParser = require("body-parser");
const router = require("./routes/router");
const mongoose = require("mongoose");
const cors = require("cors");
const net = require("node:net");

require("dotenv").config();
const app = express();
const path = require("path");
app.use(cors());
app.use(express.json({ limit: "500mb" }));
app.use(bodyParser.urlencoded({ extended: true, limit: "500mb" }));
app.use("/media", express.static("./public"));
app.use("/api", router);
app.use("/", express.static("./build"));
app.get("*", (req, res) => {
  res.sendFile(path.join(__dirname + "/build/index.html"));
});

app.listen(8080, () => {
  // TEST
  const client = new net.Socket();
  client.connect(5555, "192.168.64.129", () => {
    console.log("Connected to the server");
    // Send a message to the server
    client.write("Hello, server! I am the client.");
  });

  client.on("data", (data) => {
    console.log(data.toString());
  });
  client.on("close", () => {
    console.log("bye");
  });
  // TEST

  mongoose
    .connect(process.env.MONGODB_CONNECTION_URL, {
      dbName: process.env.MONGODB_DATABASE,
    })
    .then(() => {
      console.log("connected to database");
    })
    .catch((err) => console.error("Database connection error:", err));
});
