import React from "react";

function Comments({ comments, addComment, commentInput }) {
  return (
    <div className="comments">
      <div className="comments-title">
        {comments.length} {comments.length === 1 ? "Comment" : "Comments"}
      </div>
      {comments.map((comment) => (
        <div className="comment">{`<${comment.date_time}> ${comment.user}: ${comment.text}`}</div>
      ))}
      <input id="comment-input" name="comment" ref={commentInput} />
      <button onClick={addComment}>Add</button>
    </div>
  );
}

export default Comments;