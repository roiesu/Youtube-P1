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

export const fetchVideos = async () => {
  const response = await fetch('/path/to/videos.json');
  const data = await response.json();
  return data;
};

export { readImageIntoState, findVideoById };
