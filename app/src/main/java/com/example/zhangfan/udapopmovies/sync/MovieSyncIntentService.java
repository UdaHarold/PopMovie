package com.example.zhangfan.udapopmovies.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Harold on 2017/8/3.
 */

public class MovieSyncIntentService extends IntentService {
    public MovieSyncIntentService() {
        super("MovieSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        MovieSyncTask.syncMovie(this);
    }
}
