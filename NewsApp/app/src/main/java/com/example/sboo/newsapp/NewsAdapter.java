package com.example.sboo.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lgpc on 2017-01-22.
 */

public class NewsAdapter extends ArrayAdapter<NewsInfo> {

    Context mContext;

    public NewsAdapter(Context context, int resource, List<NewsInfo> objects) {
        super(context, resource, objects);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView;
        if(convertView != null){
            rootView = convertView;
        }
        else{
            rootView = LayoutInflater.from(mContext).inflate(R.layout.news_list_item, parent, false);
        }
        NewsInfo newsInfo = getItem(position);

        TextView titleTextView = (TextView)rootView.findViewById(R.id.news_title);
        titleTextView.setText(newsInfo.getTitle());

        TextView sectionTextView = (TextView)rootView.findViewById(R.id.news_section);
        sectionTextView.setText(newsInfo.getSection());

        return rootView;
    }
}
