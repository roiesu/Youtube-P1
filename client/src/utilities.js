const reader = new FileReader();
const shortFormatter = new Intl.NumberFormat("en-US", {
  notation: "compact",
  compactDisplay: "short",
});
const longFormatter = new Intl.NumberFormat("en-US", { maximumSignificantDiginits: 3 });

function readFileIntoState(file, setState) {
  reader.onload = () => {
    setState(reader.result);
  };
  reader.readAsDataURL(file);
}

function callWithEnter(event, func) {
  if (event.key == "Enter") {
    func();
  }
}
function secondsToTime(number) {
  let seconds = number % 60;
  seconds = seconds < 10 ? "0" + seconds : seconds;

  let minutes = Math.floor(number / 60) % 60;
  minutes = minutes < 10 ? "0" + minutes : minutes;

  let time = minutes + ":" + seconds;
  if (number >= 3600) {
    let hours = Math.floor(number / 3600);
    hours = hours < 10 ? "0" + hours : hours;
    time = hours + ":" + time;
  }
  return time;
}

function dateDifference(dateString) {
  const date = new Date(dateString);
  let diffTime = Math.floor(Math.abs(new Date() - date) / 1000);
  if (diffTime < 60) {
    return `${diffTime} seconds ago`;
  }
  diffTime = Math.floor(diffTime / 60);
  if (diffTime < 60) {
    return `${diffTime} minutes ago`;
  }
  diffTime = Math.floor(diffTime / 60);
  if (diffTime < 24) {
    return `${diffTime} hours ago`;
  }
  diffTime = Math.floor(diffTime / 24);
  if (diffTime < 30) {
    return `${diffTime} days ago`;
  }
  diffTime = Math.floor(diffTime / 30);
  if (diffTime < 12) {
    return `${diffTime} months ago`;
  }
  diffTime = Math.floor(diffTime / 12);
  return `${diffTime} years ago`;
}

function getMediaFromServer(type, name) {
  return `http://localhost:8080/media/${type}/${name}`;
}
function getQuery(url) {
  const queryArray = url.replace(/^.*\?/, "").split("&");
  let queryObj = {};
  queryArray.map((query) => {
    const [key, value] = query.split("=");
    queryObj[key] = value;
  });
  return queryObj;
}
function simpleErrorCatcher(error, handleTokenExpired, navigate, showToast) {
  console.log("HERE");
  console.log(error);
  if (error.response) {
    if (handleTokenExpired && error.response.status === 403) {
      handleTokenExpired(navigate);
    } else {
      showToast(error.response.data);
    }
    return;
  }
  showToast("An unexpected error accrued");
}
export {
  readFileIntoState,
  callWithEnter,
  secondsToTime,
  dateDifference,
  shortFormatter,
  longFormatter,
  getMediaFromServer,
  getQuery,
  simpleErrorCatcher,
};
