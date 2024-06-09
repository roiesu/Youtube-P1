import React from "react";
import { callWithEnter, dateDifference } from "../../../../../utilities";
import "./Comments.css";

function Comments({ comments, addComment, commentInput }) {
  return (
    <div className="comments">
      <div className="comments-title">
        {comments.length} {comments.length === 1 ? "Comment" : "Comments"}
      </div>
      <input
        className="comment-input"
        name="comment"
        ref={commentInput}
        onKeyDown={(e) => callWithEnter(e, addComment)}
        placeholder="Enter a comment"
      />
      <button className={"comment-button"} onClick={addComment}>
        Add
      </button>
      <div className="comments-list">
        {comments.map((comment, index) => (
          <div key={"c" + index} className="comment">
            <div className="comment-header">
              <span className="user">{comment.user}</span>
              <span className="date">{dateDifference(comment.date_time)}</span>
            </div>
            <div className="comment-content">{comment.text}</div>
          </div>
        ))}
      </div>
    </div>
  );
}
export default Comments;
