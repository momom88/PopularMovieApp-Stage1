package com.example.android.popularmovieapp.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {
    // URL to Movie Image
    public static final String MOVIE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
    // LogTag
    private static final String LOG = NetworkUtils.class.getSimpleName();
    // Api key Movie database
    private static final String movieApiKey = "";
    // movie URL
    private static final String MOVIE_DB_URL = "http://api.themoviedb.org/3/movie";
    private static final String MOVIE_API = "api_key";

    public static URL buildUrlMovie(String[] parameters) {
        Uri.Builder movieBuilder = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(parameters[0])
                .appendQueryParameter(MOVIE_API, movieApiKey);
        try {
            URL movieBuilderUrl = new URL(movieBuilder.toString());
            Log.i(LOG, "URL: " + movieBuilderUrl);
            return movieBuilderUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if (in == null) {
                return null;
            }
            boolean hasInput = scanner.hasNext();

            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
}

