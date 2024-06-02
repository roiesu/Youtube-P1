import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import SignUp from "../pages/sign_up/SignUp";
import SignIn from "../pages/sign_in/SignIn";
import MainPage from "../pages/main_page/MainPage";
import usersList from "../../data/users.json";
import Bar from "../pages/general_components/bar/Bar";
import WatchVideoPage from "../pages/watch_video/WatchVideoPage";
import UploadVideoPage from "../pages/upload_video/UploadVideoPage";

function App() {
  const [users, setUsers] = useState(usersList);
  const [currentUser, setCurrentUser] = useState();
  return (
    <div className="App">
      <Router>
        <Routes>
          {/* Pages anyone can see */}
          <Route exact path="/" element={<MainPage />} />
          <Route exact path="/watch/:id" element={<WatchVideoPage />} />
          {currentUser ? (
            // Pages only users can see
            <>
              <Route path="/upload" element={<UploadVideoPage />} />
            </>
          ) : (
            <>
              {/* Pages only non users can see */}
              <Route
                element={
                  <SignUp users={users} setCurrentUser={setCurrentUser} setUsers={setUsers} />
                }
                exact
                path="/sign-up"
              />
              <Route element={<SignIn />} exact path="/sign-in" />
            </>
          )}
        </Routes>
      </Router>
 main
    </div>
  );
}

export default App;
