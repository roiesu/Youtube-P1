const reader = new FileReader();
function readImage(imageFile, setState) {
  reader.onload = () => {
    setState(reader.result);
  };
  reader.readAsDataURL(imageFile);
}
export { readImage };
