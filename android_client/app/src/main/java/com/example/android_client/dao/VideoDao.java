package com.example.android_client.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.entities.Video;

import java.util.List;

@Dao
public interface VideoDao {
    @Transaction
    @Query("SELECT _id,name,uploaderId,date,views,thumbnail,duration FROM video " +
            "WHERE name LIKE  '%' || :searchValue || '%' " +
            "ORDER BY views DESC " +
            "LIMIT 10")
    List<VideoWithUser> topTenVideos(String searchValue);

    @Transaction
    @Query("SELECT _id,name,uploaderId,date,views,thumbnail,duration FROM video " +
            "WHERE name LIKE  '%' || :searchValue || '%' AND views <= :lastViews AND _id <> :lastId " +
            "ORDER BY RANDOM() " +
            "LIMIT 10")
    List<VideoWithUser> restTenVideos(String searchValue, Long lastViews, String lastId);


    @Query("DELETE from video")
    void deleteAll();

    @Query("SELECT * from video")
    List<Video> index();

    @Transaction
    @Query("SELECT * from video WHERE uploaderId = :channel AND _id = :videoId")
    VideoWithUser getVideoWithUser(String channel, String videoId);

    @Query("SELECT * from video WHERE uploaderId = :channel AND _id = :videoId")
    Video getVideo(String channel, String videoId);

    @Insert
    void insert(Video... videos);

    @Query("UPDATE video SET likesNum = :likesNum WHERE _id = :videoId")
    void setLikesNum(int likesNum, String videoId);

    @Query("UPDATE video SET views = :views WHERE _id = :videoId")
    void increaseViews(long views, String videoId);

    @Transaction
    @Query("DELETE FROM video WHERE _id = :videoId")
    void deleteVideo(String videoId);

    @Update
    void update(Video... videos);
  
    @Query("SELECT * from video WHERE uploaderId = :userId")
    List<VideoWithUser>getVideosByUserId(String userId);

    @Query("SELECT * FROM video WHERE uploaderId = :userId")
    LiveData<List<Video>> getVideosListByUserId(String userId);

    @Transaction
    @Query("SELECT * FROM video WHERE uploaderId = :userId")
    LiveData<List<VideoWithUser>> getVideoWithUserByUserId(String userId);
  
    @Query("UPDATE video SET commentsNum = commentsNum + :count WHERE _id =:videoId")
    void updateComments(String videoId,int count);

}



