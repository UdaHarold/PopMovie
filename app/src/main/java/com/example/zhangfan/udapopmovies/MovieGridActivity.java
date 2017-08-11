package com.example.zhangfan.udapopmovies;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.zhangfan.udapopmovies.data.MovieContract.*;
import com.example.zhangfan.udapopmovies.sync.MovieSyncUtils;


public class MovieGridActivity extends AppCompatActivity
        implements MovieAdapter.MovieItemOnClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIE_LOADER_ID = 1;
    private static final String ORDER_BY = "orderBy";
    private int mPosition = RecyclerView.NO_POSITION;

    private RecyclerView movieGrid;
    private MovieAdapter movieAdapter;
    private ProgressBar mLoadingIndicator;

    public static final String[] MAIN_MOVIE_PROJECTION = {
            MovieEntry._ID,
            MovieEntry.COLUMN_TITLE,
            MovieEntry.COLUMN_OVERVIEW,
            MovieEntry.COLUMN_POPULARITY,
            MovieEntry.COLUMN_VOTE,
            MovieEntry.COLUMN_RELEASE,
            MovieEntry.COLUMN_POSTER,
            MovieEntry.COLUMN_BACKDROP,
            MovieEntry.COLUMN_STAR
    };

    public static final int INDEX_MOVIE_ID = 0;
    public static final int INDEX_MOVIE_TITLE = 1;
    public static final int INDEX_MOVIE_OVERVIEW = 2;
    public static final int INDEX_MOVIE_POPULARITY = 3;
    public static final int INDEX_MOVIE_VOTE = 4;
    public static final int INDEX_MOVIE_RELEASE = 5;
    public static final int INDEX_MOVIE_POSTER = 6;
    public static final int INDEX_MOVIE_BACKDROP = 7;
    public static final int INDEX_MOVIE_STAR = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_grid);
        movieGrid = (RecyclerView) findViewById(R.id.rv_movie_grid);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        GridLayoutManager layout = new GridLayoutManager(this, 2);
        movieGrid.setLayoutManager(layout);
        movieAdapter = new MovieAdapter(this, this);
        movieGrid.setAdapter(movieAdapter);

        Bundle bundle = new Bundle();
        bundle.putString(ORDER_BY, "top_rated");

        showLoading();

        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, bundle, this);

        MovieSyncUtils.initialize(this);
    }

    private void showMoiveGridView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        movieGrid.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        movieGrid.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            case MOVIE_LOADER_ID:
                if (args == null) {
                    return null;
                }
                String order = args.getString(ORDER_BY);
                String selection = null;
                String[] selectionArgs = null;
                String sortedBy = null;
                if (order.equals("popular")) {
                    sortedBy = MovieEntry.COLUMN_POPULARITY + " DESC ";
                } else if (order.equals("top_rated")) {
                    sortedBy = MovieEntry.COLUMN_VOTE + " DESC ";
                } else if (order.equals("star")) {
                    selection = MovieEntry.COLUMN_STAR + " = ? ";
                    selectionArgs = new String[]{"1"};
                }

                return new CursorLoader(
                        this,
                        MovieEntry.CONTENT_URI,
                        MAIN_MOVIE_PROJECTION,
                        selection,
                        selectionArgs,
                        sortedBy
                );
            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        movieGrid.smoothScrollToPosition(mPosition);
        if (data != null && data.getCount() != 0) showMoiveGridView();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(MovieBean movie) {
        if (movie != null) {
            Intent detail = new Intent(MovieGridActivity.this, MovieDetailActivity.class);
            detail.putExtra(getString(R.string.movie_key), movie);
            startActivity(detail);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_grid_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            case R.id.order_populate:
                movieAdapter.swapCursor(null);

                Bundle bundle1 = new Bundle();
                bundle1.putString(ORDER_BY, "popular");

                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, bundle1, MovieGridActivity.this);

                return true;
            case R.id.order_top_rated:
                movieAdapter.swapCursor(null);

                Bundle bundle2 = new Bundle();
                bundle2.putString(ORDER_BY, "top_rated");

                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, bundle2, MovieGridActivity.this);

                return true;
            case R.id.order_star:

                movieAdapter.swapCursor(null);

                Bundle bundle3 = new Bundle();
                bundle3.putString(ORDER_BY, "star");

                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, bundle3, MovieGridActivity.this);

                return true;

            case R.id.fresh:

                // 刷新，onCreated 使用 SyncAdapter
                finish();
                startActivity(getIntent());

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
