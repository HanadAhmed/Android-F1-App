package com.example.main.f1resultsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.main.f1resultsapp.Models.Result;
import com.example.main.f1resultsapp.R;

import java.util.List;

public class RaceListViewAdapter extends ArrayAdapter<Result> {

    //the result list that will be displayed
    private List<Result> resultList;

    //the context object
    private Context rmCtx;

    //here we are getting the schedulelist and context
    //so while creating the object of this adapter class we need to give schedulelist and context
    public RaceListViewAdapter(List<Result> resultList, Context rmCtx) {
        super(rmCtx, R.layout.racelist_items, resultList);
        this.resultList = resultList;
        this.rmCtx = rmCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(rmCtx);

        //creating a view with our xml layout
        View resultlistViewItem = inflater.inflate(R.layout.racelist_items, null, true);

        //getting text views
        TextView textViewPosition = resultlistViewItem.findViewById(R.id.textViewPosition);
        TextView textViewDriver = resultlistViewItem.findViewById(R.id.textViewDriver);
        TextView textViewConstructor = resultlistViewItem.findViewById(R.id.textViewConstuctor);
        TextView textViewTime = resultlistViewItem.findViewById(R.id.textViewTime);
        TextView textViewPoints = resultlistViewItem.findViewById(R.id.textViewPoints);


        //Getting the result for the specified position
        Result result = resultList.get(position);

        //setting race values to textviews
        textViewPosition.setText(result.getPosition());
        textViewPoints.setText(result.getPoints());
        textViewDriver.setText(result.getGivenName() + " " + result.getFamilyName());
        textViewConstructor.setText(result.getName());
        textViewTime.setText(result.getTime());

        //returning the listitem
        return resultlistViewItem;
    }
}