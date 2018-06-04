package com.example.android.popularmovieapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.android.popularmovieapp.R;

public class MainActivity extends AppCompatActivity implements MovieFragment.OnMovieClickListener {

    /**
     * For logging purposes
     */
    private final String LOG = MainActivity.class.getSimpleName();

    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_details) != null) {
            mTwoPane = true;

        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onPostedPosition(Bundle bundle) {
        if (mTwoPane) {
            if (bundle != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DetailsMovieFragment fragment = new DetailsMovieFragment();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_details, detailsFragment(bundle),getString(R.string.detail_fragment_key))
                        .commit();
            }
        } else {
            Intent intent = new Intent(this, DetailsMovieActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }
    /**
     * Returns a MovieDetailFragment
     *
     * @param bundle Arguments
     * @return MovieDetailFragment
     */
    private DetailsMovieFragment detailsFragment(Bundle bundle) {
        DetailsMovieFragment fragment = new DetailsMovieFragment();

        // If there are some arguments the need to be passed on
        if (bundle != null && !bundle.isEmpty()) {
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
