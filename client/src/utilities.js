const reader = new FileReader();

function readImageIntoState(imageFile, setState) {
  reader.onload = () => {
    setState(reader.result);
  };
  reader.readAsDataURL(imageFile);
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
export { readImageIntoState, callWithEnter, secondsToTime, dateDifference };
