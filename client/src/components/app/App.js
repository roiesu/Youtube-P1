import React, { useState } from "react";
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
import MyVideos from "../pages/MyVideos/MyVideos.js";
import MyVideos from "../pages/MyVideos/MyVideos.js"
import Page404 from "../pages/page_404/Page404";
import VideoEdit from "../pages/VideoEdit/VideoEdit.js";
import VideoEdit from "../pages/VideoEdit/VideoEdit.js"
import { ThemeContext } from "../pages/general_components/ThemeContext";

function App() {
  const [users, setUsers] = useState(usersList);
  const [currentUser, setCurrentUser] = useState(usersList[0]);
  const [videos, setVideos] = useState(videoList);
  return (
    <ThemeContext>
      <div className="App">
        <Router>
          <div className="img">
            <Link to="/">
              <img src=" ../../../logo.png" />
            </Link>
          </div>
          <Bar logout={() => setCurrentUser(null)} loggedIn={currentUser != null} />
          <Routes>
            {/* Pages anyone can see */}
            <Route
              exact
              path="/"
              element={<MainPage videos={videos} currentUser={currentUser} />}
            />
            <Route
              exact
              path="/watch/:v?"
              element={<WatchVideoPage videos={videos} currentUser={currentUser} />}
            />
            {currentUser ? (
              // Pages only users can see
              <>
              <Route path="/myvideos" element={<MyVideos currentUser={currentUser} videos={videos} setVideos={setVideos}/>} />
              <Route path="/my-videos" element={<MyVideos currentUser={currentUser} videos={videos} setVideos={setVideos}/>} />
              <Route path="/UploadVideosPage" element={<UploadVideoPage setVideos={setVideos} videos={videos} currentUser={currentUser}/>} />
              <Route path="/videoEdit/:v?" element={<VideoEdit videos={videos} currentUser={currentUser} />}/>
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
            <Route path="*" element={<Page404 />} />
          </Routes>
        </Router>
      </div>
    </ThemeContext>
  );
}

export default App;
