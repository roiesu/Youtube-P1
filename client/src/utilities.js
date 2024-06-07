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

export { readImageIntoState, callWithEnter };
