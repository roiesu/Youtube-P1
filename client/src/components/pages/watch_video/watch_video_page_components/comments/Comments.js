import React, { useState } from "react";
import { callWithEnter } from "../../../../../utilities";
import "./Comments.css";
import Comment from "./Comment";

function Comments({ comments, addComment, deleteComment, editComment, commentInput, currentUser }) {
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
          <Comment
            {...comment}
            key={"c" + index}
            currentUser={currentUser}
            deleteComment={deleteComment}
            editComment={editComment}
          />
        ))}
      </div>
    </div>
  );
}
export default Comments;
