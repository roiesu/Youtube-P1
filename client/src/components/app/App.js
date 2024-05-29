import React, { useEffect, useState } from "react";
import "./App.css";
import SignUp from "../pages/sign_up/SignUp";
import SignIn from "../pages/sing_in/SignIn";

function App() {
  const [users, setUsers] = useState([]);
  const [currentUser, setCurrentUser] = useState();

  return (
    <div className="App">
      <SignUp users={users} setCurrentUser={setCurrentUser} setUsers={setUsers} />
      {/* <SignIn /> */}
    </div>
  );
}

export default App;
