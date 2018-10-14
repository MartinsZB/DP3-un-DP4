package com.martinszb.dp3undp4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class DP3TimerActivity extends AppCompatActivity {

    private Switch competition;
    private Switch register;
    private Boolean counterStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp3_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Read the preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        counterStatus = prefs.getBoolean(SettingsActivity.KEY_PREF_SHOOT_REGISTER, false);

        competition = (Switch)findViewById(R.id.switch1);
        register = (Switch)findViewById(R.id.switch3);
        register.setChecked(counterStatus);
        competition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    register.setChecked(false);
                    register.setTextColor(Color.GRAY);
                    register.setEnabled(false);
                }
                else{
                    register.setEnabled(true);
                    register.setChecked(counterStatus);
                    register.setTextColor(Color.argb(255,216,27,96));
                }
            }
        });

    }

    public void timerClockDP3_0(View view){
        Intent intent = new Intent(this, TimerClockActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_TYPE", "DP3");
        extras.putInt("EXTRA_SESSION", 0);
        Boolean compState = competition.isChecked();
        extras.putBoolean("EXTRA_COMPETITION", compState);
        Boolean regState = register.isChecked();
        extras.putBoolean("EXTRA_REGISTER", regState);
        intent.putExtras(extras);
        startActivity(intent);
    }
    public void timerClockDP3_1(View view){
        Intent intent = new Intent(this, TimerClockActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_TYPE", "DP3");
        extras.putInt("EXTRA_SESSION", 1);
        Boolean compState = competition.isChecked();
        extras.putBoolean("EXTRA_COMPETITION", compState);
        Boolean regState = register.isChecked();
        extras.putBoolean("EXTRA_REGISTER", regState);
        intent.putExtras(extras);
        startActivity(intent);
    }

}
