package com.example.android_client.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import com.example.android_client.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


public class DataManager {
    public static int FILTER_UPLOADER_KEY = 1;
    public static int FILTER_TITLE_KEY = 2;
    private static ArrayList<User> usersList;
    private static ArrayList<Video> videoList;
    private static User currentUser;
    private static DataManager instance;
    private static boolean initialized;

    private DataManager() {
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void setInitialized(boolean initialized) {
        DataManager.initialized = initialized;
    }

    public static ArrayList<User> getUsersList() {
        return usersList;
    }

    public static ArrayList<Video> getVideoList() {
        return videoList;
    }

    public static void setUsersList(ArrayList<User> users) {
        usersList = users;
    }

    public static void setVideoList(ArrayList<Video> videos) {
        videoList = videos;
    }

    public static void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public static void initializeData(Context context) {
        if (DataManager.isInitialized()) {
            return;
        }
        Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        Type videoListType = new TypeToken<ArrayList<Video>>() {
        }.getType();
        DataManager.setUsersList(loadDataFromJson("users.json", userListType, context));
        DataManager.setVideoList(loadDataFromJson("videos.json", videoListType, context));
        for (User user : usersList) {
            user.setImage(Utilities.getResourceUriString(context, user.getImage(), "drawable"));
        }
        for (Video video : videoList) {
            video.createVideoDetails(context);
        }
        DataManager.setCurrentUser(DataManager.getUsersList().get(0));
        DataManager.setInitialized(true);
    }

    private static <T> ArrayList<T> loadDataFromJson(String fileName, Type type, Context context) {
        ArrayList<T> array = new ArrayList<>();
        try {
            // Open and read the json file
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Converts the file to string
            String json = new String(buffer, "UTF-8");
            // Parse the json file
            Gson gson = new Gson();

            array = gson.fromJson(json, type);
            return array;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void addUser(User user) {
        usersList.add(user);
    }

    public static User findUser(String username) {
        for (User user : usersList) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static Video findVideoById(int id, boolean addView) {
        for (Video video : videoList) {
            if (video.getId() == id) {
                if (addView) {
                    video.addView();
                }
                return video;
            }
        }
        return null;
    }

    public static void likeVideo(int id, String username) {
        for (Video video : videoList) {
            if (video.getId() == id) {
                video.like(username);
                return;
            }
        }
    }

    public static void commentVideo(int id, String username, String displayUsername, String text) {
        for (Video video : videoList) {
            if (video.getId() == id) {
                video.addComment(username, displayUsername, text);
                return;
            }
        }
    }

    public static void removeCommentFromVideo(int id, String username, Date date) {
        for (Video video : videoList) {
            if (video.getId() == id) {
                video.deleteComment(username, date);
                return;
            }
        }
    }

    public static void updateVideo(int videoId, String newName, String newDescription) {
        for (Video video : videoList) {
            if (video.getId() == videoId) {
                if (newName != null && !newName.isEmpty()) {
                    video.setName(newName);
                }
                if (newDescription != null && !newDescription.isEmpty()) {
                    video.setDescription(newDescription);
                }
            }
        }
    }

    public void updateCommentInVideo(int id, String username, Date date, String newText) {
        for (Video video : videoList) {
            if (video.getId() == id) {
                video.editComment(username, date, newText);
            }
        }
    }

    public static ArrayList<Video> filterVideosBy(int key, String value) {
        ArrayList<Video> filteredVideos = new ArrayList<>();
        Pattern regex = Pattern.compile(".*" + value + ".*", Pattern.CASE_INSENSITIVE);
        for (Video video : videoList) {
            String keyValue = key == FILTER_UPLOADER_KEY ? video.getUploader() : key == FILTER_TITLE_KEY ? video.getName() : "";
            if (regex.matcher(keyValue).matches()) {
                filteredVideos.add(video);
            }
        }
        return filteredVideos;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Video findVideoById(int id) {
        for (Video video : videoList) {
            if (video.getId() == id) {
                return video;
            }
        }
        return null;
    }


    public static List<Video> getUserVideos() {
        return videoList;
    }

    public static void deleteVideoById(int videoId) {
        Video video = findVideoById(videoId);
        if (video != null) {
            videoList.remove(video);
            // Also delete the video from the server or persistent storage if necessary
        }
    }


}