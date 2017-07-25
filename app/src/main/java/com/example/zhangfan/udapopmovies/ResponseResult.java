package com.example.zhangfan.udapopmovies;

import java.util.ArrayList;

/**
 * Created by zhangfan on 2017/7/19.
 */

public class ResponseResult {
    public ResponseResult() {

    }

    // 字段名必须和response json result 一样

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

    public int getTotalResults() {
        return total_results;
    }

    public void setTotalResults(int total_results) {
        this.total_results = total_results;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public void setTotalPages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<MovieBean> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieBean> results) {
        this.results = results;
    }

}
