import React, { useState } from "react";
import { callWithEnter } from "../../../../../utilities";
import "./Comments.css";
import Comment from "./Comment";
import VideoActionButton from "../action_button/VideoActionButton";

function Comments({
  comments,
  addComment,
  deleteComment,
  editComment,
  commentInput,
  currentUser,
  showToast,
}) {
  return (
    <div className="comments">
      <div className="comments-title">
        {comments.length} {comments.length === 1 ? "Comment" : "Comments"}
      </div>
      <div className="comment-input-div">
        <input
          className="comment-input"
          name="comment"
          ref={commentInput}
          onKeyDown={(e) => callWithEnter(e, addComment)}
          placeholder="Enter a comment"
        />
        <VideoActionButton
          callback={addComment}
          name="comment"
          content={""}
          showToast={showToast}
          badMessage={"You can't comment if not signed in"}
          canActivate={currentUser != null}
        />
      </div>
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
