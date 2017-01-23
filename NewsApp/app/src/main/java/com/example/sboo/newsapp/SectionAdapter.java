package com.example.sboo.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by lgpc on 2017-01-22.
 */

public class SectionAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SectionAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        SectionFragment fragment = new SectionFragment();
        Bundle bundle = new Bundle();

        if(position == 0){
            bundle.putString(mContext.getString(R.string.section_bundle_key), mContext.getString(R.string.section_politic_bundle_value));
        }
        else if(position == 1){
            bundle.putString(mContext.getString(R.string.section_bundle_key), mContext.getString(R.string.section_business_bundle_value));
        }
        else if(position == 2){
            bundle.putString(mContext.getString(R.string.section_bundle_key), mContext.getString(R.string.section_society_bundle_value));
        }
        else if(position == 3){
            bundle.putString(mContext.getString(R.string.section_bundle_key), mContext.getString(R.string.section_sport_bundle_value));
        }

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return mContext.getString(R.string.section_politic_name);
        }
        else if(position == 1){
            return mContext.getString(R.string.section_business_name);
        }
        else if(position == 2){
            return mContext.getString(R.string.section_society_name);
        }
        else if(position == 3){
            return mContext.getString(R.string.section_sports_name);
        }

        return "";
    }
}
