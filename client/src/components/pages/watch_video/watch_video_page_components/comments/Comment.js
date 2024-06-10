import React, { useState } from "react";
import { callWithEnter, dateDifference } from "../../../../../utilities";

function Comment({ user, text, date_time, edited, deleteComment, editComment, currentUser }) {
  console.log(user, text);
  const [editing, setEditing] = useState(false);
  const [commentContent, setCommentContent] = useState(text);
  return (
    <div className="comment">
      <div className="comment-header">
        <div className="comment-details">
          <span className="user">{user}</span>
          <span className="date">
            {dateDifference(date_time)} {edited && " (edited)"}
          </span>
        </div>
        <div className="comment-actions">
          {user == currentUser ? (
            <>
              <span className="delete-comment-button" onClick={() => deleteComment(date_time)}>
                delete
              </span>{" "}
              <span
                className="edit-comment-button"
                onClick={() => {
                  if (editing) {
                    editComment(date_time, commentContent);
                    setEditing(false);
                  } else setEditing(true);
                }}
              >
                {editing ? "save" : "edit"}
              </span>
            </>
          ) : (
            ""
          )}
        </div>
      </div>
      <div className="comment-content">
        {editing ? (
          <input onChange={(e) => setCommentContent(e.target.value)} value={commentContent} />
        ) : (
          text
        )}
      </div>
    </div>
  );
}

export default Comment;
