package com.example.android.popularmovieapp;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmovieapp.ui.Movie;
import com.example.android.popularmovieapp.utilities.NetworkUtils;
import com.example.android.popularmovieapp.utilities.OnTaskCompleted;
import com.example.android.popularmovieapp.utilities.OpenMovieJsonUtils;


import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by marti on 5/7/2018.
 */

public class FetchMovieAsyncTask extends AsyncTask<String, Void, Movie[]> {

    // LogTag
    private final String LOG = FetchMovieAsyncTask.class.getSimpleName();

    //listener
    private final OnTaskCompleted mListener;

    public FetchMovieAsyncTask(OnTaskCompleted Listener) {
        mListener = Listener;
    }

    @Override
    protected Movie[] doInBackground(String... strings) {
        URL url = NetworkUtils.buildUrlMovie(strings);

        String movieJsonStr;
        try {
            String response = NetworkUtils.getResponseFromHttpUrl(url);
            movieJsonStr = response;
        } catch (IOException e) {
            Log.e(LOG, "Error build url", e);
            return null;
        }
        try {
            // Make sense of the JSON data
            return OpenMovieJsonUtils.moviesDataFromJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e(LOG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);

        mListener.onTaskCompleted(movies);
    }
}
