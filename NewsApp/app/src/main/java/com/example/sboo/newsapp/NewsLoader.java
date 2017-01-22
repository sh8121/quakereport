package com.example.sboo.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by lgpc on 2017-01-22.
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsInfo>> {

    public NewsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<NewsInfo> loadInBackground() {
        return NewsUtil.returnNewsInfos();
    }
}
