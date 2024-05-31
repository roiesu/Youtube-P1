import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import SignUp from "../pages/sign_up/SignUp";
import SignIn from "../pages/sign_in/SignIn";
import usersList from "../../data/users.json";

function App() {
  const [users, setUsers] = useState(usersList);
  const [currentUser, setCurrentUser] = useState();
  console.log(users);
  return (
    <Router basename="/">
      <div className="App">
        <Routes>
          <Route
            element={<SignUp users={users} setCurrentUser={setCurrentUser} setUsers={setUsers} />}
            path="/sign-up"
          />
          <Route element={<SignIn />} path="/sign-in" />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
