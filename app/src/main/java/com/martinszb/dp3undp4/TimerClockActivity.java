package com.martinszb.dp3undp4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class TimerClockActivity extends AppCompatActivity {

    private Boolean clockComp;
    private String clockType;
    private Integer clockSession;
    private CountDownTimer myTimer;
    private Boolean isRunning = false;
    private TextView typeText;
    private TextView sessionText;
    private TextView compText;
    private TextView counterText;
    private MediaPlayer audioSagatavoties;
    private MediaPlayer audioUzmanibu;
    private MediaPlayer audioStarts;
    private MediaPlayer audioStop;
    private MediaPlayer audioMinute;
    private Boolean minuteBeep;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Override back button to cancel existing timer
    @Override
    public void onBackPressed() {
        if (isRunning) {
            myTimer.cancel();
            isRunning = false;
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_clock);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //screen on during timer

        //Getting extras from activity from previous activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        clockType = extras.getString("EXTRA_TYPE");
        clockSession = extras.getInt("EXTRA_SESSION");
        clockComp = extras.getBoolean("EXTRA_COMPETITION");

        //Initialize TextViews
        typeText = findViewById(R.id.textView8);
        sessionText = findViewById(R.id.textView9);
        compText = findViewById(R.id.textView10);
        counterText = findViewById(R.id.textView11);

        //Read the preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String komanduVeids = prefs.getString(SettingsActivity.KEY_PREF_SOUND_LIST, "balss");
        minuteBeep = prefs.getBoolean(SettingsActivity.KEY_PREF_MINUTE_BEEP, false);

        //Set sounds
        if (komanduVeids.equals("balss")) {
            audioSagatavoties = MediaPlayer.create(getApplicationContext(), R.raw.sagatavoties_mz_2s);
            audioUzmanibu = MediaPlayer.create(getApplicationContext(), R.raw.uzmanibu_mz_2s);
            audioStarts = MediaPlayer.create(getApplicationContext(), R.raw.starts_mz_1s);
            audioStop = MediaPlayer.create(getApplicationContext(), R.raw.stop_mz_1_5s);
            audioMinute = MediaPlayer.create(getApplicationContext(), R.raw.beep_minute);
        }
        else {
            audioSagatavoties = MediaPlayer.create(getApplicationContext(), R.raw.beep_commands);
            audioUzmanibu = MediaPlayer.create(getApplicationContext(), R.raw.beep_commands);
            audioStarts = MediaPlayer.create(getApplicationContext(), R.raw.beep_commands);
            audioStop = MediaPlayer.create(getApplicationContext(), R.raw.beep_commands);
            audioMinute = MediaPlayer.create(getApplicationContext(), R.raw.beep_minute);
        }

        //Mute sounds if competition or silence in settings
        if (komanduVeids.equals("klusums")) {
            audioSagatavoties.setVolume(0,0);
            audioUzmanibu.setVolume(0,0);
            audioStarts.setVolume(0,0);
            audioStop.setVolume(0,0);
            audioMinute.setVolume(0,0);
            compText.setText(getString(R.string.sound_off_in_settings));
            compText.setTextColor(Color.RED);
        }
        else if (clockComp) {
            audioSagatavoties.setVolume(0,0);
            audioUzmanibu.setVolume(0,0);
            audioStarts.setVolume(0,0);
            audioStop.setVolume(0,0);
            audioMinute.setVolume(0,0);
            compText.setText(getString(R.string.sound_off));
            compText.setTextColor(Color.RED);
        }
        else {
            compText.setText(getString(R.string.sound_on));
        }

        //Set timers for discipline and session
        if (clockType.equals("DP3") && clockSession == 0) {
            //DP3 preparing round
            typeText.setText(String.format(getString(R.string.piesaudes_serija), clockType));
            initTimer();
            startTimer(61000, 1000, 61000, 0);
        } else if (clockType.equals("DP3") && clockSession == 1) {
            //DP3 competition round
            typeText.setText(String.format(getString(R.string.DP3_ieskaites_serija), clockType));
            initTimer();
            startTimer(61000, 1000, 301000, 0);
        } else if (clockType.equals("DP4") && clockSession == 0) {
            //DP4 preparing round
            typeText.setText(String.format(getString(R.string.piesaudes_serija), clockType));
            initTimer();
            startTimer(61000, 1000, 181000, 0);
        } else if (clockType.equals("DP4") && clockSession == 1) {
            //DP4 first competition round
            typeText.setText(String.format(getString(R.string.DP4_ieskaites_serija), clockType, clockSession));
            initTimer();
            startTimer(61000, 1000, 61000, 0);
        } else if (clockType.equals("DP4") && clockSession == 2) {
            //DP4 second competition round
            typeText.setText(String.format(getString(R.string.DP4_ieskaites_serija), clockType, clockSession));
            initTimer();
            startTimer(61000, 1000, 31000, 0);
        } else if (clockType.equals("DP4") && clockSession == 3) {
            //DP4 third competition round
            typeText.setText(String.format(getString(R.string.DP4_ieskaites_serija), clockType, clockSession));
            initTimer();
            startTimer(61000, 1000, 16000, 0);


        }
    }

    private void initTimer() {
        isRunning = true;
        sessionText.setText(getString(R.string.sagatavoties));
        audioSagatavoties.start();
    }


    private void startTimer(long time, final long tick, final long shootTime, final int id) {
        myTimer = new CountDownTimer(time, tick) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 == 8 && id == 0) {
                        sessionText.setText(getString(R.string.uzmanibu));
                        audioUzmanibu.start();
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                }
                else if (millisUntilFinished / 1000 == 1 && id == 1) {
                        audioStop.start();
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                }
                else if (id == 1 && clockType.equals("DP3") && clockSession == 1 && minuteBeep){
                    if (millisUntilFinished / 1000 == 240 || millisUntilFinished / 1000 == 180 || millisUntilFinished / 1000 == 120 || millisUntilFinished / 1000 == 60 ) {
                        audioMinute.start();
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                    }
                    else {
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                    }
                }
                else if (id == 1 && clockType.equals("DP4") && clockSession == 0 && minuteBeep){
                    if (millisUntilFinished / 1000 == 120 || millisUntilFinished / 1000 == 60) {
                        audioMinute.start();
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                    }
                    else {
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                    }
                }
                else {
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                }
            }

            @Override
            public void onFinish() {
                if (id == 0) {
                        sessionText.setText(getString(R.string.starts));
                        audioStarts.start();
                    startTimer(shootTime, tick, 0, 1);
                }
                else {
                    sessionText.setText(getString(R.string.stop));
                    isRunning = false;
                }

            }
        };
        myTimer.start();
    }

}
