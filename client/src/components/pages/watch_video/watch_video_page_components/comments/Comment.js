import React, { useState } from "react";
import { Link } from "react-router-dom";
import { callWithEnter, dateDifference, getMediaFromServer } from "../../../../../utilities";
import IconSave from "../../../../icons/IconSave";
import IconEdit from "../../../../icons/IconEdit";
import IconTrash from "../../../../icons/IconTrash";
import IconClose from "../../../../icons/IconClose";

function Comment({ _id, user, text, date, edited, deleteComment, editComment, currentUser }) {
  const [editing, setEditing] = useState(false);
  const [commentContent, setCommentContent] = useState(text);
  const [expanded, setExpanded] = useState(false);
  function edit() {
    if (editing) {
      editComment(_id, commentContent);
      setEditing(false);
    } else setEditing(true);
  }
  return (
    <div className="comment">
      <div className="comment-left">
        <Link to={`/channel/${user.username}`}>
          <img className="profile-pic small" src={getMediaFromServer("image", user.image)} />
        </Link>
      </div>
      <div className="comment-right">
        <div className="comment-header">
          <div className="comment-details">
            <Link to={`/channel/${user.username}`} className="user">
              {user.name}
            </Link>
            <span className="date">
              {dateDifference(date)} {edited && " (edited)"}
            </span>
          </div>
          <div className="comment-actions">
            {user.username == currentUser ? (
              <>
                <span className="delete-comment-button" onClick={() => deleteComment(_id)}>
                  <IconTrash />
                </span>
                <span className="edit-comment-button" onClick={edit}>
                  {editing ? <IconSave /> : <IconEdit />}
                </span>
                {editing ? (
                  <span
                    className="edit-comment-button"
                    onClick={() => {
                      setEditing(false);
                      setCommentContent(text);
                    }}
                  >
                    <IconClose />
                  </span>
                ) : (
                  ""
                )}
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
