package com.martinszb.dp3undp4;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.util.TimeUtils;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.concurrent.TimeUnit;



public class TimerClockActivity extends AppCompatActivity {

    Boolean clockComp;
    CountDownTimer myTimer;
    Boolean isRunning = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            if (isRunning){
                myTimer.cancel();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isRunning){
            myTimer.cancel();
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_clock);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String clockType = extras.getString("EXTRA_TYPE");
        Integer clockSession = extras.getInt("EXTRA_SESSION");
        clockComp = extras.getBoolean("EXTRA_COMPETITION");

        TextView typeText = findViewById(R.id.textView8);
        TextView compText = findViewById(R.id.textView10);
        if (clockComp){
            compText.setText(getString(R.string.sound_off));
            compText.setTextColor(Color.RED);
        }
        else {
            compText.setText(getString(R.string.sound_on));
        }


        if (clockType.equals("DP3") && clockSession == 0){
            typeText.setText(String.format(getString(R.string.piesaudes_serija),clockType));
            initTimer();
            startTimer(60000,1000,60000,0);
        }
        else if (clockType.equals("DP3") && clockSession == 1){
            typeText.setText(String.format(getString(R.string.DP3_ieskaites_serija),clockType));
            initTimer();
            startTimer(60000,1000,300000,0);
        }
        else if (clockType.equals("DP4") && clockSession == 0){
            typeText.setText(String.format(getString(R.string.piesaudes_serija),clockType));
            initTimer();
            startTimer(60000,1000,180000,0);
        }
        else if (clockType.equals("DP4") && clockSession == 1){
            typeText.setText(String.format(getString(R.string.DP4_ieskaites_serija),clockType, clockSession));
            initTimer();
            startTimer(60000,1000,60000,0);
        }
        else if (clockType.equals("DP4") && clockSession == 2){
            typeText.setText(String.format(getString(R.string.DP4_ieskaites_serija),clockType, clockSession));
            initTimer();
            startTimer(60000,1000,30000,0);
        }
        else if (clockType.equals("DP4") && clockSession == 3){
            typeText.setText(String.format(getString(R.string.DP4_ieskaites_serija),clockType, clockSession));
            initTimer();
            startTimer(60000,1000,15000,0);
        }
        else {
        }

    }

    private void initTimer() {
        final TextView sessionText = findViewById(R.id.textView9);
        if (clockComp){
            sessionText.setText(getString(R.string.sagatavoties));
        }
        else {
            sessionText.setText(getString(R.string.sagatavoties));
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sagatavoties);
            mp.start();
        }
    }


    private void startTimer(long time, final long tick, final long shootTime, final int id) {
        final TextView sessionText = findViewById(R.id.textView9);
        final TextView counterText = findViewById(R.id.textView11);
        myTimer = new CountDownTimer(time, tick) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 == 7 && id == 0) {
                    if (clockComp){
                        isRunning = true;
                        sessionText.setText(getString(R.string.uzmanibu));
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                    }
                    else {
                        isRunning = true;
                        sessionText.setText(getString(R.string.uzmanibu));
                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.uzmanibu);
                        mp.start();
                        counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000)));
                    }
                }
                else {
                    isRunning = true;
                    counterText.setText(String.format("%02d:%02d", (millisUntilFinished / 60000),(millisUntilFinished % 60000 / 1000)));
                }
            }

            @Override
            public void onFinish() {
                if (id == 0){
                    if (clockComp){
                        sessionText.setText(getString(R.string.starts));
                        startTimer(shootTime, tick, 0, 1);
                    }
                    else {
                        sessionText.setText(getString(R.string.starts));
                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.starts);
                        mp.start();
                        startTimer(shootTime, tick, 0, 1);
                    }
                }
                else {
                    if (clockComp){
                        sessionText.setText(getString(R.string.stop));
                    }
                    else {
                        sessionText.setText(getString(R.string.stop));
                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.stop);
                        mp.start();
                    }
                }

            }
        };
        myTimer.start();
    }

}
