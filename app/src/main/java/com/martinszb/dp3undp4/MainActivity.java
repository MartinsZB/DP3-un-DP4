package com.martinszb.dp3undp4;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.martinszb.dp3undp4.MESSAGE";
    private static final int SETTINGS_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this,SettingsActivity.class));
        return true;
    }

    public void openRulesDP3(View view){
        Intent intent = new Intent(this, ShowRulesActivity.class);
        String message = getString(R.string.dp3_noteikumuTexts);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void openRulesDP4(View view){
        Intent intent = new Intent(this, ShowRulesActivity.class);
        String message = getString(R.string.dp4_noteikumuTexts);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void openTimersDP3(View view){
        Intent intent = new Intent(this, DP3TimerActivity.class);
        startActivity(intent);
    }
    public void openTimersDP4(View view){
        Intent intent = new Intent(this, DP4TimerActivity.class);
        startActivity(intent);
    }
}
