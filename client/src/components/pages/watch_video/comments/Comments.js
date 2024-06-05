import React from "react";

function Comments({ videoId, comments, addComment }) {
  return (
    <div className="comments">
      <div className="comments-title">
        {comments.length} {comments.length === 1 ? "Comment" : "Comments"}
      </div>
      {comments.map((comment) => (
        <div className="comment">{`<${comment.date_time}> ${comment.user}: ${comment.text}`}</div>
      ))}
      <button onClick={() => addComment("Et Hapali", videoId)}>Add</button>
    </div>
  );
}

export default Comments;
