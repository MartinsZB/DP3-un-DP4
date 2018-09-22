package com.martinszb.dp3undp4;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    private CountDownTimer myTimer;
    private Boolean isRunning = false;
    private TextView typeText;
    private TextView sessionText;
    private TextView compText;
    private TextView counterText;
    private MediaPlayer mp;
    private MediaPlayer audioSagatavoties;
    private MediaPlayer audioUzmanibu;
    private MediaPlayer audioStarts;
    private MediaPlayer audioStop;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            //if (isRunning){
            //    myTimer.cancel();
            //}
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Override back button to cancel existing timer
    @Override
    public void onBackPressed() {
        if (isRunning) {
            myTimer.cancel();
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
        String clockType = extras.getString("EXTRA_TYPE");
        Integer clockSession = extras.getInt("EXTRA_SESSION");
        clockComp = extras.getBoolean("EXTRA_COMPETITION");

        //Initialize TextViews
        typeText = findViewById(R.id.textView8);
        sessionText = findViewById(R.id.textView9);
        compText = findViewById(R.id.textView10);
        counterText = findViewById(R.id.textView11);


        //Check and set if sound is necessary
        if (clockComp) {
            compText.setText(getString(R.string.sound_off));
            compText.setTextColor(Color.RED);
        } else {
            compText.setText(getString(R.string.sound_on));
            //Initialize Sounds
            audioSagatavoties = MediaPlayer.create(getApplicationContext(), R.raw.sagatavoties_mz_2s);
            audioUzmanibu = MediaPlayer.create(getApplicationContext(), R.raw.uzmanibu_mz_2s);
            audioStarts = MediaPlayer.create(getApplicationContext(), R.raw.starts_mz_1s);
            audioStop = MediaPlayer.create(getApplicationContext(), R.raw.stop_mz_1_5s);
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
        if (clockComp) {
            sessionText.setText(getString(R.string.sagatavoties));
        } else {
            sessionText.setText(getString(R.string.sagatavoties));
            audioSagatavoties.start();
        }
    }


    private void startTimer(long time, final long tick, final long shootTime, final int id) {
        myTimer = new CountDownTimer(time, tick) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 == 8 && id == 0) {
                    if (clockComp) {
                        sessionText.setText(getString(R.string.uzmanibu));
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                    } else {
                        sessionText.setText(getString(R.string.uzmanibu));
                        audioUzmanibu.start();
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                    }
                } else if (millisUntilFinished / 1000 == 1 && id == 1) {
                    if (!clockComp) {
                        audioStop.start();
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                    } else {
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                    }
                } else {
                    counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                }
                isRunning = true;
            }

            @Override
            public void onFinish() {
                isRunning = false;
                if (id == 0) {
                    if (clockComp) {
                        sessionText.setText(getString(R.string.starts));
                    } else {
                        sessionText.setText(getString(R.string.starts));
                        audioStarts.start();
                    }
                    startTimer(shootTime, tick, 0, 1);
                } else {
                    sessionText.setText(getString(R.string.stop));
                }

            }
        };
        myTimer.start();
    }

}
