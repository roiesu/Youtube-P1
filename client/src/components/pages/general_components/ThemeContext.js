import React, { useState, createContext, useContext } from "react";
const DarkThemeContext = createContext(null);

function useTheme() {
  return useContext(DarkThemeContext);
}

function ThemeContext({ children }) {
  const [theme, setTheme] = useState("light");
  function changeTheme() {
    setTheme(theme === "light" ? "dark" : "light");
  }
  return (
    <DarkThemeContext.Provider value={{ theme, changeTheme }}>{children}</DarkThemeContext.Provider>
  );
}

export { ThemeContext, useTheme };
