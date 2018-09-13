package com.example.prana.project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        abc();
    }
    public void abc()
    {
     Button b=findViewById(R.id.b);
    b.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProgressBar pb=findViewById(R.id.pb);
            pb.setVisibility(View.VISIBLE);
            Intent i=new Intent(getApplicationContext(),B.class);
            startActivity(i);
        }
    });
    Button ex=findViewById(R.id.ex);
        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
}
