package com.example.prana.project;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class F extends AppCompatActivity {

    G fg;
    M ob=new M();
    H obj = new H();
    float n;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            read();
            if(n%2!=0)
            {
                handler.postDelayed(this, 5000);
            }
            else
            {
                setContentView(R.layout.activity_b);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                fg = new G(getApplicationContext());
                fg.setBackgroundColor(Color.WHITE);
                setContentView(fg);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNetworkAvailable()) {
            obj.operation();
            ref.child("n").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ob.m= Integer.parseInt(dataSnapshot.child("m").getValue().toString());
                    n=ob.getM();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            if (n % 2 == 0)
            {
                setContentView(R.layout.activity_b);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                fg = new G(this);
                fg.setBackgroundColor(Color.WHITE);
                setContentView(fg);
            }
            else
            {
                handler.postDelayed(runnable, 5000);
            }
        }
        else
        {
            Intent i=new Intent(getApplicationContext(),J.class);
            startActivity(i);
            finish();
        }
    }
    public void onDestroy()
    {
        super.onDestroy();
        I obj=new I();
        obj.operation();
    }
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
    public void read()
    {
        ref.child("n").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ob.m= Integer.parseInt(dataSnapshot.child("m").getValue().toString());
                n=ob.getM();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}