package com.example.sboo.newsapp;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lgpc on 2017-01-22.
 */

public class NewsUtil {
    private static String REQ_BASE_URL = "http://content.guardianapis.com/search";
    private static int HTTP_STATUS_OK = 200;

    private static ArrayList<NewsInfo> extractNewsInfos(String jsonStr) throws JSONException{
        ArrayList<NewsInfo> newsInfos = new ArrayList<NewsInfo>();

        JSONObject rootObject = new JSONObject(jsonStr);
        JSONObject response = rootObject.optJSONObject("response");
        JSONArray results = response.optJSONArray("results");

        for(int i = 0;i < results.length(); i++){
            JSONObject result = results.optJSONObject(i);
            String webTitle = result.optString("webTitle");
            String sectionName = result.optString("sectionName");
            String webUrl = result.optString("webUrl");
            newsInfos.add(new NewsInfo(webTitle, sectionName, webUrl));
        }

        return newsInfos;
    }

    private static String createStringUrl(String sectionStr){
        Uri uri = Uri.parse(REQ_BASE_URL);
        Uri.Builder uriBuilder = uri.buildUpon();
        uriBuilder.appendQueryParameter("api-key", "test");
        uriBuilder.appendQueryParameter("q", sectionStr);
        return uriBuilder.toString();
    }

    private static URL createUrl(String strUrl) throws MalformedURLException{
        URL result = new URL(strUrl);
        return result;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        StringBuilder builder = new StringBuilder();
        if(urlConnection.getResponseCode() == HTTP_STATUS_OK){
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(1000);
            urlConnection.setReadTimeout(1000);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while(line != null){
                builder.append(line);
                line = bufferedReader.readLine();
            }
        }

        return builder.toString();
    }

    public static ArrayList<NewsInfo> returnNewsInfos(String sectionStr){
        ArrayList<NewsInfo> result = new ArrayList<NewsInfo>();;
        try{
            String strUrl = createStringUrl(sectionStr);
            URL url = createUrl(strUrl);
            String jsonStr = makeHttpRequest(url);
            result = extractNewsInfos(jsonStr);
        }
        catch (Exception ex){
            ex.printStackTrace();
            result = new ArrayList<NewsInfo>();
        }

        return result;
    }
}
