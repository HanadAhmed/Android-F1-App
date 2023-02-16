package com.example.main.f1resultsapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.main.f1resultsapp.Models.Favourite;
import com.example.main.f1resultsapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.main.f1resultsapp.Fragments.FavouritesFragment.favDriv;

public class FavouriteListViewAdapter extends ArrayAdapter<Favourite> {

    //the favourite list that will be displayed
    private List<Favourite> favouriteList;

    DatabaseReference databaseReference;

//    String PerNum;
//    String DriNam;
//    String DriNat;
//    String DriDob;

    //the context object
    private Context mCtx;

    //here we are getting the schedulelist and context
    //so while creating the object of this adapter class we need to give schedulelist and context
    public FavouriteListViewAdapter(List<Favourite> favouriteList, Context mCtx) {
        super(mCtx, R.layout.favouritelist_items, favouriteList);
        this.favouriteList = favouriteList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference fRef = database.getReference("");

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.favouritelist_items, null, true);

        //getting text views
        TextView textViewName = listViewItem.findViewById(R.id.textViewDriverName);
        TextView textViewNumber = listViewItem.findViewById(R.id.textViewNumber);
        TextView textViewNationality = listViewItem.findViewById(R.id.textViewNationality);
        TextView textViewDateOfBirth = listViewItem.findViewById(R.id.textViewDOB);

        //Getting the Favourite for the specified position
        Favourite favourite = favouriteList.get(position);

        fRef.child("perNum").setValue(favourite.getPermanentNumber() + " - ").toString();
        fRef.child("driNam").setValue(favourite.getGivenName() + favourite.getFamilyName()).toString();
        fRef.child("driNat").setValue(favourite.getNationality()).toString();
        fRef.child("driDob").setValue(favourite.getDateOfBirth()).toString();


//        databaseReference.child("").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                PerNum = dataSnapshot.child("perNum").getValue().toString();
//                //textViewNumber.setText(PerNum);
//                DriNam = dataSnapshot.child("perNum").getValue().toString();
//                //textViewName.setText(DriNam);
//                DriNat = dataSnapshot.child("driNat").getValue().toString();
//                //textViewNationality.setText(DriNat);
//                DriDob = dataSnapshot.child("driDob").getValue().toString();
//                //textViewDateOfBirth.setText(DriDob);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

        //setting Favourite values to textviews
        textViewNumber.setText(favourite.getPermanentNumber() + " - ");
        textViewName.setText(favourite.getGivenName() + " " + favourite.getFamilyName());
        textViewNationality.setText(favourite.getNationality());
        textViewDateOfBirth.setText(favourite.getDateOfBirth());

//        textViewNumber.setText(PerNum);
//        textViewName.setText(DriNam);
//        textViewNationality.setText(DriNat);
//        textViewDateOfBirth.setText(DriDob);



        //returning the listitem
        return listViewItem;
    }
}