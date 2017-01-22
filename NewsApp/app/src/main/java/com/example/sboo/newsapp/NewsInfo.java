package com.example.sboo.newsapp;

/**
 * Created by lgpc on 2017-01-22.
 */

public class NewsInfo {

    public NewsInfo(String title, String section, String linkUrl) {
        this.mTitle = title;
        this.mSection = section;
        this.mLinkUrl = section;
    }

    private String mTitle;
    private String mSection;
    private String mLinkUrl;

    public String getTitle(){ return mTitle; }
    public String getSection(){ return mSection; }
    public String getLinkUrl(){ return mLinkUrl; }

}
