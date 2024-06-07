import React from "react";
import { callWithEnter } from "../../../../utilities";

function Comments({ comments, addComment, commentInput }) {
  return (
    <div className="comments">
      <div className="comments-title">
        {comments.length} {comments.length === 1 ? "Comment" : "Comments"}
      </div>
      <input
        id="comment-input"
        name="comment"
        ref={commentInput}
        onKeyDown={(e) => callWithEnter(e, addComment)}
      />
      <button onClick={addComment}>Add</button>
      <div className="comments-list">
        {comments.map((comment) => (
          <div className="comment">{`<${comment.date_time}> ${comment.user}: ${comment.text}`}</div>
        ))}
      </div>
    </div>
  );
}

export default Comments;
