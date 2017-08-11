package com.example.zhangfan.udapopmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by Harold on 2017/8/5.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MoiveTrailerViewHolder> {

    // private static final String MOVIE_TRAILER_URL = "https://www.youtube.com/watch?v=6as8ahAr1Uc";

    private Context mContext;
    private String[] mPath;

    public MovieTrailerAdapter(Context context, String[] path) {
        mContext = context;
        mPath = path;
    }

    @Override
    public MoiveTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.movie_trailer_item, parent, false);
        return new MoiveTrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoiveTrailerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        if (mPath == null) {
            return 0;
        }
        return mPath.length;
    }

    class MoiveTrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView mTrailer;

        public MoiveTrailerViewHolder(View itemView) {
            super(itemView);
            mTrailer = (ImageView) itemView.findViewById(R.id.video_trailer);
            mTrailer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String url = "https://www.youtube.com/watch?v=" + mPath[position];
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(intent);
            }
        }
    }

}
