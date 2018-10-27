package com.martinszb.dp3undp4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    public static final String KEY_PREF_SOUND_LIST = "command_sound_string";
    public static final String KEY_PREF_MINUTE_BEEP = "minute_beep_boolean";
    public static final String KEY_PREF_SHOOT_REGISTER = "register_shoot_boolean";
    public static final String KEY_PREF_SHOOT_LEVEL = "register_level_int";
    public static final String KEY_PREF_SHOOT_DELAY = "register_delay_millis_int";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
