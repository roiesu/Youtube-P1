import React, { useEffect, useState } from "react";
import "./App.css";
import SignUp from "../pages/sign_up/SignUp";
import SignIn from "../pages/sign_in/SignIn";
import usersList from "../../data/users.json";
function App() {
  const [users, setUsers] = useState(usersList);
  const [currentUser, setCurrentUser] = useState();
  console.log(users);
  return (
    <div className="App">
      <SignUp users={users} setCurrentUser={setCurrentUser} setUsers={setUsers} />
      {/* <SignIn /> */}
    </div>
  );
}

export default App;
