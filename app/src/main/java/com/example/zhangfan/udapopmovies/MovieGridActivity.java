package com.example.zhangfan.udapopmovies;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;

public class MovieGridActivity extends AppCompatActivity
        implements MovieAdapter.MovieItemOnClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<MovieBean>> {

    private static final String MOVIE_API_URL = "http://api.themoviedb.org/3/movie/";
    private static final String RESULT_KEY = "results";
    private static final int MOVIE_LOADER_ID = 1;
    private static final String ORDER_BY = "orderBy";
    private static final String API_KEY = "";

    private RecyclerView movieGrid;
    private MovieAdapter movieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_grid);
        movieGrid = (RecyclerView) findViewById(R.id.rv_movie_grid);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        GridLayoutManager layout = new GridLayoutManager(this, 2);
        movieGrid.setLayoutManager(layout);
        movieAdapter = new MovieAdapter(this, this);
        movieGrid.setAdapter(movieAdapter);

        Bundle bundle = new Bundle();
        bundle.putString(ORDER_BY, "top_rated");

        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, bundle, this);
    }

    private void showMoiveGridView() {

        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        movieGrid.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {

        movieGrid.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    @Override
    public Loader<ArrayList<MovieBean>> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<ArrayList<MovieBean>>(this) {
            ArrayList<MovieBean> movieList = null;

            @Override
            public ArrayList<MovieBean> loadInBackground() {
                if (args == null) {
                    return null;
                }
                String order = args.getString(ORDER_BY);

                try {
                    Map<String, String> params = new HashMap<>();
                    params.put("api_key", API_KEY);
                    String url = NetworkUtils.httpGetDataFromUrl(MOVIE_API_URL + order, params);
                    movieList = NetworkUtils.parseJson(url, RESULT_KEY);
                    return movieList;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }

                Boolean isConnective = NetworkUtils.isNetworkAvailable(MovieGridActivity.this);
                if (isConnective) {
                    if (movieList != null) {
                        deliverResult(movieList);
                    } else {
                        mLoadingIndicator.setVisibility(View.VISIBLE);
                        forceLoad();
                    }
                } else {
                    String message = getString(R.string.connect_issue);
                    Toast.makeText(MovieGridActivity.this, message, Toast.LENGTH_SHORT).show();
                    showErrorMessage();
                }
            }

            @Override
            public void deliverResult(ArrayList<MovieBean> data) {
                movieList = data;
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieBean>> loader, ArrayList<MovieBean> data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        movieAdapter.setMovieDaList(data);
        if (data == null) {
            showErrorMessage();
        } else {
            showMoiveGridView();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieBean>> loader) {

    }

    @Override
    public void onItemClick(MovieBean movie) {
        if (movie != null) {
            Intent detail = new Intent(MovieGridActivity.this, MovieDetailActivity.class);
            detail.putExtra(getString(R.string.movie_key),movie);
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
                movieAdapter.setMovieDaList(null);

                Bundle bundle1 = new Bundle();
                bundle1.putString(ORDER_BY, "popular");

                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, bundle1, MovieGridActivity.this);

                return true;
            case R.id.order_top_rated:
                movieAdapter.setMovieDaList(null);

                Bundle bundle2 = new Bundle();
                bundle2.putString(ORDER_BY, "top_rated");

                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, bundle2, MovieGridActivity.this);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
