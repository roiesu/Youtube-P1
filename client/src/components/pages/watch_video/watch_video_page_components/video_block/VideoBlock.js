import React, { useEffect, useState } from "react";
import VideoActionButton from "../action_button/VideoActionButton";
import "./VideoBlock.css";
import { longFormatter, getMediaFromServer } from "../../../../../utilities";
import ShareMenu from "../share_menu/ShareMenu";
import IconLikeFilled from "../../../../icons/IconLikeFilled";
import IconLikeEmpty from "../../../../icons/IconLikeEmpty";
import { Link } from "react-router-dom";

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
  showToast,
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
          <Link className="user-details" to={`/channel/${uploader.username}`}>
            <img className="profile-pic" src={getMediaFromServer("image", uploader.image)} />
            <div>{uploader.name}</div>
          </Link>
          <div className="actions">
            <VideoActionButton
              name="Comment"
              content=""
              callback={scrollToComment}
              canActivate={loggedIn}
              badMessage={"You can't comment if not signed in"}
              showToast={showToast}
            />
            <VideoActionButton
              active={likedVideo}
              name={likedVideo ? <IconLikeFilled /> : <IconLikeEmpty />}
              content={likes}
              callback={like}
              canActivate={loggedIn}
              showToast={showToast}
              badMessage={"You can't like a video if not signed in"}
            />
            <VideoActionButton
              name="Share"
              content=""
              canActivate={true}
              showToast={showToast}
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
