package com.example.android.quakereport;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by sboo on 2017-01-14.
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuakeInfo>> {

    String[] mUrls;
    public final static String LOG_TAG = EarthquakeLoader.class.getName();

    public EarthquakeLoader(Context context, String... urls) {
        super(context);
        mUrls = urls;
        Log.e(LOG_TAG, "Loader contructor");
    }

    public final static int HTTP_RESPONSE_OK = 200;
    private static final String EARTHQUAKE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.e(LOG_TAG, "LOader onStartLoading");
        forceLoad();
    }

    @Override
    public List<EarthQuakeInfo> loadInBackground() {
        Log.e(LOG_TAG, "loadinBackground");

        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){

        }

        if(mUrls == null || mUrls.length == 0 || mUrls[0] == null){
            return null;
        }

        URL url = createUri(mUrls[0]);
        if(url == null){
            return null;
        }

        List<EarthQuakeInfo> earthQuakeInfos = null;
        try{
            String jsonResponse = makeHttpRequest(url);
            earthQuakeInfos = QueryUtils.extractEarthquakes(jsonResponse);
        }catch (IOException ex){

        }

        return earthQuakeInfos;
    }

    private URL createUri(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }
        catch (MalformedURLException ex){
            Log.e("MainActivity", "EarchQuakeAsyncTaskException");
        }

        return url;
    }

    private String makeHttpRequest(URL url)throws IOException{
        String jsonResponse = null;
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try{
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setReadTimeout(1000);
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == HTTP_RESPONSE_OK){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e("MainActivity", "HttpResponseFail");
            }
        }
        catch (IOException ex){
            Log.e("MainActivity", "IOException");
        }
        finally {
            if(inputStream != null){
                inputStream.close();
                inputStream = null;
            }
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
                httpURLConnection = null;
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream stream) throws IOException{
        StringBuilder builder = new StringBuilder();
        if(stream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line != null){
                builder.append(line);
                line = bufferedReader.readLine();
            }
        }

        return builder.toString();
    }
}
