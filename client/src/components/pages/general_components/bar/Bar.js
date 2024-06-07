import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

function Bar() {
  const location = useLocation();
  const [visible, setVisible] = useState(false);
  useEffect(() => {
    if (location.pathname.match("sign")) {
      setVisible(false);
      return;
    }
    setVisible(true);
  }, [location]);

  return visible ? <div>Bar</div> : "";
}

export default Bar;
