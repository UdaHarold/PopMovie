package com.example.zhangfan.udapopmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private ImageView mBackgroud;
    private TextView mRealeseDate;
    private TextView mVote;
    private TextView mOverview;
    private static final String MOVIE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        mTitle = (TextView) findViewById(R.id.title);
        mBackgroud = (ImageView) findViewById(R.id.post_image);
        mRealeseDate = (TextView) findViewById(R.id.release_date);
        mVote = (TextView) findViewById(R.id.vote_average);
        mOverview = (TextView) findViewById(R.id.overview);

        MovieBean movie = (MovieBean) getIntent().getSerializableExtra(getString(R.string.movie_key));

        if (movie != null) {
            mTitle.setText(movie.getTitle());

            String imageUrl = MOVIE_IMAGE_URL + movie.getBackdrop_path();  //背景图
            Picasso.with(MovieDetailActivity.this).load(imageUrl).into(mBackgroud);

            mRealeseDate.setText(movie.getRelease_date());
            mVote.setText(String.valueOf(movie.getVote_average()));
            mOverview.setText(movie.getOverview());
        }

    }
}
