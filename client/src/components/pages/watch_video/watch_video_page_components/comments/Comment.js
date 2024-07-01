import React, { useState } from "react";
import {
  callWithEnter,
  dateDifference,
  getMediaFromServer,
} from "../../../../../utilities";
import IconSave from "../../../../icons/IconSave";
import IconEdit from "../../../../icons/IconEdit";
import IconTrash from "../../../../icons/IconTrash";

function Comment({ _id, user, text, date, edited, deleteComment, editComment, currentUser }) {
  const [editing, setEditing] = useState(false);
  const [commentContent, setCommentContent] = useState(text);
  const [expanded, setExpanded] = useState(false);
  function edit() {
    if (editing) {
      editComment(date, commentContent);
      setEditing(false);
    } else setEditing(true);
  }
  return (
    <div className="comment">
      <div className="comment-left">
        <img className="profile-pic small" src={getMediaFromServer("image", user.image)} />
      </div>
      <div className="comment-right">
        <div className="comment-header">
          <div className="comment-details">
            <span className="user">{user.name}</span>
            <span className="date">
              {dateDifference(date)} {edited && " (edited)"}
            </span>
          </div>
          <div className="comment-actions">
            {user == currentUser ? (
              <>
                <span className="delete-comment-button" onClick={() => deleteComment(date)}>
                  <IconTrash />
                </span>{" "}
                <span className="edit-comment-button" onClick={edit}>
                  {editing ? <IconSave /> : <IconEdit />}
                </span>
              </>
            ) : (
              ""
            )}
          </div>
        </div>
        <div className={expanded ? "comment-content open" : "comment-content closed"}>
          {editing ? (
            <input
              onKeyDown={(e) => callWithEnter(e, edit)}
              className="edit-comment-input"
              onChange={(e) => setCommentContent(e.target.value)}
              value={commentContent}
            />
          ) : (
            text
          )}
        </div>
        <span className="comment-expand" onClick={() => setExpanded(!expanded)}>
          {expanded ? "close" : "expand"}
        </span>
      </div>
    </div>
  );
}

export default Comment;
