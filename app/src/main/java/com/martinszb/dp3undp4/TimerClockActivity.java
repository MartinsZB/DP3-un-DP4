package com.martinszb.dp3undp4;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static java.lang.Math.toIntExact;

public class TimerClockActivity extends AppCompatActivity {

    private String clockType;
    private Integer clockSession;
    private Boolean clockReg;
    private CountDownTimer myTimer;
    private Boolean isRunning = false;
    private TextView typeText;
    private TextView sessionText;
    private TextView counterText;
    private MediaPlayer audioSagatavoties;
    private MediaPlayer audioUzmanibu;
    private MediaPlayer audioStarts;
    private MediaPlayer audioStop;
    private MediaPlayer audioIzladet;
    private MediaPlayer audioMinute;
    private Boolean minuteBeep;
    private Button exitButton;
    private MediaRecorder mRecorder;
    private Integer shootCount;
    private TableLayout shootTable;
    private int timerWait;
    private Integer registerLevel;
    private Integer shootDelay;
    private Integer shootTimeMillis;
    // Requesting permission to RECORD_AUDIO
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) {
            clockReg = false;
            shootTable.removeAllViews();
        }

    }



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
        stopRecorder();
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

        exitButton = findViewById(R.id.button5);
        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                initTimer();
            }
        });

        //Getting extras from activity from previous activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        clockType = extras.getString("EXTRA_TYPE");
        clockSession = extras.getInt("EXTRA_SESSION");
        Boolean clockComp = extras.getBoolean("EXTRA_COMPETITION");
        clockReg = extras.getBoolean("EXTRA_REGISTER");



        //Initialize TextViews
        typeText = findViewById(R.id.textView8);
        sessionText = findViewById(R.id.textView9);
        TextView compText = findViewById(R.id.textView10);
        counterText = findViewById(R.id.textView11);

        //Shooting register
        shootTable = findViewById(R.id.ShootTable);
        shootCount = 0;
        if(clockReg){
            //Check permission
            ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        }
        else {
            shootTable.removeAllViews();
        }


        //Read the preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String komanduVeids = prefs.getString(SettingsActivity.KEY_PREF_SOUND_LIST, "balss");
        minuteBeep = prefs.getBoolean(SettingsActivity.KEY_PREF_MINUTE_BEEP, false);
        registerLevel = Integer.parseInt(prefs.getString(SettingsActivity.KEY_PREF_SHOOT_LEVEL, "80"));
        shootDelay = Integer.parseInt(prefs.getString(SettingsActivity.KEY_PREF_SHOOT_DELAY, "100"));

        //Set sounds
        if (komanduVeids.equals("balss")) {
            audioSagatavoties = MediaPlayer.create(getApplicationContext(), R.raw.sagatavoties_mz_2s);
            audioUzmanibu = MediaPlayer.create(getApplicationContext(), R.raw.uzmanibu_mz_2s);
            audioStarts = MediaPlayer.create(getApplicationContext(), R.raw.starts_mz_1s);
            audioStop = MediaPlayer.create(getApplicationContext(), R.raw.stop_mz_1_5s);
            audioIzladet = MediaPlayer.create(getApplicationContext(), R.raw.izladet_mz_2s);
            audioMinute = MediaPlayer.create(getApplicationContext(), R.raw.beep_minute);
        }
        else {
            audioSagatavoties = MediaPlayer.create(getApplicationContext(), R.raw.beep_commands);
            audioUzmanibu = MediaPlayer.create(getApplicationContext(), R.raw.beep_commands);
            audioStarts = MediaPlayer.create(getApplicationContext(), R.raw.beep_commands);
            audioStop = MediaPlayer.create(getApplicationContext(), R.raw.beep_commands);
            audioIzladet = MediaPlayer.create(getApplicationContext(), R.raw.beep_commands);
            audioMinute = MediaPlayer.create(getApplicationContext(), R.raw.beep_minute);
        }

        //Mute sounds if competition or silence in settings
        if (komanduVeids.equals("klusums")) {
            audioSagatavoties.setVolume(0,0);
            audioUzmanibu.setVolume(0,0);
            audioStarts.setVolume(0,0);
            audioStop.setVolume(0,0);
            audioMinute.setVolume(0,0);
            audioIzladet.setVolume(0,0);
            compText.setText(getString(R.string.sound_off_in_settings));
            compText.setTextColor(Color.RED);
        }
        else if (clockComp) {
            audioSagatavoties.setVolume(0,0);
            audioUzmanibu.setVolume(0,0);
            audioStarts.setVolume(0,0);
            audioStop.setVolume(0,0);
            audioMinute.setVolume(0,0);
            audioIzladet.setVolume(0,0);
            compText.setText(getString(R.string.sound_off));
            compText.setTextColor(Color.RED);
        }
        else {
            compText.setText(getString(R.string.sound_on));
        }
        //Set labels for discipline and session
        timerWait = 60000;
        counterText.setText(String.format("%02d:%02d:%01d", timerWait / 60000, timerWait % 60000 / 1000, timerWait % 1000 / 100));
        sessionText.setText(getString(R.string.sagatavoties));
        if (clockType.equals("DP3") && clockSession == 0) {
            //DP3 preparing round
            typeText.setText(String.format(getString(R.string.piesaudes_serija), clockType));
        } else if (clockType.equals("DP3") && clockSession == 1) {
            //DP3 competition round
            typeText.setText(String.format(getString(R.string.DP3_ieskaites_serija), clockType));
        } else if (clockType.equals("DP4") && clockSession == 0) {
            //DP4 preparing round
            typeText.setText(String.format(getString(R.string.piesaudes_serija), clockType));
        } else if (clockType.equals("DP4") && clockSession == 1) {
            //DP4 first competition round
            typeText.setText(String.format(getString(R.string.DP4_ieskaites_serija), clockType, clockSession));
        } else if (clockType.equals("DP4") && clockSession == 2) {
            //DP4 second competition round
            typeText.setText(String.format(getString(R.string.DP4_ieskaites_serija), clockType, clockSession));
        } else if (clockType.equals("DP4") && clockSession == 3) {
            //DP4 third competition round
            typeText.setText(String.format(getString(R.string.DP4_ieskaites_serija), clockType, clockSession));
        }
    }

    private void initTimer() {
        isRunning = true;
        int timerTick = 100;
        audioSagatavoties.start();
        //Set timers for discipline and session
        if (clockType.equals("DP3") && clockSession == 0) {
            //DP3 preparing round
            typeText.setText(String.format(getString(R.string.piesaudes_serija), clockType));
            startTimer(timerWait, timerTick, 60100, 0);
        } else if (clockType.equals("DP3") && clockSession == 1) {
            //DP3 competition round
            typeText.setText(String.format(getString(R.string.DP3_ieskaites_serija), clockType));
            startTimer(timerWait, timerTick, 300100, 0);
        } else if (clockType.equals("DP4") && clockSession == 0) {
            //DP4 preparing round
            typeText.setText(String.format(getString(R.string.piesaudes_serija), clockType));
            startTimer(timerWait, timerTick, 180100, 0);
        } else if (clockType.equals("DP4") && clockSession == 1) {
            //DP4 first competition round
            typeText.setText(String.format(getString(R.string.DP4_ieskaites_serija), clockType, clockSession));
            startTimer(timerWait, timerTick, 60100, 0);
        } else if (clockType.equals("DP4") && clockSession == 2) {
            //DP4 second competition round
            typeText.setText(String.format(getString(R.string.DP4_ieskaites_serija), clockType, clockSession));
            startTimer(timerWait, timerTick, 30100, 0);
        } else if (clockType.equals("DP4") && clockSession == 3) {
            //DP4 third competition round
            typeText.setText(String.format(getString(R.string.DP4_ieskaites_serija), clockType, clockSession));
            startTimer(timerWait, timerTick, 15100, 0);
        }
        exitButton.setText(getString(R.string.Stop_button));
        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                myTimer.cancel();
                restartTimer();
            }
        });
    }

    private void restartTimer(){
        isRunning = false;
        stopRecorder();
        sessionText.setText(getString(R.string.sagatavoties));
        counterText.setText(String.format("%02d:%02d:%01d", timerWait / 60000, timerWait % 60000 / 1000, timerWait % 1000 / 100));
        exitButton.setText(getString(R.string.Start_button));
        shootCount = 0;
        if(clockReg) {
            while (shootTable.getChildCount() > 1) {
                shootTable.removeView(shootTable.getChildAt(shootTable.getChildCount() - 1));
            }
        }
        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                initTimer();
            }
        });
    }


    private void startTimer(final long time, final long tick, final long shootTime, final int id) {

        shootTimeMillis = (int) (long) time;
        myTimer = new CountDownTimer(time, tick) {

            @Override
            public void onTick(long millisUntilFinished) {
                Integer soundVolume = getAmplitude();
                Integer regAmplitude = (32767 * registerLevel)/100;
                if(millisUntilFinished <= (shootTimeMillis-shootDelay) && soundVolume >= regAmplitude) {
                    //shootTimeMillis = toIntExact(millisUntilFinished);
                    shootTimeMillis = (int) (long) millisUntilFinished;
                    String shootTime = String.format("%02d:%02d:%01d", (time - millisUntilFinished) / 60000, (time - millisUntilFinished) % 60000 / 1000, (time - millisUntilFinished) % 1000 / 100);
                    shootCount = shootCount + 1;
                    boolean inTime;
                    if (id == 1) {
                        inTime = true;
                    } else {
                        inTime = false;
                    }
                    addShoot(shootCount.toString(), shootTime, inTime);
                }
                if (clockReg) {
                    if (((((time - millisUntilFinished) / 100) == 4) && (id == 1)) || ((((time - millisUntilFinished) / 100) == 4) && (id == 2))) {
                        startRecorder();
                    }
                }
                if (id == 2) {
                    counterText.setText(String.format("%02d:%02d:%01d", (time - millisUntilFinished) / 60000, (time - millisUntilFinished) % 60000 / 1000, (time - millisUntilFinished) % 1000 /100));
                }
                else {
                    counterText.setText(String.format("%02d:%02d:%01d", (millisUntilFinished / 60000), (millisUntilFinished % 60000 / 1000), (millisUntilFinished % 1000 / 100)));
            // Time is defined in 1/10 seconds, since Tick is 1/10 second - 70 = 7sec
                    if (id ==0 && millisUntilFinished / 100 == 70) {
                        sessionText.setText(getString(R.string.uzmanibu));
                        audioUzmanibu.start();
                    }
                    else if (id == 1 && millisUntilFinished / 100 == 10) {
                        stopRecorder();
                        audioStop.start();
                    }
                    else if (id == 1 && clockType.equals("DP3") && clockSession == 1 && minuteBeep){
                        if (millisUntilFinished / 100 == 2400 || millisUntilFinished / 100 == 1800 || millisUntilFinished / 100 == 1200 || millisUntilFinished / 100 == 600 ) {
                            stopRecorder();
                            audioMinute.start();
                        }
                        if (millisUntilFinished / 100 == 2397 || millisUntilFinished / 100 == 1797 || millisUntilFinished / 100 == 1197 || millisUntilFinished / 100 == 597 ) {
                            startRecorder();
                        }
                    }
                    else if (id == 1 && clockType.equals("DP4") && clockSession == 0 && minuteBeep){
                        if (millisUntilFinished / 100 == 1200 || millisUntilFinished / 100 == 600) {
                            stopRecorder();
                            audioMinute.start();
                        }
                        if (millisUntilFinished /100 == 1197 || millisUntilFinished /100 == 597){
                            startRecorder();
                        }
                    }
                }
            }

            @Override
            public void onFinish() {
                stopRecorder();
                //Time to unload in milliseconds
                int unloadTime = 10000;

                if (id == 0) {
                    sessionText.setText(getString(R.string.starts));
                    counterText.setText("00:00:0");
                    audioStarts.start();
                    startTimer(shootTime, tick, unloadTime, 1);
                }
                 else if (id == 1) {
                    sessionText.setText(getString(R.string.stop));
                    counterText.setText("00:00:0");
                    startTimer(shootTime, tick,0,2);
                }
                else {
                    sessionText.setText(getString(R.string.izladet));
                    counterText.setText(String.format("%02d:%02d:%01d", unloadTime /60000, unloadTime % 60000 /1000, unloadTime % 1000 /100));
                    audioIzladet.start();
                    isRunning = false;
                    exitButton.setText(getString(R.string.Exit_button));
                    exitButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });
                }
            }
        };
        myTimer.start();
    }

    public void startRecorder(){
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile("/dev/null");
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            try
            {
                mRecorder.prepare();
            }catch (java.io.IOException ioe) {
                android.util.Log.e("[Monkey]", "IOException: " +
                        android.util.Log.getStackTraceString(ioe));

            }catch (java.lang.SecurityException e) {
                android.util.Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }
            try
            {
                mRecorder.start();
            }catch (java.lang.SecurityException e) {
                android.util.Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }
        }
    }
    public void stopRecorder() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public Integer getAmplitude() {
        if (mRecorder != null)
            return  mRecorder.getMaxAmplitude();
        else
            return 0;

    }
    private void addShoot(String id, String time, boolean inTime) {
        TableRow shootEntry = new TableRow(this);
        shootEntry.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        TextView shootID= new TextView(this);
        shootID.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        shootID.setPadding(20,0,0,0);

        TextView shootTime= new TextView(this);
        shootTime.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        shootTime.setPadding(10,0,0,0);

        TextView shootRes= new TextView(this);
        shootRes.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        shootRes.setPadding(10,0,0,0);

        if (inTime){
            shootID.setText(id);
            shootEntry.addView(shootID);
            shootTime.setText(time);
            shootEntry.addView(shootTime);
            shootRes.setText(R.string.accept);
            shootEntry.addView(shootRes);
        }
        else {
            shootID.setText(id);
            shootID.setTextColor(Color.argb(255,216,27,96));
            shootEntry.addView(shootID);
            shootTime.setText(time);
            shootTime.setTextColor(Color.argb(255,216,27,96));
            shootEntry.addView(shootTime);
            shootRes.setText(R.string.not_accept);
            shootRes.setTextColor(Color.argb(255,216,27,96));
            shootEntry.addView(shootRes);
        }
        shootTable.addView(shootEntry);
    }

}
