import React, { useEffect, useRef, useState } from "react";
import VideoActionButton from "../action_button/VideoActionButton";
import "./VideoBlock.css";
import { numberFormatter } from "../../../../../utilities";
function VideoBlock({
  name,
  uploader,
  src,
  description,
  views,
  likes,
  date_time,
  tags,
  commentInput,
  like,
  likedVideo,
}) {
  function share() {
    navigator.clipboard.writeText(window.location.href);
  }
  const videoRef = useRef();

  // Auto play
  // useEffect(() => {
  //   setTimeout(() => {
  //     videoRef.current.play();
  //   }, 1000);
  // }, []);

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
            <VideoActionButton
              name="Comment"
              content=""
              callback={() => {
                commentInput.current.scrollIntoView({ behavior: "smooth" });
                setTimeout(() => {
                  commentInput.current.focus();
                }, [200]);
              }}
            />
            <VideoActionButton active={likedVideo} name="Like" content={likes} callback={like} />
            <VideoActionButton name="Share" content="" callback={share} />
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
          <div className="views">Views: {numberFormatter.format(views)}</div>
          <div className="description">{description}</div>
          <div className="tags">
            {tags.map((tag, index) => (
              <a key={"tag" + index} href="/" className="tag">
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
