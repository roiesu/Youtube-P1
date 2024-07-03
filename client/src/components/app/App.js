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
import MyVideos from "../pages/MyVideos/MyVideos.js";
import Page404 from "../pages/page_404/Page404";
import VideoEdit from "../pages/VideoEdit/VideoEdit.js";
import EditUser from "../pages/edit_user/EditUser.js";
import { ThemeContext } from "../pages/general_components/ThemeContext";
import ChannelPage from "../pages/channel_page/channel_page.js";

function App() {
  const [users, setUsers] = useState(usersList);
  const [currentUser, setCurrentUser] = useState();
  const [videos, setVideos] = useState(videoList);
  function logout() {
    localStorage.removeItem("token");
    setCurrentUser(null);
  }
  useEffect(() => {
    localStorage.removeItem("token");
  }, []);
  return (
    <ThemeContext>
      <div className="App">
        <Router>
          <div className="img">
            <Link to="/">
              <img src=" ../../../logo.png" />
            </Link>
          </div>
          <Bar logout={logout} loggedIn={currentUser != null} />
          <Routes>
            {/* Pages anyone can see */}
            <Route exact path="/" element={<MainPage currentUser={currentUser} />} />
            <Route
              exact
              path="/watch/:v?"
              element={<WatchVideoPage videos={videos} currentUser={currentUser} />}
            />
            {currentUser ? (
              // Pages only users can see
              <>
                <Route
                  path="/my-videos"
                  element={
                    <MyVideos currentUser={currentUser} videos={videos} setVideos={setVideos} />
                  }
                />
                <Route
                  path="/upload"
                  element={
                    <UploadVideoPage
                      setVideos={setVideos}
                      videos={videos}
                      currentUser={currentUser}
                    />
                  }
                />
                <Route
                  path="/edit/:v?"
                  element={<VideoEdit videos={videos} currentUser={currentUser} />}
                />
                <Route
                path="/edit-user"
                element={
                  <EditUser currentUser= {currentUser} />}
                />
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
                <Route
                  exact
                  path="/users/:id/videos"
                  element={<ChannelPage />}
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
