package com.example.android.popularmovieapp.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.popularmovieapp.FetchMovieAsyncTask;
import com.example.android.popularmovieapp.R;
import com.example.android.popularmovieapp.utilities.OnTaskCompleted;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnMovieClickListener mCallback;
    /**
     * Listener for clicks on movie posters in GridView
     */
    private final GridView.OnItemClickListener moviePosterClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Movie movie = (Movie) parent.getItemAtPosition(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable(getResources().getString(R.string.parcel_movie), movie);
            mCallback.onPostedPosition(bundle);

        }
    };

    // OnMovieClickListener interface, calls a method in the host activity named onPostedPosition
    private GridView mGridView;
    SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(key.equals(getString(R.string.settings_order_by_key)))) {
                // Clear the ListView as a new query will be kicked off
                mGridView = null;
            }
        }
    };

    public MovieFragment() {
        // Required empty public constructor
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnMovieClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        // Inflate the layout for this fragment
        // Obtain a reference to the SharedPreferences file for this app
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // And register to be notified of preference changes
        // So we know when the user has adjusted the query settings
        prefs.registerOnSharedPreferenceChangeListener(listener);

        mGridView = (GridView) rootView.findViewById(R.id.gridview);
        mGridView.setOnItemClickListener(moviePosterClickListener);


        if (savedInstanceState == null) {
            moviesFromApi(getSortOrder());
        } else {
            Parcelable[] parcelables = savedInstanceState.getParcelableArray("PARCEL_MOVIE");
            if (parcelables != null) {
                int numMovieObjects = parcelables.length;
                Movie[] movies = new Movie[numMovieObjects];
                for (int i = 0; i < numMovieObjects; i++) {
                    movies[i] = (Movie) parcelables[i];
                }
                mGridView.setAdapter(new MovieAdapter(getActivity(), movies));
            }
        }
        return rootView;
    }

    private void moviesFromApi(String sortOrder) {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService
                (Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            OnTaskCompleted tastFinish = new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(Movie[] movies) {
                    mGridView.setAdapter(new MovieAdapter(getActivity().getApplicationContext(), movies));
                }
            };
            FetchMovieAsyncTask task = new FetchMovieAsyncTask(tastFinish);
            task.execute(sortOrder);
        }
    }

    private String getSortOrder() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        return orderBy;
    }

    public interface OnMovieClickListener {
        void onPostedPosition(Bundle bundle);
    }
}