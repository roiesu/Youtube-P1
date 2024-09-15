import "./VideoActionButton.css";

function VideoActionButton({
  name,
  content,
  callback,
  active,
  canActivate,
  badMessage,
  showToast,
}) {
  return (
    <div
      onClick={() => {
        if (canActivate) {
          callback();
        } else {
          showToast(badMessage);
        }
      }}
      className={active ? "video-action-button button active" : "video-action-button button"}
    >
      {name} {content}
    </div>
  );
}

export default VideoActionButton;
