import React, { useState, useRef } from "react";
import { readFileIntoState } from "../../../../utilities";
import "./ImagePicker.css";
function ImagePicker({ setThumbnail, videoRef, videoPreview, thumbnail, showToast }) {
  const [imageURI, setImageURI] = useState(thumbnail);
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
    try {
      setThumbnail(canvasRef.current.toDataURL());
    } catch (err) {
      showToast("Couldn't get image");
    }
  }
  return (
    <div className="image-picker">
      <div className="video-container">
        <video crossOrigin="anonymous" controls loop key={videoPreview} ref={videoRef}>
          <source src={videoPreview} type="video/mp4" />
        </video>
      </div>
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
            <img
              style={{ display: "none" }}
              crossOrigin="anonymous"
              src={imageURI}
              onLoad={uploadThumbnail}
            />
          </>
        )}
      </div>
      <canvas allowTaint={true} className="thumb-canvas" height={180} width={320} ref={canvasRef} />
    </div>
  );
}

export default ImagePicker;
