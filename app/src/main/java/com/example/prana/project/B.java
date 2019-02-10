package com.example.prana.project;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.*;
public class B extends AppCompatActivity {

    C fg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        H obj=new H();
        obj.operation();
        setContentView(R.layout.activity_b);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        fg=new C(this);
        fg.setBackgroundColor(Color.WHITE);
        setContentView(fg);
    }
}
