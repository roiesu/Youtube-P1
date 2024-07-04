import React from "react";
import "./ShareMenu.css";
import IconFacebook from "../../../../icons/IconFacebook";
import IconTwitter from "../../../../icons/IconTwitter";
import IconWhatsapp from "../../../../icons/IconWhatsapp";

function ShareMenu({ visible, onClose }) {
  if (!visible) return "";

  return (
    <div className="share-menu-overlay" onClick={onClose}>
      <div className="share-menu" onClick={(e) => e.stopPropagation()}>
        <button onClick={onClose} className="close-button">
          X
        </button>
        <div className="icons">
          <IconWhatsapp className="icon_Whatsapp" />
          <IconTwitter className="icon_Twitter" />
          <IconFacebook className="icon_Facebook" />
        </div>
        <div className="input-div">
          <input value={window.location.href} readOnly />
          <button onClick={() => navigator.clipboard.writeText(window.location.href)}>copy link</button>
        </div>
      </div>
    </div>
  );
}

export default ShareMenu;
