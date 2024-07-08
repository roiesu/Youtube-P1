import React from "react";
import "./Toast.css";
import { useTheme } from "../ThemeContext";
function Toast({ message, active }) {
  const { theme } = useTheme();
  return <div className={`toast ${theme} ${active}`}>{message}</div>;
}

export default Toast;
