package com.example.zhangfan.udapopmovies;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhangfan.udapopmovies.data.MovieContract;
import com.google.gson.internal.LinkedTreeMap;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private ImageView mBackgroud;
    private TextView mRealeseDate;
    private TextView mVote;
    private TextView mOverview;
    private static final String MOVIE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String API_KEY = "";
    private RecyclerView mMovieTrailer;
    private MovieTrailerAdapter mTrailerAdapter;
    private TextView mRuntimeTextView;
    private TextView mReview;
    private static int MOVIE_ID = 0;

    private static final int MOVIE_RUNTIME_LOADER_ID = 1;
    private static final int MOVIE_VIDEO_LOADER_ID = 2;
    private static final int MOVIE_REVIEW_LOADER_ID = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        mTitle = (TextView) findViewById(R.id.title);
        mBackgroud = (ImageView) findViewById(R.id.post_image);
        mRealeseDate = (TextView) findViewById(R.id.release_date);
        mVote = (TextView) findViewById(R.id.vote_average);
        mOverview = (TextView) findViewById(R.id.overview);
        mMovieTrailer = (RecyclerView) findViewById(R.id.rv_movie_trailer);
        mRuntimeTextView = (TextView) findViewById(R.id.runtime);
        mReview = (TextView) findViewById(R.id.tv_review);

        final MovieBean movie = (MovieBean) getIntent().getSerializableExtra(getString(R.string.movie_key));

        if (movie != null) {
            MOVIE_ID = movie.getId();
            mTitle.setText(movie.getTitle());

            final Button starBtn = (Button) findViewById(R.id.star_movie);

            if (movie.getStar() == 0) {
                starBtn.setText("收藏");
            } else {
                starBtn.setText("取消收藏");
            }

            String imageUrl = MOVIE_IMAGE_URL + movie.getBackdropPath();  //背景图
            Picasso.with(MovieDetailActivity.this).load(imageUrl).into(mBackgroud);

            mRealeseDate.setText(movie.getReleaseDate());
            mVote.setText(String.valueOf(movie.getVoteaAverage()));
            mOverview.setText(movie.getOverview());


            starBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE, movie.getVoteaAverage());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE, movie.getReleaseDate());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER, movie.getPosterPath());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP, movie.getBackdropPath());

                    if (movie.getStar() == 0) {
                        contentValues.put(MovieContract.MovieEntry.COLUMN_STAR, 1);   // 是否收藏，收藏1
                        starBtn.setText("取消收藏");
                    } else {
                        contentValues.put(MovieContract.MovieEntry.COLUMN_STAR, 0);
                        starBtn.setText("收藏");
                    }

                    Uri updateUri = MovieContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(movie.getId())).build();
                    getContentResolver().update(updateUri, contentValues, null, null);

                }
            });

        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mMovieTrailer.setLayoutManager(linearLayoutManager);
        mMovieTrailer.setHasFixedSize(true);


        getSupportLoaderManager().initLoader(MOVIE_RUNTIME_LOADER_ID, null, runtime);
        getSupportLoaderManager().initLoader(MOVIE_VIDEO_LOADER_ID, null, trailer);
        getSupportLoaderManager().initLoader(MOVIE_REVIEW_LOADER_ID, null, review);

    }

    // 加载电影时长
    private LoaderManager.LoaderCallbacks<Integer> runtime = new LoaderManager.LoaderCallbacks<Integer>() {
        @Override
        public Loader<Integer> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<Integer>(MovieDetailActivity.this) {

                Integer mData = 0;

                @Override
                protected void onStartLoading() {
                    if (mData != 0) {
                        deliverResult(mData);
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public Integer loadInBackground() {
                    Integer runtime = 0;
                    String url = "";
                    if (MOVIE_ID > 0) {
                        url = BASE_URL + String.valueOf(MOVIE_ID);
                        Map<String, String> params = new HashMap<>();
                        params.put("api_key", API_KEY);
                        try {
                            String result = NetworkUtils.httpGetDataFromUrl(url, params);
                            runtime = NetworkUtils.parseJsonToMovie(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    return runtime;
                }

                public void deliverResult(Integer data) {
                    mData = data;
                    super.deliverResult(data);
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<Integer> loader, Integer data) {
            mRuntimeTextView.setText("电影时长： " + data.toString() + " 分钟");
        }

        @Override
        public void onLoaderReset(Loader<Integer> loader) {


        }
    };

    //加载预告片
    private LoaderManager.LoaderCallbacks<String[]> trailer = new LoaderManager.LoaderCallbacks<String[]>() {
        @Override
        public Loader<String[]> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<String[]>(MovieDetailActivity.this) {
                String[] mData = null;

                @Override
                protected void onStartLoading() {
                    if (mData != null) {
                        deliverResult(mData);
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public String[] loadInBackground() {

                    String url = "";
                    if (MOVIE_ID > 0) {
                        url = BASE_URL + String.valueOf(MOVIE_ID) + "/videos";
                        Map<String, String> params = new HashMap<>();
                        params.put("api_key", API_KEY);
                        try {
                            String result = NetworkUtils.httpGetDataFromUrl(url, params);
                            ArrayList<Video> videos = NetworkUtils.parseJsonToVideoPath(result);
                            if (videos != null && !videos.isEmpty()) {
                                int i = 0;
                                String[] paths = new String[videos.size()];
                                for (Video video : videos) {
                                    paths[i] = new String(video.getKey());
                                    i++;
                                }
                                return paths;
                            } else {
                                return null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                    return null;
                }

                public void deliverResult(String[] data) {
                    mData = data;
                    super.deliverResult(data);
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<String[]> loader, String[] data) {
            mTrailerAdapter = new MovieTrailerAdapter(MovieDetailActivity.this, data);
            mMovieTrailer.setAdapter(mTrailerAdapter);
        }

        @Override
        public void onLoaderReset(Loader<String[]> loader) {

        }
    };

    //加载评论
    private LoaderManager.LoaderCallbacks<ArrayList<Review>> review = new LoaderManager.LoaderCallbacks<ArrayList<Review>>() {
        @Override
        public Loader<ArrayList<Review>> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<ArrayList<Review>>(MovieDetailActivity.this) {
                ArrayList<Review> mData = null;

                @Override
                protected void onStartLoading() {
                    if (mData != null) {
                        deliverResult(mData);
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public ArrayList<Review> loadInBackground() {
                    String url = "";
                    if (MOVIE_ID > 0) {
                        url = BASE_URL + String.valueOf(MOVIE_ID) + "/reviews";
                        Map<String, String> params = new HashMap<>();
                        params.put("api_key", API_KEY);
                        try {
                            String result = NetworkUtils.httpGetDataFromUrl(url, params);
                            ArrayList<Review> reviews = NetworkUtils.parseJsonToReview(result);
                            if (reviews != null && !reviews.isEmpty()) {
                                return reviews;
                            } else {
                                return null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                    return null;
                }

                public void deliverResult(ArrayList<Review> data) {
                    mData = data;
                    super.deliverResult(data);
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Review>> loader, ArrayList<Review> data) {
            if (data != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Review review : data) {
                    stringBuilder.append(review.getContent());
                    stringBuilder.append("\n\n");
                    stringBuilder.append("\t\t\t");
                    stringBuilder.append(review.getAuthor());
                    stringBuilder.append("\n\n\n");
                }
                mReview.setText(stringBuilder.toString());
            }
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Review>> loader) {

        }
    };

}
