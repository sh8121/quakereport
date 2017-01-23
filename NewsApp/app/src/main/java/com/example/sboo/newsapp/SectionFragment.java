package com.example.sboo.newsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgpc on 2017-01-22.
 */

public class SectionFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsInfo>>{

    private NewsAdapter mAdapter;
    private String mSectionName;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSectionName = getArguments().getString(getString(R.string.section_bundle_key));

        View rootView = inflater.inflate(R.layout.fragment_politic, container, false);
        ListView politicListView = (ListView)rootView.findViewById(R.id.politic_listview);
        mProgressBar = (ProgressBar)rootView.findViewById(R.id.politic_progress);
        mAdapter = new NewsAdapter(getActivity(), 0, new ArrayList<NewsInfo>());
        politicListView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);

        return rootView;
    }

    @Override
    public Loader<List<NewsInfo>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getActivity(), mSectionName);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsInfo>> loader, List<NewsInfo> data) {
        mProgressBar.setVisibility(View.GONE);
        mAdapter.clear();
        mAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<NewsInfo>> loader) {
        mAdapter.clear();
    }
}
