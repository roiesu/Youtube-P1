import React, { useEffect, useState } from "react";
import VideoActionButton from "../action_button/VideoActionButton";
import "./VideoBlock.css";
import { longFormatter, getMediaFromServer } from "../../../../../utilities";
import ShareMenu from "../share_menu/ShareMenu";

function VideoBlock({
  name,
  uploader,
  src,
  description,
  views,
  likes,
  date,
  tags,
  commentInput,
  like,
  likedVideo,
  loggedIn,
}) {
  const [shareMenuVisible, setShareMenuVisible] = useState(false);

  function scrollToComment() {
    commentInput.current.scrollIntoView({ behavior: "smooth" });
    setTimeout(() => {
      commentInput.current.focus();
    }, [200]);
  }

  return (
    <div className="video-block">
      <video controls className="video">
        <source src={getMediaFromServer("video", src)} type="video/mp4" />
      </video>
      <div className="video-tools">
        <div className="first-row row">{name}</div>
        <div className="second-row row">
          <div className="user-details">
            <img className="profile-pic" src={getMediaFromServer("image", uploader.image)} />{" "}
            <div>{uploader.name}</div>
          </div>
          <div className="actions">
            <VideoActionButton
              name="Comment"
              content=""
              callback={scrollToComment}
              canActivate={loggedIn}
              badMessage={"You can't comment if not signed in"}
            />
            <VideoActionButton
              active={likedVideo}
              name="Like"
              content={likes}
              callback={like}
              canActivate={loggedIn}
              badMessage={"You can't like a video if not signed in"}
            />
            <VideoActionButton
              name="Share"
              content=""
              canActivate={true}
              callback={() => {
                setShareMenuVisible(true);
                navigator.clipboard.writeText(window.location.href);
              }}
            />
          </div>
        </div>
        <div className="description-div">
          <div className="date">
            uploaded on{" "}
            {new Date(date).toLocaleDateString("en", {
              year: "numeric",
              month: "short",
              day: "numeric",
            })}
          </div>
          <div className="views">Views: {longFormatter.format(views)}</div>
          <div className="description">{description}</div>
          <div className="tags">
            {tags.map((tag, index) => (
              <a key={"tag" + index} href="#" className="tag">
                #{tag}
              </a>
            ))}
          </div>
        </div>
      </div>
      <ShareMenu visible={shareMenuVisible} onClose={() => setShareMenuVisible(false)} />
    </div>
  );
}

export default VideoBlock;
