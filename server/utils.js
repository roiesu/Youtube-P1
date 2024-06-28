const fs = require("fs");

function write64FileWithCopies(dest, data) {
  let fileName = dest;
  let file = fs.existsSync(dest);
  if (file) {
    let i = 0;
    let startName = fileName;
    while (file) {
      file = fs.existsSync(`${startName} (${++i})`);
    }
    fileName = `${startName} (${i})`;
  }

  let [header, base64File] = data.split(/;base64,/);
  let [fileType, fileSuffix] = header.replace(/data:/, "").split("/");
  fs.writeFileSync(`./public/${fileType}/${fileName}.${fileSuffix}`, base64File, {
    encoding: "base64",
  });
  return fileName + "." + fileSuffix;
}

module.exports = { write64FileWithCopies };