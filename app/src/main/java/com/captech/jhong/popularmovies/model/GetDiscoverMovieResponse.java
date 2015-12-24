package com.captech.jhong.popularmovies.model;

import com.captech.jhong.popularmovies.model.Result;
import com.captech.jhong.popularmovies.network.response.NetworkResponse;

import java.util.List;

/**
 * Created by jhong on 12/17/15.
 */
public class GetDiscoverMovieResponse implements NetworkResponse {
    private int page;
    private List<Result> results;
    private int total_pages;
    private int total_results;

    public Integer getPage() {
        return page;
    }

    public List<Result> getResults() {
        return results;
    }

    public Integer getTotalPages() {
        return total_pages;
    }

    public Integer getTotalResults() {
        return total_results;
    }
}
