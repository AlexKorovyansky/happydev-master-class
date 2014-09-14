package ru.happydev_lite.masterapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;


public class DayListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<Forecast> arrayAdapter = new ArrayAdapter<Forecast>(
                this, android.R.layout.simple_list_item_1, android.R.id.text1,
                Forecast.makeRandom(7)
        );
        setListAdapter(arrayAdapter);
    }



}
