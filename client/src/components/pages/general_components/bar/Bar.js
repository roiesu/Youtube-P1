import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import '../bar.css';

function Bar() {
  const location = useLocation();
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    if (!location.pathname.match("sign")) {
      setVisible(true);
    } else {
      setVisible(false);
    }
  }, [location]);

  const toggleSidebar = () => {
    setVisible(!visible);
  };

  return (
    <div className="sidebar-container">
      <button className="sidebar-toggle" onClick={toggleSidebar}>
        {visible ? 'Close' : 'Open'}
      </button>
      {visible && (
        <div className="sidebar">
          <div href="#" className="sidebar-link">Home</div>
          <div href="#" className="sidebar-link">MyVideos</div>
          <div href="#" className="sidebar-link">Subscriptions</div>
        </div>
      )}
    </div>
  );
}

export default Bar;
