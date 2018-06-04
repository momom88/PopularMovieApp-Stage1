package com.example.android.popularmovieapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.popularmovieapp.R;

public class DetailsMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_container , new DetailsMovieFragment())
                .commit();
    }
}
