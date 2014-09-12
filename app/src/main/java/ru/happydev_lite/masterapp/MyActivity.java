package ru.happydev_lite.masterapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        String appSecret = getResources().getString(R.string.app_secret);
        ((TextView)findViewById(R.id.hello)).setText(new String(Base64.decode(appSecret, Base64.NO_PADDING)));
    }

}
