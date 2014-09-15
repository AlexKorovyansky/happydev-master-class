package ru.happydev_lite.masterapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ViewAnimator;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DayListActivity extends ListActivity {

    public class RefreshTask extends AsyncTask<Void, Void, List<Forecast>> {

        @Override
        protected List<Forecast> doInBackground(Void... params) {
            List<Forecast> result = new ArrayList<Forecast>();
            String json = loadJson();
            Response response = new Gson().fromJson(json, Response.class);
            Log.i(null, response.toString());
            for (Response.Forecast forecast: response.forecastList) {
                result.add(new Forecast(
                        new Date(forecast.dt * 1000),
                        forecast.temp.day,
                        forecast.temp.night,
                        forecast.weatherList.get(0).description,
                        "http://openweathermap.org/img/w/" + forecast.weatherList.get(0).icon + ".png"));
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            super.onPostExecute(forecasts);
            if (isCancelled()) {
                return;
            }
            ArrayAdapter<Forecast> arrayAdapter = new ArrayAdapter<Forecast>(
                    DayListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1,
                    forecasts
            );
            setListAdapter(arrayAdapter);
            viewAnimator.setDisplayedChild(0);
        }
    }

    private ViewAnimator viewAnimator;
    private RefreshTask refreshTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_list);
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
        Forecast forecast = (Forecast) getListAdapter().getItem(position);
        Intent intent = new Intent(DayListActivity.this, ForecastActivity.class);
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
