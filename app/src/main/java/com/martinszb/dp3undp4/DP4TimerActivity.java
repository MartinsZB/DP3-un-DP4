package com.martinszb.dp3undp4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

public class DP4TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp4_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void timerClockDP4_0(View view) {
        Intent intent = new Intent(this, TimerClockActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_TYPE", "DP4");
        extras.putInt("EXTRA_SESSION", 0);
        Switch compSwitch = (Switch) findViewById(R.id.switch2);
        Boolean compState = compSwitch.isChecked();
        extras.putBoolean("EXTRA_COMPETITION", compState);
        intent.putExtras(extras);
        startActivity(intent);
    }
    public void timerClockDP4_1(View view) {
        Intent intent = new Intent(this, TimerClockActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_TYPE", "DP4");
        extras.putInt("EXTRA_SESSION", 1);
        Switch compSwitch = (Switch) findViewById(R.id.switch2);
        Boolean compState = compSwitch.isChecked();
        extras.putBoolean("EXTRA_COMPETITION", compState);
        intent.putExtras(extras);
        startActivity(intent);
    }
    public void timerClockDP4_2(View view) {
        Intent intent = new Intent(this, TimerClockActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_TYPE", "DP4");
        extras.putInt("EXTRA_SESSION", 2);
        Switch compSwitch = (Switch) findViewById(R.id.switch2);
        Boolean compState = compSwitch.isChecked();
        extras.putBoolean("EXTRA_COMPETITION", compState);
        intent.putExtras(extras);
        startActivity(intent);
    }
    public void timerClockDP4_3(View view) {
        Intent intent = new Intent(this, TimerClockActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_TYPE", "DP4");
        extras.putInt("EXTRA_SESSION", 3);
        Switch compSwitch = (Switch) findViewById(R.id.switch2);
        Boolean compState = compSwitch.isChecked();
        extras.putBoolean("EXTRA_COMPETITION", compState);
        intent.putExtras(extras);
        startActivity(intent);
    }

}
