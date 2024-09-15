package com.example.android_client.api;

import androidx.lifecycle.MutableLiveData;

import com.example.android_client.ContextApplication;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.datatypes.VideoWithLikes;
import com.example.android_client.DataManager;
import com.example.android_client.datatypes.VideoWithUser;
import com.example.android_client.entities.Video;
import com.example.android_client.web_service.VideoWebServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoApi {
    Retrofit retrofit;
    VideoWebServiceAPI webServiceAPI;

    public VideoApi() {
        retrofit = new Retrofit.Builder().baseUrl(ContextApplication.context.getString(R.string.BaseUrlApi)).addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(VideoWebServiceAPI.class);
    }

    public void getVideo(MutableLiveData viewsData, String channel, String videoId) {
        if (videoId == null || channel == null) {
            return;
        }
        Call<Video> call = webServiceAPI.incViews(channel, videoId);
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video body = response.body();
                if (body != null) {
                    viewsData.setValue(body.getViews());
                } else {
                    viewsData.setValue((long)-1);
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                viewsData.setValue((long)-1);
                ContextApplication.showToast(t.getMessage());
            }
        });
    }


    public void getAll(MutableLiveData videoListData) {
        Call<List<VideoWithLikes>> call = webServiceAPI.getAll();
        call.enqueue(new Callback<List<VideoWithLikes>>() {
            @Override
            public void onResponse(Call<List<VideoWithLikes>> call, Response<List<VideoWithLikes>> response) {
                List<VideoWithLikes> body = response.body();
                if (body != null) {
                    videoListData.setValue(body);
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<List<VideoWithLikes>> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }

    public void getVideosDetailsByUser(MutableLiveData<List<Video>> videoListData, String username) {
        String header =DataManager.getTokenHeader();

        Call<List<Video>> call = webServiceAPI.getVideosDetailsByUser(username, header);
        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                List<Video> body = response.body();
                if (body != null) {
                    videoListData.setValue(body);
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }

    public void uploadVideo(MutableLiveData<Video> videoData) {
        String header =DataManager.getTokenHeader();
        Call<Video> call = webServiceAPI.uploadVideo(videoData.getValue().getUploaderId(), header, videoData.getValue());
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                Video body = response.body();
                if (body != null) {
                    videoData.setValue(body);
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });

    }

    public void deleteVideo(MutableLiveData<Video> data, String videoId, String userId) {
        String header =DataManager.getTokenHeader();
        Call<Void> call = webServiceAPI.deleteVideo(userId, videoId, header);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Video video = new Video();
                    video.set_id(videoId);
                    data.setValue(video);
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }

    public void updateVideo(MutableLiveData<Video> data) {
        String header =DataManager.getTokenHeader();
        Call<Void> call = webServiceAPI.updateVideo(DataManager.getCurrentUsername(), data.getValue().get_id(), header, data.getValue());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Video video = data.getValue();
                    video.set_id("-1");
                    data.setValue(video);
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });

    }

    public void getRecommendations(MutableLiveData<List<VideoWithUser>> data, String userId, String videoId) {
        String header =DataManager.getTokenHeader();
        Call<List<VideoWithUser>> call = webServiceAPI.getRecommendations(userId, videoId, header);
        call.enqueue(new Callback<List<VideoWithUser>>() {
            @Override
            public void onResponse(Call<List<VideoWithUser>> call, Response<List<VideoWithUser>> response) {
                List<VideoWithUser> body = response.body();
                if (body != null) {
                    data.setValue(body);
                } else {
                    Utilities.handleError(response);
                }
            }

            @Override
            public void onFailure(Call<List<VideoWithUser>> call, Throwable t) {
                ContextApplication.showToast(t.getMessage());
            }
        });
    }
}
