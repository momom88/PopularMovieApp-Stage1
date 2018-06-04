package com.example.android.popularmovieapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovieapp.R;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsMovieFragment extends Fragment {

   private Movie mMovie;


    public DetailsMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details_movie, container, false);

        ImageView posterView = rootView.findViewById(R.id.poster);
        TextView titleView = rootView.findViewById(R.id.title_content);
        TextView releaseDateView = rootView.findViewById(R.id.release_date_content);
        TextView averageView = rootView.findViewById(R.id.vote_average_content);
        TextView overviewView = rootView.findViewById(R.id.overview_content);

        Intent intent = getActivity().getIntent();
        mMovie = intent.getParcelableExtra(getString(R.string.parcel_movie));

        if (mMovie == null){
            Bundle bundle = getArguments();
            mMovie = bundle.getParcelable(getString(R.string.parcel_movie));
        }

        Picasso.with(getActivity())
                .load(mMovie.getmPosterPath())
                .into(posterView);

        titleView.setText(mMovie.getTitle());
        releaseDateView.setText(mMovie.getDate());
        averageView.setText(mMovie.getDetailedVoteAverage());
        overviewView.setText(mMovie.getOverview());
        // Inflate the layout for this fragment
        return rootView;
    }

}
