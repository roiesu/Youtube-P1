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
  return new Promise((resolve) => {
    const dataListener = (data) => {
      client.removeListener("error", errorListener);
      resolve(data.toString()); 
    };
    const errorListener = () => {
      client.removeListener("data", dataListener);
      resolve("empty");
    };

    client.once("data", dataListener);
    client.once("error", errorListener);

    client.write(message);
  });
}
module.exports = { client, sendMessageToTcpServer };
