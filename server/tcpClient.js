const net = require("node:net");
const client = new net.Socket();
client.setMaxListeners(20);
client.on("error", (err) => {
  console.log("couldn't connect to the tcp server:", err.message);
});

client.on("close", () => {
  console.log("Disconnected from the TCP server");
});

// A function that prevents from listeners to pile up
function waitForNoListeners() {
  return new Promise((resolve) => {
    const interval = setInterval(() => {
      const listeners = client.listeners("data");
      if (listeners.length === 0) {
        clearInterval(interval);
        resolve();
      }
    }, 50);
  });
}

async function sendMessageToTcpServer(message) {
  await waitForNoListeners();
  return new Promise((resolve, reject) => {
    // Set up an event listener for TCP data
    client.once("data", (data) => {
      resolve(data.toString()); // Resolve with the TCP server's response
      console.log(data.toString());
    });

    // Handle any errors that occur
    client.once("error", (err) => {
      reject(err);
      console.log(err.message);
    });

    // Send the message to the TCP server
    client.write(message);
  });
}
module.exports = { client, sendMessageToTcpServer };
