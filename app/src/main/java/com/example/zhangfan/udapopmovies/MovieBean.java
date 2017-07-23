package com.example.zhangfan.udapopmovies;

import java.io.Serializable;

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

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
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

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


}
