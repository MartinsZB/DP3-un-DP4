package com.martinszb.dp3undp4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    public static final String KEY_PREF_SOUND_LIST = "command_sound_string";
    public static final String KEY_PREF_MINUTE_BEEP = "minute_beep_boolean";
    public static final String KEY_PREF_SHOOT_REGISTER = "register_shoot_boolean";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
