package com.example.main.f1resultsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.main.f1resultsapp.Models.Schedule;
import com.example.main.f1resultsapp.R;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Schedule> {

    //the schedule list that will be displayed
    private List<Schedule> scheduleList;

    //the context object
    private Context mCtx;

    //here we are getting the schedulelist and context
    //so while creating the object of this adapter class we need to give schedulelist and context
    public ListViewAdapter(List<Schedule> scheduleList, Context mCtx) {
        super(mCtx, R.layout.schedulelist_items, scheduleList);
        this.scheduleList = scheduleList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View schedulelistViewItem = inflater.inflate(R.layout.schedulelist_items, null, true);

        //getting text views
        TextView textViewRound = schedulelistViewItem.findViewById(R.id.textViewRound);
        TextView textViewRaceName = schedulelistViewItem.findViewById(R.id.textViewRaceName);
        TextView textViewCircuit = schedulelistViewItem.findViewById(R.id.textViewCircuit);
        TextView textViewLocation = schedulelistViewItem.findViewById(R.id.textViewLocation);
        TextView textViewDateTime = schedulelistViewItem.findViewById(R.id.textViewDateTime);


        //Getting the schedule for the specified position
        Schedule schedule = scheduleList.get(position);

        //setting schedule values to textviews
        textViewRound.setText(schedule.getRound() + " - ");
        textViewRaceName.setText(schedule.getRaceName());
        textViewCircuit.setText(schedule.getCircuit());
        textViewLocation.setText(schedule.getLocation() + ", " + schedule.getCountry());
        textViewDateTime.setText(schedule.getDate() + "  " + schedule.getTime());

        //returning the listitem
        return schedulelistViewItem;
    }
}