import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { Link } from "react-router-dom";

import "./App.css";
import SignUp from "../pages/sign_up/SignUp";
import SignIn from "../pages/sign_in/SignIn";
import MainPage from "../pages/main_page/MainPage";
import usersList from "../../data/users.json";
import videoList from "../../data/videos.json";
import Bar from "../pages/general_components/bar/Bar";
import WatchVideoPage from "../pages/watch_video/WatchVideoPage";
import UploadVideoPage from "../pages/upload_video/UploadVideoPage";

function App() {
  const [users, setUsers] = useState(usersList);
  const [currentUser, setCurrentUser] = useState();
  const [videos, setVideos] = useState(videoList);
  useEffect(() => {
    console.log("App");
  }, []);
  return (
    <div className="App">
      <Router>
        <Bar />
        <Routes>
          {/* Pages anyone can see */}
          <Route exact path="/" element={<MainPage videos={videos} />} />
          <Route
            exact
            path="/watch/:v?"
            element={<WatchVideoPage videos={videos} currentUser={currentUser} />}
          />
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
              <Route
                element={<SignIn users={users} setCurrentUser={setCurrentUser} />}
                exact
                path="/sign-in"
              />
            </>
          )}
        </Routes>
      </Router>
    </div>
  );
}

export default App;
