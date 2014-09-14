package ru.happydev_lite.masterapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ViewAnimator;

import java.util.List;


public class DayListActivity extends ListActivity {

    public class RefreshTask extends AsyncTask<Void, Void, List<Forecast>> {

        @Override
        protected List<Forecast> doInBackground(Void... params) {
            SystemClock.sleep(3000);
            return Forecast.makeRandom(7);
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
}
