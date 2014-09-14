package ru.happydev_lite.masterapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class ForecastActivity extends Activity {

    public static final String FORECAST = "forecast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        Forecast forecast = (Forecast) getIntent().getSerializableExtra(FORECAST);
        displayForecast(forecast);
    }

    private void displayForecast(Forecast forecast) {
        ImageView icon = (ImageView)findViewById(R.id.forecast_icon);
        TextView temp = (TextView)findViewById(R.id.forecast_temp);
        TextView description = (TextView)findViewById(R.id.forecast_description);
        temp.setText(String.format("%.1f *C", forecast.getDayTemp()));
        description.setText(forecast.getDescription());
    }

}
