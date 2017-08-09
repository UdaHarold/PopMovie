package com.example.zhangfan.udapopmovies;

import java.util.ArrayList;

/**
 * Created by Harold on 2017/8/8.
 */

public class Result<T> {
    public Result() {}
    private ArrayList<T> results;

    public ArrayList<T> getResults() {
        return results;
    }

    public void setResults(ArrayList<T> results) {
        this.results = results;
    }



}
