package com.example.zhangfan.udapopmovies;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by Harold on 2017/7/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoiveViewHolder> {

    private ArrayList<MovieBean> movieList;
    private Context mActivity;
    private final MovieItemOnClickListener movieItemOnClickListener;
    private static final String MOVIE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";

    public MovieAdapter(Context context, MovieItemOnClickListener movieLisner) {
        movieItemOnClickListener = movieLisner;
        mActivity = context;
    }

    public interface MovieItemOnClickListener {
        void onItemClick(MovieBean movie);
    }

    @Override
    public MoiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_grid_item, parent, false);


        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();


        // 假如是横屏，图片高度为设备宽度

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
        MovieBean movie = movieList.get(position);
        String imageUrl = MOVIE_IMAGE_URL + movie.getPosterPath();  //海报图

        Picasso.with(mActivity).load(imageUrl).into(holder.mMovieImage);
    }

    @Override
    public int getItemCount() {
        if (movieList == null) {
            return 0;
        }
        return movieList.size();
    }


    class MoiveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mMovieImage;

        public MoiveViewHolder(View itemView) {
            super(itemView);
            mMovieImage = (ImageView) itemView.findViewById(R.id.image_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            MovieBean movie = movieList.get(adapterPosition);
            movieItemOnClickListener.onItemClick(movie);
        }
    }

    public void setMovieDaList(ArrayList<MovieBean> movies) {
        movieList = movies;
        notifyDataSetChanged();
    }
}
