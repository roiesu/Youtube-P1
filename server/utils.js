const fs = require("fs");

function write64FileWithCopies(dest, data) {
  let fileName = dest;
  let file = fs.existsSync(dest);
  if (file) {
    let i = 0;
    let [start, end] = dest.split(/\.(?=[^\.]+$)/);
    while (file) {
      file = fs.existsSync(`${start} (${++i}).${end}`);
    }
    fileName = `${start} (${i}).${end}`;
  }

  let base64File = data.replace(/^.*;base64,/, "");
  fs.writeFileSync(fileName, base64File, { encoding: "base64" });
  return fileName;
}

module.exports = { write64FileWithCopies };
