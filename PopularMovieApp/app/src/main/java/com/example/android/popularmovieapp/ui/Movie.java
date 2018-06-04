package com.example.android.popularmovieapp.ui;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.popularmovieapp.utilities.NetworkUtils;

/**
 * Created by marti on 5/5/2018.
 */

public class Movie implements Parcelable{
    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private Double mVoteAverage;
    private String mDate;

    /**
     * Constructor for a movie object
     */
    public Movie() {
    }

    public void importTitle(String originalTitle){
        mTitle = originalTitle;
    }

    public void importPosterPath(String posterPath){
        mPosterPath = posterPath;
    }

    public void importOverview(String overview){
        mOverview = overview;
    }

    public void importVoteAverage(Double voteAverage){
        mVoteAverage = voteAverage;
    }

    public void importDate(String releaseDate){
            mDate = releaseDate;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getmPosterPath(){

        return NetworkUtils.MOVIE_IMAGE_URL + mPosterPath;
    }

    public String getOverview(){
        return mOverview;
    }

    public Double getVoteAverage(){
        return mVoteAverage;
    }

    public String getDate(){
        return mDate;
    }

    public String getDetailedVoteAverage() {
        return String.valueOf(getVoteAverage()) + "/10";
    }


    protected Movie(Parcel in) {
        mTitle = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        if (in.readByte() == 0) {
            mVoteAverage = null;
        } else {
            mVoteAverage = in.readDouble();
        }
        mDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        if (mVoteAverage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mVoteAverage);
        }
        dest.writeString(mDate);
    }
}
