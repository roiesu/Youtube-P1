const express = require("express");
const bodyParser = require("body-parser");
const router = require("./routes/router");
const mongoose = require("mongoose");
const app = express();

app.use(express.json({ limit: "500mb" }));
app.use(bodyParser.urlencoded({ extended: true, limit: "500mb" }));
app.use("/media", express.static("./public"));
app.use("/api", router);

app.listen(8080, () => {
  mongoose
    .connect("mongodb+srv://orenb99:org05101971@bishbash.3blxfdz.mongodb.net/", {
      dbName: "BishBash",
    })
    .then(() => {
      console.log("connected");
    })
    .catch((err) => console.error("Database connection error:", err));
});
