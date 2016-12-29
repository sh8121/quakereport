package com.example.android.quakereport;

/**
 * Created by sboo on 2016-12-24.
 */
public class EarthQuakeInfo {
    private double mMagnitude;
    private String mLocation;
    private long mDate;
    private String mWebSiteUrl;

    public EarthQuakeInfo(double magnitude, String location, long date, String webSiteUrl) {
        this.mMagnitude = magnitude;
        this.mLocation = location;
        this.mDate = date;
    }

    public long getDate() {
        return mDate;
    }

    public String getLocation() {
        return mLocation;
    }

    public double getMagnitude() {
        return mMagnitude;
    }
}
