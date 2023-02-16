package com.example.main.f1resultsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.main.f1resultsapp.Fragments.FavouritesFragment;
import com.example.main.f1resultsapp.Models.Driver;
import com.example.main.f1resultsapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.main.f1resultsapp.Fragments.FavouritesFragment.favDriv;

public class DriverListViewAdapter extends ArrayAdapter<Driver> {

    //the driver list that will be displayed
    private List<Driver> driverList;

    //the context object
    private Context mCtx;

    //here we are getting the schedulelist and context
    //so while creating the object of this adapter class we need to give schedulelist and context
    public DriverListViewAdapter(List<Driver> driverList, Context mCtx) {
        super(mCtx, R.layout.driverlist_items, driverList);
        this.driverList = driverList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mRef = database.getReference("");


        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.driverlist_items, null, true);

        //getting text views
        TextView textViewPosition = listViewItem.findViewById(R.id.textViewDriverPosition);
        TextView textViewDriver = listViewItem.findViewById(R.id.textViewDriverDriver);
        TextView textViewConstructor = listViewItem.findViewById(R.id.textViewDriverConstuctor);
        TextView textViewWins = listViewItem.findViewById(R.id.textViewDriverWins);
        TextView textViewPoints = listViewItem.findViewById(R.id.textViewDriverPoints);
        ImageButton imageButtonFavourite = listViewItem.findViewById(R.id.imageButtonFavourite);


        //Getting the driver for the specified position
        final Driver driver = driverList.get(position);

        //setting driver values to textviews
        textViewPosition.setText(driver.getPosition());
        textViewPoints.setText(driver.getPoints());
        textViewDriver.setText(driver.getGivenName() + " " + driver.getFamilyName());
        textViewWins.setText(driver.getWins());
        textViewConstructor.setText(driver.getName());

        imageButtonFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favDriv = FavouritesFragment.JSON_URL + driver.getId() + ".json";
                mRef.child("favDriver").setValue(favDriv).toString();
                Toast.makeText(getContext(),driver.getGivenName() + " " + driver.getFamilyName() + " added To Favourites" , Toast.LENGTH_SHORT).show();
                }
                });

                //returning the listitem
        return listViewItem;
    }

}