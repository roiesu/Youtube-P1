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
import Toast from "../pages/general_components/toast/Toast.js";

function App() {
  const [currentUser, setCurrentUser] = useState();
  const [toastMessage, setToastMessage] = useState();
  const [toastShowing, setToastShowing] = useState("hide");
  let toastTimeout;
  function showToast(message) {
    if (toastShowing == "show") {
      clearTimeout(toastTimeout);
    }
    setToastMessage(message);
    setToastShowing("show");
    toastTimeout = setTimeout(() => {
      setToastShowing("hide");
    }, 3500);
  }

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
            <Route
              exact
              path="/"
              element={<MainPage currentUser={currentUser} showToast={showToast} />}
            />
            <Route
              exact
              path="/watch/:v?"
              element={<WatchVideoPage currentUser={currentUser} showToast={showToast} />}
            />
            <Route exact path="/channel/:id" element={<ChannelPage showToast={showToast} />} />
            {currentUser ? (
              // Pages only users can see
              <>
                <Route
                  path="/my-videos"
                  element={<MyVideos currentUser={currentUser} showToast={showToast} />}
                />
                <Route
                  path="/video/upload"
                  element={<UploadVideoPage currentUser={currentUser} showToast={showToast} />}
                />
                <Route
                  path="/video/edit/:v?channel?"
                  element={<VideoEdit currentUser={currentUser} showToast={showToast} />}
                />
                <Route
                  path="/user/edit"
                  element={
                    <EditUser logout={logout} currentUser={currentUser} showToast={showToast} />
                  }
                />
              </>
            ) : (
              <>
                {/* Pages only non users can see */}
                <Route
                  element={<SignUp setCurrentUser={setCurrentUser} showToast={showToast} />}
                  exact
                  path="/sign-up"
                />
                <Route
                  element={<SignIn setCurrentUser={setCurrentUser} showToast={showToast} />}
                  exact
                  path="/sign-in"
                />
              </>
            )}
            <Route path="*" element={<Page404 />} />
          </Routes>
        </Router>
        {toastMessage ? <Toast message={toastMessage} active={toastShowing} /> : ""}
      </div>
    </ThemeContext>
  );
}

export default App;
