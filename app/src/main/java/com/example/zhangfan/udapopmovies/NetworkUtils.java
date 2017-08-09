package com.example.zhangfan.udapopmovies;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.value;


/**
 * Created by Harold on 2017/7/17.
 */

public class NetworkUtils {
    /**
     * @param url
     * @param params
     */
    public static String httpGetDataFromUrl(String url, Map<String, String> params) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        HttpUrl httpUrl = builder.build();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        Response response = client.newCall(request).execute();

        if (response != null) {
            return response.body().string();
        }
        return null;
    }

    public static ArrayList<MovieBean> parseJson(String jsonString, String keys) {
        Gson gson = new Gson();
        ResponseResult result = gson.fromJson(jsonString, ResponseResult.class);
        if (result != null) {
            return result.getResults();
        } else {
            return null;
        }
    }

    public static int parseJsonToMovie(String jsonString) {
        Gson gson = new Gson();
        MovieBean result = gson.fromJson(jsonString, MovieBean.class);
        if (result != null) {
            return result.getRuntime();
        } else {
            return 0;
        }
    }

    public static ArrayList<Video> parseJsonToVideoPath(String jsonString) {
        GsonBuilder gson = new GsonBuilder();
        Type collectionType = new TypeToken<Result<Video>>() {
        }.getType();

        Result<Video> videos = gson.create().fromJson(jsonString, collectionType);

        return videos.getResults();
    }

    public static ArrayList<Review> parseJsonToReview(String jsonString) {
        GsonBuilder gson = new GsonBuilder();
        Type collectionType = new TypeToken<Result<Review>>() {
        }.getType();

        Result<Review> videos = gson.create().fromJson(jsonString, collectionType);

        return videos.getResults();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
