package com.example.main.f1resultsapp;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.main.f1resultsapp.Fragments.FavouritesFragment.favDriv;

public class FirebaseClass extends android.app.Application {

    DatabaseReference databaseReference;

    @Override
    public void onCreate() {
        super.onCreate();
        //Enable disk persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        refreshurl();
    }
    public void refreshurl(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);

        databaseReference.child("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String FavouriteDriv = dataSnapshot.child("favDriver").getValue().toString();
                favDriv = FavouriteDriv;

                System.out.print(favDriv);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
