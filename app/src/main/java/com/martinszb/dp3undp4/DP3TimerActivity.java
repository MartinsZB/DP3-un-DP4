package com.martinszb.dp3undp4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class DP3TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp3_timer);
    }
    public void timerClockDP3_0(View view){
        Intent intent = new Intent(this, TimerClockActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_TYPE", "DP3");
        extras.putInt("EXTRA_SESSION", 0);
        Switch compSwitch = (Switch) findViewById(R.id.switch1);
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
        Switch compSwitch = (Switch) findViewById(R.id.switch1);
        Boolean compState = compSwitch.isChecked();
        extras.putBoolean("EXTRA_COMPETITION", compState);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
