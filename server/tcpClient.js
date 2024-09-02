const net = require("node:net");
const client = new net.Socket();

client.on("error", (err) => {
  console.log("couldn't connect to the tcp server:", err.message);
});

client.on("close", () => {
  console.log("Disconnected from the TCP server");
});

function sendMessageToTcpServer(message) {
  return new Promise((resolve, reject) => {
    // Set up an event listener for TCP data
    client.once("data", (data) => {
      resolve(data.toString()); // Resolve with the TCP server's response
    });

    // Handle any errors that occur
    client.once("error", (err) => {
      reject(err);
    });

    // Send the message to the TCP server
    client.write(message);
  });
}

module.exports = { client, sendMessageToTcpServer };
