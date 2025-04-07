import React, { useState, createContext, useContext, useEffect } from "react";
const DarkThemeContext = createContext(null);

function useTheme() {
  return useContext(DarkThemeContext);
}

function ThemeContext({ children }) {
  const [theme, setTheme] = useState("light");

  useEffect(() => {
    var item = localStorage.getItem("theme");
    if (item) {
      setTheme(item);
    }
  }, []);

  useEffect(() => {
    localStorage.setItem("theme", theme);
  }, [theme]);

  function changeTheme() {
    setTheme(theme === "light" ? "dark" : "light");
  }

  return (
    <DarkThemeContext.Provider value={{ theme, changeTheme }}>{children}</DarkThemeContext.Provider>
  );
}

export { ThemeContext, useTheme };
