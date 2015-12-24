package com.captech.jhong.popularmovies.model;

import java.util.List;

/**
 * Created by jhong on 12/17/15.
 */
public class Result {
    private boolean adult;
    private String backdrop_path;
    private List<Integer> genre_ids;
    private int id;
    private String original_language;
    private String original_title;
    private String overview;
    private String release_date;
    private String poster_path;
    private float popularity;
    private String title;
    private boolean video;
    private float vote_average;
    private int vote_count;

    public String getBackdropPath() {
        return backdrop_path;
    }

    public List<Integer> getGenreIds() {
        return genre_ids;
    }

    public int getId() {
        return id;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public String getOverview() {
        return overview;
    }


    public String getPosterPath() {
        return poster_path;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public float getVoteAverage() {
        return vote_average;
    }

    public int getVoteCount() {
        return vote_count;
    }
    public boolean isVideo() {
        return video;
    }
}
