package com.example.android_client.datatypes;

import com.example.android_client.entities.Video;

import java.util.List;

public class VideoWithLikes {
    Video video;
    List<String> likes;

    public VideoWithLikes(Video video, List<String> likes) {
        this.video = video;
        this.likes = likes;
    }

    public Video getVideo() {
        return video;
    }

    public List<String> getLikes() {
        return likes;
    }

}
