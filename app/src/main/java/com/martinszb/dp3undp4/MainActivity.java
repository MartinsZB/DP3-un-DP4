package com.martinszb.dp3undp4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.martinszb.dp3undp4.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openRulesDP3(View view){
        Intent intent = new Intent(this, ShowRulesActivity.class);
        String message = getString(R.string.dp3_noteikumuTexts);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void openRulesDP4(View view){
        Intent intent = new Intent(this, ShowRulesActivity.class);
        String message = getString(R.string.dp4_noteikumuTexts);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
