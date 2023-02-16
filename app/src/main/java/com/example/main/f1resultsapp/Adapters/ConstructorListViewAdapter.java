package com.example.main.f1resultsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.main.f1resultsapp.Models.Constructor;
import com.example.main.f1resultsapp.R;

import java.util.List;

public class ConstructorListViewAdapter extends ArrayAdapter<Constructor> {

    //the constructor list that will be displayed
    private List<Constructor> constructorList;

    //the context object
    private Context mCtx;

    //here we are getting the schedulelist and context
    //so while creating the object of this adapter class we need to give schedulelist and context
    public ConstructorListViewAdapter(List<Constructor> constructorList, Context mCtx) {
        super(mCtx, R.layout.constructorlist_items, constructorList);
        this.constructorList = constructorList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.constructorlist_items, null, true);

        //getting text views
        TextView textViewPosition = listViewItem.findViewById(R.id.textViewConstructorPosition);
        TextView textViewNationality = listViewItem.findViewById(R.id.textViewConstructorNationality);
        TextView textViewConstructor = listViewItem.findViewById(R.id.textViewConstructor);
        TextView textViewWins = listViewItem.findViewById(R.id.textViewConstructorWins);
        TextView textViewPoints = listViewItem.findViewById(R.id.textViewConstructorPoints);

        //Getting the constructor for the specified position
        Constructor constructor = constructorList.get(position);

        //setting Constructor values to textviews
        textViewPosition.setText(constructor.getPosition());
        textViewPoints.setText(constructor.getPoints());
        textViewConstructor.setText(constructor.getName());
        textViewWins.setText(constructor.getWins());
        textViewNationality.setText(constructor.getNationality());

        //returning the listitem
        return listViewItem;
    }
}