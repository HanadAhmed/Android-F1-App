package com.example.main.f1resultsapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.main.f1resultsapp.Adapters.ListViewAdapter;
import com.example.main.f1resultsapp.Models.Schedule;
import com.example.main.f1resultsapp.R;
import com.example.main.f1resultsapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ScheduleFragment extends Fragment {

    //the URL having the json data
    private static final String JSON_URL = "https://ergast.com/api/f1/current.json";

    private RequestQueue vQueue;

    //listview object
    ListView listView;

    //the schedule list where we will store all the schedule objects after parsing json
    List<Schedule> scheduleList;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        //initializing listview and schedule list
        listView = view.findViewById(R.id.schedulelistView);
        scheduleList = new ArrayList<>();

        //creating a request queue
        vQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();

            //this method will fetch and parse the data
            loadScheduleList(view);

            return view;
    }

    private void loadScheduleList(View view) {

        //getting the progressbar
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            JSONObject mrdat = obj.getJSONObject("MRData");
                            JSONObject racetable = mrdat.getJSONObject("RaceTable");

                            JSONArray raceArray = racetable.getJSONArray("Races");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < raceArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject raceObject = raceArray.getJSONObject(i);

                                JSONObject circuit = raceObject.getJSONObject("Circuit");

                                JSONObject location = circuit.getJSONObject("Location");

                                //creating a schedule object and giving them the values from json object
                                Schedule schedule = new Schedule(
                                        raceObject.getString("round"),
                                        raceObject.getString("raceName"),
                                        circuit.getString("circuitName"),
                                        location.getString("locality"),
                                        location.getString("country"),
                                        raceObject.getString("date"),
                                        raceObject.getString("time"));

                                //adding the schedule to schedulelist
                                scheduleList.add(schedule);

                            }

                            //creating custom adapter object
                            ListViewAdapter adapter = new ListViewAdapter(scheduleList, getActivity().getApplicationContext());

                            //adding the adapter to listview
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //adding the string request to request queue
        vQueue.add(stringRequest);
    }
}