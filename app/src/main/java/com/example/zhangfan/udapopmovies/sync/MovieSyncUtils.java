package com.example.zhangfan.udapopmovies.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.zhangfan.udapopmovies.data.MovieContract;

/**
 * Created by Harold on 2017/8/3.
 */

public class MovieSyncUtils {

    private static boolean sInitialized;

    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, MovieSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }

    synchronized public static void initialize(@NonNull final Context context) {
        if (sInitialized) return;

        sInitialized = true;

        Thread checkForEmpty = new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = context.getContentResolver().query(
                        MovieContract.MovieEntry.CONTENT_URI,
                        new String[]{MovieContract.MovieEntry._ID},
                        null,
                        null,
                        null);
                if (cursor == null || cursor.getCount() == 0) {
                    startImmediateSync(context);
                }
                if (cursor != null) {
                    cursor.close();
                }
            }


        });

        /* Finally, once the thread is prepared, fire it off to perform our checks. */
        checkForEmpty.start();
    }
}
