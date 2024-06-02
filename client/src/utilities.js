import videoList from "./data/videos.json";

const reader = new FileReader();
function readImageIntoState(imageFile, setState) {
  reader.onload = () => {
    setState(reader.result);
  };
  reader.readAsDataURL(imageFile);
}

function findVideoById(id) {
  const video = videoList.find((video) => video.id == id);
  return video;
}

export { readImageIntoState, findVideoById };
