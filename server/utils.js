const { errorMonitor } = require("events");
const fs = require("fs");

function write64FileWithCopies(dest, data) {
  let fileName = dest;
  try {
    let [header, base64File] = data.split(/;base64,/);
    let [fileType, fileSuffix] = header.replace(/data:/, "").split("/");
    let file = fs.existsSync(`./public/${fileType}/${fileName}.${fileSuffix}`);
    if (file) {
      let i = 0;
      let startName = fileName;
      while (file) {
        file = fs.existsSync(`./public/${fileType}/${startName} (${++i}).${fileSuffix}`);
      }
      fileName = `${startName} (${i})`;
    }
    fs.writeFileSync(`./public/${fileType}/${fileName}.${fileSuffix}`, base64File, {
      encoding: "base64",
    });
    return fileName + "." + fileSuffix;
  } catch (err) {
    return null;
  }
}
function override64File(fileType, name, data) {
  try {
    let base64File = data.split(/;base64,/)[1];
    fs.writeFileSync(`./public/${fileType}/${name}`, base64File, { encoding: "base64" });
  } catch (err) {
    return null;
  }
}
function deletePublicFile(type, name) {
  fs.unlinkSync(`./public/${type}/${name}`);
}

module.exports = { write64FileWithCopies, deletePublicFile, override64File };
