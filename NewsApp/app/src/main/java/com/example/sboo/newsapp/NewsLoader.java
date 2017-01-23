package com.example.sboo.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by lgpc on 2017-01-22.
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsInfo>> {

    private String mSectionName;

    public NewsLoader(Context context, String sectionName) {
        super(context);
        mSectionName = sectionName;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<NewsInfo> loadInBackground() {
        return NewsUtil.returnNewsInfos(mSectionName);
    }
}
