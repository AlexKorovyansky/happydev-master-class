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
        setTitle(forecast.dateName());
    }

    private void displayForecast(Forecast forecast) {
        ImageView icon = (ImageView)findViewById(R.id.forecast_icon);
        Picasso.with(this).load(forecast.getIconUrl()).fit().into(icon);
        Log.i(null, forecast.getIconUrl());
        TextView temp = (TextView)findViewById(R.id.forecast_temp);
        TextView description = (TextView)findViewById(R.id.forecast_description);
        temp.setText(String.format("%.1f *C", forecast.getDayTemp()));
        description.setText(forecast.getDescription());
    }

}
