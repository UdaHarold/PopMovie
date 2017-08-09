package com.example.zhangfan.udapopmovies;

import java.io.Serializable;

import static com.example.zhangfan.udapopmovies.R.id.runtime;
import static com.example.zhangfan.udapopmovies.R.id.vote_average;

/**
 * Created by zhangfan on 2017/7/17.
 */

public class MovieBean implements Serializable {
    private int id;
    private float vote_average;  //用户评分
    private double popularity;  //热门程度
    private String title;  //电影名称
    private String overview; //剧情简介
    private String release_date;  //发布日期
    private String poster_path; //海报图（List）
    private String backdrop_path; //背景图（Detail）
    private int runtime;  // 电影时长
    private int star;  // 是否收藏(1表示收藏，默认是0)

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getVoteaAverage() {
        return vote_average;
    }

    public void setVoteaAverage(float voteaAverage) {
        this.vote_average = voteaAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String releaseDate) {
        this.release_date = releaseDate;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String posterPath) {
        this.poster_path = posterPath;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdrop_path = backdropPath;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }


}
