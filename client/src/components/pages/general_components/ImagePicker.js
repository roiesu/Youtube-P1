import React, { useState, useRef } from "react";
import { readFileIntoState } from "../../../utilities";
function ImagePicker({ setThumbnail, videoRef }) {
  const [imageURI, setImageURI] = useState(null);
  const [isCatchingFrame, setIsCatchingFrame] = useState(false);
  const canvasRef = useRef(null);

  function captureFrame() {
    getThumbnail(videoRef.current, videoRef.current.videoWidth, videoRef.current.videoHeight);
  }
  function uploadThumbnail(e) {
    getThumbnail(e.target, e.target.width, e.target.height);
  }
  function getThumbnail(item, width, height) {
    const heightRatio = height / canvasRef.current.height;
    const newWidth = width / heightRatio;
    const offsetX = (canvasRef.current.width - newWidth) / 2;
    const context = canvasRef.current.getContext("2d");
    context.rect(0, 0, canvasRef.current.width, canvasRef.current.height);
    context.fillStyle = "black";
    context.fill();
    context.drawImage(item, 0, 0, width, height, offsetX, 0, newWidth, canvasRef.current.height);
    setThumbnail(canvasRef.current.toDataURL());
  }
  return (
    <div>
      <div>
        <button disabled={isCatchingFrame} onClick={() => setIsCatchingFrame(true)}>
          Catch frame
        </button>
        <button disabled={!isCatchingFrame} onClick={() => setIsCatchingFrame(false)}>
          Upload Thumbnail
        </button>
      </div>
      <div>
        {isCatchingFrame ? (
          <button onClick={captureFrame}>capture frame for thumbnail</button>
        ) : (
          <>
            <input
              type="file"
              className="input-field"
              accept="image/*"
              onChange={(e) => readFileIntoState(e.target.files[0], setImageURI)}
            />
            <img style={{ display: "none" }} src={imageURI} onLoad={uploadThumbnail} />
          </>
        )}
      </div>
      <canvas className="thumb-canvas" height={180} width={320} ref={canvasRef} />
    </div>
  );
}

export default ImagePicker;
