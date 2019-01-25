package com.example.prana.project;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.*;
public class F extends AppCompatActivity {

    G fg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        fg=new G(this);
        fg.setBackgroundColor(Color.WHITE);
        setContentView(fg);
    }
}
