package com.example.zhangfan.udapopmovies;

import java.util.ArrayList;

/**
 * Created by zhangfan on 2017/7/19.
 */

public class ResponseResult {
    public ResponseResult() {

    }

    private int page;
    private int total_results;
    private int total_pages;
    private ArrayList<MovieBean> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<MovieBean> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieBean> results) {
        this.results = results;
    }

}
