package ru.happydev_lite.masterapp;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.ViewAnimator;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DaysListActivity extends ListActivity {

    public class RefreshTask extends AsyncTask<Void, Void, List<Forecast>> {

        @Override
        protected List<Forecast> doInBackground(Void... params) {
            List<Forecast> result = new ArrayList<Forecast>();
            try {
                String json = loadJson();
                if (json == null) {
                    return null;
                }
                Response response = new Gson().fromJson(json, Response.class);
                Log.i(null, response.toString());
                for (Response.Forecast forecast : response.forecastList) {
                    result.add(new Forecast(
                            new Date(forecast.dt * 1000),
                            forecast.temp.day,
                            forecast.temp.night,
                            forecast.weatherList.get(0).description,
                            "http://openweathermap.org/img/w/" + forecast.weatherList.get(0).icon + ".png"));
                }
                return result;
            } catch (Exception e) {
                Log.w("WeatherInOmsk", "Error in RefreshTask.doInBackground", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            super.onPostExecute(forecasts);
            if (isCancelled()) {
                return;
            }
            if (forecasts == null) {
                // error
                viewAnimator.setDisplayedChild(2);
            } else {
                List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
                for (Forecast forecast : forecasts) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("forecast", forecast);
                    map.put("day_name", forecast.getDayName());
                    map.put("day_temp", String.format("%.1f°", forecast.getDayTemp()));
                    map.put("night_temp", String.format("%.1f°", forecast.getNightTemp()));
                    map.put("icon", Uri.parse(forecast.getIconUrl()));
                    data.add(map);
                }
                String[] from = new String[]{"day_name", "day_temp", "night_temp", "icon"};
                int[] to = new int[]{R.id.days_list_item_day_name, R.id.days_list_item_day_temp, R.id.days_list_item_night_temp, R.id.days_list_item_icon};
                SimpleAdapter arrayAdapter = new SimpleAdapter(DaysListActivity.this,
                        data,
                        R.layout.days_list_item, from, to);
                arrayAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data, String textRepresentation) {
                        if (view instanceof ImageView && data instanceof Uri) {
                            ImageView imageView = (ImageView) view;
                            Uri uri = (Uri) data;
                            Picasso.with(DaysListActivity.this).load(uri).fit().into(imageView);
                            return true;
                        }
                        return false;
                    }
                });
                setListAdapter(arrayAdapter);
                viewAnimator.setDisplayedChild(0);
            }
        }
    }

    private ViewAnimator viewAnimator;
    private RefreshTask refreshTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_list);
        setTitle(R.string.title_day_list_activity);
        viewAnimator = (ViewAnimator) findViewById(R.id.day_list_animator);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.daily, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getListAdapter() != null && getListAdapter().getCount() > 0) {
            return;
        }
        refresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            refresh();
            return true;
        }
        return false;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Map<String, Object> map = (Map<String, Object>) getListAdapter().getItem(position);
        Forecast forecast = (Forecast) map.get("forecast");
        Intent intent = new Intent(DaysListActivity.this, ForecastActivity.class);
        intent.putExtra(ForecastActivity.FORECAST, forecast);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (refreshTask != null) {
            refreshTask.cancel(true);
        }
    }

    private void refresh() {
        if (refreshTask != null && !refreshTask.isCancelled()) {
            refreshTask.cancel(true);
        }
        refreshTask = new RefreshTask();
        refreshTask.execute();
        viewAnimator.setDisplayedChild(1);
    }

    private String loadJson() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=Omsk&mode=json&units=metric&cnt=7&lang=en");

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                forecastJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                forecastJsonStr = null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            forecastJsonStr = null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return forecastJsonStr;
    }
}
