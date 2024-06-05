
const reader = new FileReader();
function readImageIntoState(imageFile, setState) {
  reader.onload = () => {
    setState(reader.result);
  };
  reader.readAsDataURL(imageFile);
}

export { readImageIntoState };
