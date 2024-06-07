import React, { useEffect, useRef } from "react";
import VideoActionButton from "../VideoActionButton";
import "./VideoBlock.css";
function VideoBlock({ name, uploader, src, description, views, likes, date_time, tags }) {
  // Auto play
  const videoRef = useRef();
  useEffect(() => {
    setTimeout(() => {
      videoRef.current.play();
    }, 500);
  }, []);

  return (
    <div className="video-block">
      <video controls className="video" ref={videoRef}>
        <source src={src} type="video/mp4" />
      </video>
      <div className="video-tools">
        <div className="first-row row">{name}</div>
        <div className="second-row row">
          <div className="uploader">Uploaded by {uploader}</div>
          <div className="actions">
            <VideoActionButton name="Comment" />
            <VideoActionButton name="Like" />
            <VideoActionButton name="Share" />
          </div>
        </div>
        <div className="description-div">
          <div className="date">
            uploaded in{" "}
            {new Date(date_time).toLocaleDateString("en", {
              year: "numeric",
              month: "short",
              day: "numeric",
            })}
          </div>
          <div className="views">Views: {views}</div>
          <div className="description">{description}</div>
          <div className="tags">
            {tags.map((tag) => (
              <a href="/" className="tag">
                #{tag}
              </a>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default VideoBlock;
