package com.example.zhangfan.udapopmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Harold on 2017/7/31.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.zhangfan.udapopmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_OVERVIEW = "overview";

        public static final String COLUMN_POPULARITY = "popularity";

        public static final String COLUMN_VOTE = "vote";

        public static final String COLUMN_RELEASE = "release";

        public static final String COLUMN_POSTER = "poster";

        public static final String COLUMN_BACKDROP = "backdrop";

        public static final String COLUMN_STAR = "star";

        public static Uri buildMovieUriWithID(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();
        }

    }
}
