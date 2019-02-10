package com.example.prana.project;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class H {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    M obj=new M();
    float n;
    public H()
    {
    }
    public void operation()
    {
        ref.child("n").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                obj.m= Integer.parseInt(dataSnapshot.child("m").getValue().toString());
                n=obj.getM();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        n++;
        obj.setM((int) n);
        ref.child("n").setValue(obj);
    }
}
