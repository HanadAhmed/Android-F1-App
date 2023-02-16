package com.example.main.f1resultsapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.main.f1resultsapp.Adapters.RaceListViewAdapter;
import com.example.main.f1resultsapp.Models.Result;
import com.example.main.f1resultsapp.R;
import com.example.main.f1resultsapp.VolleySingleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.main.f1resultsapp.Fragments.FavouritesFragment.favDriv;

public class RaceFragment extends Fragment {

    //the URL having the json data
    private static final String JSON_URL = "https://ergast.com/api/f1/current/last/results.json";

    private RequestQueue vQueue;

    String data = "";

    //listview object
    ListView listView;

    //the result list where we will store all the result objects after parsing json
    List<Result> resultList;


    public RaceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View raceview = inflater.inflate(R.layout.fragment_race, container, false);

        //initializing listview and race list
        listView = raceview.findViewById(R.id.racelistView);
        resultList = new ArrayList<>();
        vQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();

        //this method will fetch and parse the data
        loadResultList(raceview);
        // Inflate the layout for this fragment
        return raceview;
    }

    private void loadResultList(View raceview) {
        //getting the progressbar
        final ProgressBar progressBar = raceview.findViewById(R.id.progressBar);

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

                            JSONObject raceobj =  raceArray.getJSONObject(0);

                            JSONArray resultarray = raceobj.getJSONArray("Results");

                            //System.out.println(resultarray.length());

                            for (int i = 0; i < resultarray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject resultObject = resultarray.getJSONObject(i);

                                //System.out.println(resultObject.length());

                                JSONObject driver = resultObject.getJSONObject("Driver");

                                JSONObject constructor = resultObject.getJSONObject("Constructor");

                                if (resultObject.has("Time")) {
                                    JSONObject time = resultObject.getJSONObject("Time");
                                    data = time.getString("time");
                                } else {
                                    data = resultObject.getString("status");
                                }

                                //creating a result object and giving them the values from json object
                                Result result = new Result(
                                        resultObject.getString("position"),
                                        resultObject.getString("points"),
                                        resultObject.getString("grid"),
                                        driver.getString("givenName"),
                                        driver.getString("familyName"),
                                        constructor.getString("name"),
                                        (data));

                                //adding the result to resultlist
                                resultList.add(result);
                            }

                            //creating custom adapter object
                            RaceListViewAdapter adapter = new RaceListViewAdapter(resultList, getActivity().getApplicationContext());

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
