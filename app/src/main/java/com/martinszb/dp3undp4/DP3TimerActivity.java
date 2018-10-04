package com.martinszb.dp3undp4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

public class DP3TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp3_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void timerClockDP3_0(View view){
        Intent intent = new Intent(this, TimerClockActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_TYPE", "DP3");
        extras.putInt("EXTRA_SESSION", 0);
        Switch compSwitch = findViewById(R.id.switch1);
        Boolean compState = compSwitch.isChecked();
        extras.putBoolean("EXTRA_COMPETITION", compState);
        intent.putExtras(extras);
        startActivity(intent);
    }
    public void timerClockDP3_1(View view){
        Intent intent = new Intent(this, TimerClockActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_TYPE", "DP3");
        extras.putInt("EXTRA_SESSION", 1);
        Switch compSwitch = findViewById(R.id.switch1);
        Boolean compState = compSwitch.isChecked();
        extras.putBoolean("EXTRA_COMPETITION", compState);
        intent.putExtras(extras);
        startActivity(intent);
    }

}
