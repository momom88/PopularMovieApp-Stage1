package com.example.android.popularmovieapp.utilities;

import com.example.android.popularmovieapp.ui.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marti on 5/10/2018.
 */

public final class OpenMovieJsonUtils {

    private static final String TAG_RESULTS = "results";

    //Movie information
    private static final String TAG_TITLE = "original_title";
    private static final String TAG_POSTER_PATH = "poster_path";
    private static final String TAG_OVERVIEW = "overview";
    private static final String TAG_VOTE_AVERAGE = "vote_average";
    private static final String TAG_RELEASE_DATE = "release_date";

    public static Movie[] moviesDataFromJson(String movieJsonStr) throws JSONException {


        JSONObject jsonObject = new JSONObject(movieJsonStr);
        JSONArray resultArray = jsonObject.getJSONArray(TAG_RESULTS);

        Movie[] movies = new Movie[resultArray.length()];
        for (int i = 0; i < resultArray.length(); i++) {
            movies[i] = new Movie();

            JSONObject infoMovie = resultArray.getJSONObject(i);
            movies[i].importTitle(infoMovie.getString(TAG_TITLE));
            movies[i].importPosterPath(infoMovie.getString(TAG_POSTER_PATH));
            movies[i].importOverview(infoMovie.getString(TAG_OVERVIEW));
            movies[i].importVoteAverage(infoMovie.getDouble(TAG_VOTE_AVERAGE));
            movies[i].importDate(infoMovie.getString(TAG_RELEASE_DATE));
        }
        return movies;
    }
}
