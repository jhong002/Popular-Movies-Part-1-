package com.captech.jhong.popularmovies.model;

import com.captech.jhong.popularmovies.network.ReviewsResult;
import com.captech.jhong.popularmovies.network.response.NetworkResponse;

import java.util.List;

/**
 * Created by jhong on 1/11/16.
 */
public class GetReviewsResponse implements NetworkResponse {
    private int id;
    private List<ReviewsResult> results;
    private int page;
    private int total_pages;
    private int total_results;

    public int getId() {
        return id;
    }

    public List<ReviewsResult> getResults() {
        return results;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public int getTotalResults() {
        return total_results;
    }
}
