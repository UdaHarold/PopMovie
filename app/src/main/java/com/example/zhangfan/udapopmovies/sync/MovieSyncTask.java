package com.example.zhangfan.udapopmovies.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Movie;
import android.view.View;
import android.widget.Toast;

import com.example.zhangfan.udapopmovies.MovieBean;
import com.example.zhangfan.udapopmovies.MovieGridActivity;
import com.example.zhangfan.udapopmovies.NetworkUtils;
import com.example.zhangfan.udapopmovies.R;
import com.example.zhangfan.udapopmovies.data.MovieContract;
import com.example.zhangfan.udapopmovies.data.MovieContract.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.transition.move;
import static com.example.zhangfan.udapopmovies.AppConfig.API_KEY;
import static com.example.zhangfan.udapopmovies.AppConfig.MOVIE_API_BASE_URL;

/**
 * Created by Harold on 2017/8/3.
 */

public class MovieSyncTask {

    private static final String POPULAR_URL = "popular";
    private static final String TOP_RATED_URL = "top_rated";
    private static final String RESULT_KEY = "results";

    synchronized public static void syncMovie(Context context) {
        ArrayList<MovieBean> movieList = null;
        Boolean isConnective = NetworkUtils.isNetworkAvailable(context);
        if (isConnective) {
            try {
                // clear all DB
                ContentResolver contentResolver = context.getContentResolver();
                contentResolver.delete(MovieEntry.CONTENT_URI, null, null);

                Map<String, String> params = new HashMap<>();
                params.put("api_key", API_KEY);
                // request popular movie from http, then populate into SQLiteDB
                String url = NetworkUtils.httpGetDataFromUrl(MOVIE_API_BASE_URL + POPULAR_URL, params);
                movieList = NetworkUtils.parseJson(url, RESULT_KEY);
                if (movieList != null && !movieList.isEmpty()) {
                    ContentValues[] movieValues = new ContentValues[movieList.size()];
                    int i = 0;
                    for (MovieBean movie : movieList) {
                        movieValues[i] = new ContentValues();
                        movieValues[i].put(MovieEntry._ID, movie.getId());
                        movieValues[i].put(MovieEntry.COLUMN_TITLE, movie.getTitle());
                        movieValues[i].put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
                        movieValues[i].put(MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
                        movieValues[i].put(MovieEntry.COLUMN_VOTE, movie.getVoteaAverage());
                        movieValues[i].put(MovieEntry.COLUMN_RELEASE, movie.getReleaseDate());
                        movieValues[i].put(MovieEntry.COLUMN_POSTER, movie.getPosterPath());
                        movieValues[i].put(MovieEntry.COLUMN_BACKDROP, movie.getBackdropPath());
                        movieValues[i].put(MovieEntry.COLUMN_STAR, 0);   // 是否收藏，默认0
                        movieValues[i].put(MovieEntry.COLUMN_SORTED, 1);   // 排序方式，popular 1

                        i++;
                    }

                    contentResolver.bulkInsert(MovieEntry.CONTENT_URI, movieValues);


                    // request top rated movie from http, then populate into SQLiteDB
                    url = NetworkUtils.httpGetDataFromUrl(MOVIE_API_BASE_URL + TOP_RATED_URL, params);
                    movieList.clear();
                    movieList = NetworkUtils.parseJson(url, RESULT_KEY);

                    ContentValues[] topmovieValues = new ContentValues[movieList.size()];
                    i = 0;
                    for (MovieBean movie : movieList) {
                        topmovieValues[i] = new ContentValues();
                        topmovieValues[i].put(MovieEntry._ID, movie.getId());
                        topmovieValues[i].put(MovieEntry.COLUMN_TITLE, movie.getTitle());
                        topmovieValues[i].put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
                        topmovieValues[i].put(MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
                        topmovieValues[i].put(MovieEntry.COLUMN_VOTE, movie.getVoteaAverage());
                        topmovieValues[i].put(MovieEntry.COLUMN_RELEASE, movie.getReleaseDate());
                        topmovieValues[i].put(MovieEntry.COLUMN_POSTER, movie.getPosterPath());
                        topmovieValues[i].put(MovieEntry.COLUMN_BACKDROP, movie.getBackdropPath());
                        topmovieValues[i].put(MovieEntry.COLUMN_STAR, 0);   // 是否收藏，默认0
                        topmovieValues[i].put(MovieEntry.COLUMN_SORTED, 2);   // 排序方式，vote 2

                        i++;
                    }

                    contentResolver.bulkInsert(MovieEntry.CONTENT_URI, topmovieValues);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            String message = context.getString(R.string.connect_issue);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        }

    }
}
