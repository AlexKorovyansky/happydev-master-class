package ru.happydev_lite.masterapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class ForecastActivity extends Activity {

    public static final String FORECAST = "forecast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        Forecast forecast = (Forecast) getIntent().getSerializableExtra(FORECAST);
        displayForecast(forecast);
        setTitle(forecast.getDate("EEEE, dd MMMM"));
    }

    private void displayForecast(Forecast forecast) {
        ImageView icon = (ImageView)findViewById(R.id.forecast_icon);
        Picasso.with(this).load(forecast.getIconUrl()).fit().into(icon);
        Log.i(null, forecast.getIconUrl());
        TextView dayTemp = (TextView)findViewById(R.id.forecast_day_temp);
        TextView nightTemp = (TextView)findViewById(R.id.forecast_night_temp);
        TextView description = (TextView)findViewById(R.id.forecast_description);
        String format = getString(R.string.forecast_day_temp_template);
        Log.i(null, format);
        dayTemp.setText(String.format(getString(R.string.forecast_day_temp_template), forecast.getDayTemp()));
        nightTemp.setText(String.format(getString(R.string.forecast_night_temp_template), forecast.getNightTemp()));
        description.setText(forecast.getDescription());
    }

}
