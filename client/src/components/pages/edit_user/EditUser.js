import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useTheme } from "../general_components/ThemeContext";
import "../upload_video/UploadVideoPage.css";
import { getMediaFromServer, readFileIntoState } from "../../../utilities";
import axios from "axios";

function EditUser({ currentUser }) {
  const { theme } = useTheme();
  const [user, setUser] = useState({});
  const [previewImage, setPreviewImage] = useState();
  const [errorMessage, setErrorMessage] = useState("");
  const [image, setImage] = useState(false);
  const [name, setName] = useState();
  const [password, setPassword] = useState();
  const navigate = useNavigate();

  const changeName = (event) => {
    setName(event.target.value);
  };

  const changePassword = (event) => {
    setPassword(event.target.value);
  };

  const changeImage = (event) => {
    setImage(true);
    readFileIntoState(event.target.files[0], setPreviewImage);
  };
  const deleteUser = async () => {
    const answer = prompt("Enter password to confirm deletion");
    if (answer != user.password) {
      return;
    } else {
      console.log("Confirmed");
      return;
    }
  };
  const editUser = async () => {
    const body = {};
    if (name && name != user.name) {
      body.name = name;
    }
    if (password && password != user.password) {
      body.password = password;
    }
    if (image) {
      body.image = previewImage;
    }
    if (Object.keys(body).length == 0) {
      setErrorMessage("Didn't change anything");
      return;
    }
    try {
      const token = localStorage.getItem("token");
      const response = await axios.patch(`/api/users/${currentUser}`, body, {
        headers: { Authorization: "Bearer " + token },
      });
      if (response.status === 200) {
        navigate("/my-videos");
      }
    } catch (error) {
      if (error.response) {
        if (error.response.status === 404) {
          setErrorMessage("User not found");
        } else if (error.response.status === 500) {
          setErrorMessage("Internal server error");
        } else {
          setErrorMessage(error.response.message);
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
        const response = await axios.get(`/api/users/details/${currentUser}`, {
          headers: { Authorization: "Bearer " + token },
        });
        const found = response.data;
        setUser(found);
        setPreviewImage(getMediaFromServer("image", found.image));
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
        <div className="preview-img-container">
          <img className="preview-img-container" src={previewImage} />
        </div>
        <div className="input-group">
          <input
            type="text"
            className="input-field"
            placeholder="Enter new name"
            aria-label="name"
            defaultValue={user.name}
            onChange={changeName}
          />
          <input
            type="text"
            className="input-field"
            placeholder="Enter new password"
            aria-label="password"
            defaultValue={user.password}
            onChange={changePassword}
          />
          <input type="file" className="input-field" accept="image/*" onChange={changeImage} />
          <button className="submit-button" onClick={editUser}>
            Update User
          </button>
          <button className="delete-button" onClick={deleteUser}>
            Delete User
          </button>
        </div>
      </div>
    </div>
  );
}

export default EditUser;
