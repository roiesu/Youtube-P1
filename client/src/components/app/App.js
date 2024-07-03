import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { Link } from "react-router-dom";

import "./App.css";
import SignUp from "../pages/sign_up/SignUp";
import SignIn from "../pages/sign_in/SignIn";
import MainPage from "../pages/main_page/MainPage";
import Bar from "../pages/general_components/bar/Bar";
import WatchVideoPage from "../pages/watch_video/WatchVideoPage";
import UploadVideoPage from "../pages/upload_video/UploadVideoPage";
import MyVideos from "../pages/MyVideos/MyVideos.js";
import Page404 from "../pages/page_404/Page404";
import VideoEdit from "../pages/VideoEdit/VideoEdit.js";
import EditUser from "../pages/edit_user/EditUser.js";
import { ThemeContext } from "../pages/general_components/ThemeContext";
import ChannelPage from "../pages/channel_page/ChannelPage.js";

function App() {
  const [currentUser, setCurrentUser] = useState();
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
            <Route exact path="/watch/:v?" element={<WatchVideoPage currentUser={currentUser} />} />
            <Route exact path="/channel/:id" element={<ChannelPage />} />
            {currentUser ? (
              // Pages only users can see
              <>
                <Route path="/my-videos" element={<MyVideos currentUser={currentUser} />} />
                <Route path="/video/upload" element={<UploadVideoPage currentUser={currentUser} />} />
                <Route path="/video/edit/:v?channel?" element={<VideoEdit currentUser={currentUser} />} />
                <Route path="/user/edit" element={<EditUser currentUser={currentUser} />} />
              </>
            ) : (
              <>
                {/* Pages only non users can see */}
                <Route element={<SignUp setCurrentUser={setCurrentUser} />} exact path="/sign-up" />
                <Route element={<SignIn setCurrentUser={setCurrentUser} />} exact path="/sign-in" />
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
