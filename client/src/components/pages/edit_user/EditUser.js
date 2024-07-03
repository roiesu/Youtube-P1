import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useTheme } from "../general_components/ThemeContext";
import "../upload_video/UploadVideoPage.css";
import axios from "axios";

function EditUser({ currentUser }) {
  const { theme } = useTheme();

  const [username, setUsername] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [image, setImage] = useState("");

  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const changeUsername = (event) => {
    setUsername(event.target.value);
  };

  const changeName = (event) => {
    setName(event.target.value);
  };

  const changePassword = (event) => {
    setPassword(event.target.value);
  };

  const changeImage = (event) => {
    setImage(event.target.value);
  };

  const submit = async () => {
    if (username === "" || name === "" || password === "" || image === "") {
      setErrorMessage("One or more of the details missing");
      return;
    }

    try {
      const token = localStorage.getItem("token");
      const response = await axios.patch(
        `/api/users/${currentUser}`,
        {
          username: username,
          name: name,
          password: password,
          image: image
        },
        { headers: { Authorization: "Bearer " + token } }
      );
      if (response.status === 200) {
        navigate("/");
      }
    } catch (error) {
      if (error.response) {
        if (error.response.status === 404) {
          setErrorMessage("User not found");
        } else if (error.response.status === 500) {
          setErrorMessage("Internal server error");
        } else {
          setErrorMessage("An unexpected error occurred");
        }
      } else {
        setErrorMessage("An unexpected error occurred");
      }
    }
  };

  useEffect(() => {
    async function getUser() {
      if (!currentUser) return;
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(`/api/users/${currentUser}`, {
          headers: { Authorization: "Bearer " + token }
        });
        const found = response.data;
        setUsername(found.username);
        setName(found.name);
        setPassword(found.password);
        setImage(found.image);
      } catch (err) {
        if (err.response) {
          if (err.response.status === 404) {
            setErrorMessage("User not found");
          } else if (err.response.status === 500) {
            setErrorMessage("Internal server error");
          } else {
            setErrorMessage("An unexpected error occurred");
          }
        } else {
          setErrorMessage("An unexpected error occurred");
        }
      }
    }
    getUser();
  }, [currentUser]);

  return (
    <div className={`page video-upload-page ${theme}`}>
      {errorMessage && <p className="error-message">{errorMessage}</p>}
      <div className="video-upload-container">
        <h1>Edit User Details</h1>
        <div className="input-group">
          <input
            type="text"
            className="input-field"
            placeholder="Enter new username"
            aria-label="username"
            value={username}
            onChange={changeUsername}
          />
        </div>
        <div className="input-group">
          <input
            type="text"
            className="input-field"
            placeholder="Enter new name"
            aria-label="name"
            value={name}
            onChange={changeName}
          />
        </div>
        <div className="input-group">
          <input
            type="password"
            className="input-field"
            placeholder="Enter new password"
            aria-label="password"
            value={password}
            onChange={changePassword}
          />
        </div>
        <div className="input-group">
          <input
            type="text"
            className="input-field"
            placeholder="Enter new image URL"
            aria-label="image"
            value={image}
            onChange={changeImage}
          />
        </div>
        <button className="submit-button" onClick={submit}>
          Update User
        </button>
      </div>
    </div>
  );
}

export default EditUser;
