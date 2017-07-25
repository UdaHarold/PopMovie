package com.example.zhangfan.udapopmovies;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by zhangfan on 2017/7/17.
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

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
