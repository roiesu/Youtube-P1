import React, { useState } from "react";
import "./App.css";
import SignUp from "./components/pages/SignUp";

function App() {
  const [users, setUsers] = useState([]);
  const [currentUser, setCurrentUser] = useState();
  console.log("users", users);
  console.log("current", currentUser);
  return (
    <div className="App">
      <SignUp users={users} setCurrentUser={setCurrentUser} setUsers={setUsers} />
    </div>
  );
}

export default App;
