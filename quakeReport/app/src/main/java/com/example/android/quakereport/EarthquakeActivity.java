/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<EarthQuakeInfo>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String EARTHQUAKE_BASE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query";

    public EarthQuakeAdapter mAdapter;
    public TextView mEmptyTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ListView listView = (ListView)findViewById(R.id.list);
        mEmptyTextView = (TextView)findViewById(R.id.empty_text);
        mProgressBar = (ProgressBar)findViewById(R.id.loading_spinner);
        mAdapter = new EarthQuakeAdapter(this, 0, new ArrayList<EarthQuakeInfo>());
        listView.setAdapter(mAdapter);
        listView.setEmptyView(mEmptyTextView);

        //(new EarthQuakeAsyncTask()).execute(EARTHQUAKE_URL);

        // Create a fake list of earthquake locations.
        //ArrayList<EarthQuakeInfo> earthquakes = QueryUtils.extractEarthquakes();


        // Find a reference to the {@link ListView} in the layout
        //ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
//        EarthQuakeAdapter adapter = new EarthQuakeAdapter(
//                this, 0, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        //earthquakeListView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EarthQuakeInfo earthQuake = (EarthQuakeInfo)parent.getAdapter().getItem(position);
                String url = earthQuake.getWebSiteUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        Log.e(LOG_TAG, "InitLoader called");

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){
            getLoaderManager().initLoader(0, null, this);
        }
        else{
            mProgressBar.setVisibility(View.GONE);
            mEmptyTextView.setText(getString(R.string.no_network));
        }
    }

    @Override
    public android.content.Loader<List<EarthQuakeInfo>> onCreateLoader(int id, Bundle args) {
        Log.e(LOG_TAG, "onCreateLoader");
        SharedPreferences prfs = PreferenceManager.getDefaultSharedPreferences(this);
        String magnitude = prfs.getString(getString(R.string.settings_min_magnitude_key), getString(R.string.settings_min_magnitude_default));

        String orderBy = prfs.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(EARTHQUAKE_BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", magnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);


        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<EarthQuakeInfo>> loader, List<EarthQuakeInfo> data) {
        Log.e(LOG_TAG, "onLoaderFinished");

        mProgressBar.setVisibility(View.GONE);

        mAdapter.clear();
        if(data != null && !data.isEmpty()){
            mAdapter.addAll(data);
        }

        mEmptyTextView.setText(getString(R.string.no_earchquake));
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<EarthQuakeInfo>> loader) {
        Log.e(LOG_TAG, "onLoaderReset");
        mAdapter.clear();
    }

    //    private class EarthQuakeAsyncTask extends AsyncTask<String, Void, List<EarthQuakeInfo>>{
//
//        public final static int HTTP_RESPONSE_OK = 200;
//
//        @Override
//        protected List<EarthQuakeInfo> doInBackground(String... params) {
//            if(params == null || params.length == 0 || params[0] == null){
//                return null;
//            }
//
//            URL url = createUri(params[0]);
//            if(url == null){
//                return null;
//            }
//
//            List<EarthQuakeInfo> earthQuakeInfos = null;
//            try{
//                String jsonResponse = makeHttpRequest(url);
//                earthQuakeInfos = QueryUtils.extractEarthquakes(jsonResponse);
//            }catch (IOException ex){
//
//            }
//
//            return earthQuakeInfos;
//        }
//
//        @Override
//        protected void onPostExecute(List<EarthQuakeInfo> earthQuakeInfos) {
//            super.onPostExecute(earthQuakeInfos);
////            ListView listView = (ListView)EarthquakeActivity.this.findViewById(R.id.list);
////            EarthQuakeAdapter adapter = new EarthQuakeAdapter(getApplicationContext(), 0, earthQuakeInfos);
////            listView.setAdapter(adapter);
//            mAdapter.clear();
//            if(earthQuakeInfos != null && !earthQuakeInfos.isEmpty()){
//                mAdapter.addAll(earthQuakeInfos);
//            }
//        }
//
//        private URL createUri(String stringUrl){
//            URL url = null;
//            try{
//                url = new URL(stringUrl);
//            }
//            catch (MalformedURLException ex){
//                Log.e("MainActivity", "EarchQuakeAsyncTaskException");
//            }
//
//            return url;
//        }
//
//        private String makeHttpRequest(URL url)throws IOException{
//            String jsonResponse = null;
//            if(url == null){
//                return jsonResponse;
//            }
//
//            HttpURLConnection httpURLConnection = null;
//            InputStream inputStream = null;
//
//            try{
//                httpURLConnection = (HttpURLConnection)url.openConnection();
//                httpURLConnection.setReadTimeout(1000);
//                httpURLConnection.setConnectTimeout(1000);
//                httpURLConnection.setRequestMethod("GET");
//                httpURLConnection.connect();
//
//                if(httpURLConnection.getResponseCode() == HTTP_RESPONSE_OK){
//                    inputStream = httpURLConnection.getInputStream();
//                    jsonResponse = readFromStream(inputStream);
//                }
//                else{
//                    Log.e("MainActivity", "HttpResponseFail");
//                }
//            }
//            catch (IOException ex){
//                Log.e("MainActivity", "IOException");
//            }
//            finally {
//                if(inputStream != null){
//                    inputStream.close();
//                    inputStream = null;
//                }
//                if(httpURLConnection != null){
//                    httpURLConnection.disconnect();
//                    httpURLConnection = null;
//                }
//            }
//            return jsonResponse;
//        }
//
//        private String readFromStream(InputStream stream) throws IOException{
//            StringBuilder builder = new StringBuilder();
//            if(stream != null){
//                InputStreamReader inputStreamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String line = bufferedReader.readLine();
//                while(line != null){
//                    builder.append(line);
//                    line = bufferedReader.readLine();
//                }
//            }
//
//            return builder.toString();
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
