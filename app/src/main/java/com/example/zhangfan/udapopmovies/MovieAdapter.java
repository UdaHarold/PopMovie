package com.example.zhangfan.udapopmovies;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.attr.id;
import static com.example.zhangfan.udapopmovies.AppConfig.MOVIE_IMAGE_BASE_URL;

/**
 * Created by Harold on 2017/7/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoiveViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    private final MovieItemOnClickListener movieItemOnClickListener;

    public MovieAdapter(Context context, MovieItemOnClickListener movieLisner) {
        movieItemOnClickListener = movieLisner;
        mContext = context;
    }

    public interface MovieItemOnClickListener {
        void onItemClick(MovieBean movie);
    }

    @Override
    public MoiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.movie_grid_item, parent, false);
        view.setFocusable(true);

        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();


        // 假如是横屏，图片高度为设备宽度

        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            lp.height = parent.getMeasuredWidth();
        } else {
            lp.height = parent.getMeasuredHeight() / 2;
        }


        view.setLayoutParams(lp);

        MoiveViewHolder viewHolder = new MoiveViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoiveViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String posterPath = mCursor.getString(MovieGridActivity.INDEX_MOVIE_POSTER);
        String imageUrl = MOVIE_IMAGE_BASE_URL + posterPath;  //海报图

        Picasso.with(mContext)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.mMovieImage);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }


    class MoiveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mMovieImage;

        public MoiveViewHolder(View itemView) {
            super(itemView);
            mMovieImage = (ImageView) itemView.findViewById(R.id.image_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();

            mCursor.moveToPosition(adapterPosition);

            MovieBean movie = new MovieBean();
            movie.setId(mCursor.getInt(MovieGridActivity.INDEX_MOVIE_ID));
            movie.setTitle(mCursor.getString(MovieGridActivity.INDEX_MOVIE_TITLE));
            movie.setOverview(mCursor.getString(MovieGridActivity.INDEX_MOVIE_OVERVIEW));
            movie.setPopularity(mCursor.getDouble(MovieGridActivity.INDEX_MOVIE_POPULARITY));
            movie.setVoteaAverage(mCursor.getFloat(MovieGridActivity.INDEX_MOVIE_VOTE));
            movie.setReleaseDate(mCursor.getString(MovieGridActivity.INDEX_MOVIE_RELEASE));
            movie.setPosterPath(mCursor.getString(MovieGridActivity.INDEX_MOVIE_POSTER));
            movie.setBackdropPath(mCursor.getString(MovieGridActivity.INDEX_MOVIE_BACKDROP));
            movie.setStar(mCursor.getInt(MovieGridActivity.INDEX_MOVIE_STAR));

            if (movieItemOnClickListener != null) {
                movieItemOnClickListener.onItemClick(movie);
            }
        }
    }

}
