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
import com.example.main.f1resultsapp.Adapters.ConstructorListViewAdapter;
import com.example.main.f1resultsapp.Models.Constructor;
import com.example.main.f1resultsapp.R;
import com.example.main.f1resultsapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConstructorFragment extends Fragment {

    //the URL having the json data
    private static final String JSON_URL = "https://ergast.com/api/f1/current/constructorStandings.json";

    private RequestQueue vQueue;

    String data = "";

    //listview object
    ListView listView;

    //the Constructor list where we will store all the Constructor objects after parsing json
    List<Constructor> constructorList;


    public ConstructorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View constructorview = inflater.inflate(R.layout.fragment_constructor, container, false);

        //initializing listview and Constructor list
        listView = constructorview.findViewById(R.id.constructorlistView);
        constructorList = new ArrayList<>();
        vQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();

        //this method will fetch and parse the data
        loadConstructorList(constructorview);

        return constructorview;
    }
    private void loadConstructorList(View constructorview) {
        //getting the progressbar
        final ProgressBar progressBar = constructorview.findViewById(R.id.progressBar);

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
                            JSONObject standtable = mrdat.getJSONObject("StandingsTable");

                            //we have the array named Constructor inside the object
                            //so here we are getting that json array
                            JSONArray standArray = standtable.getJSONArray("StandingsLists");

                            JSONObject standObj =  standArray.getJSONObject(0);

                            JSONArray resultArray = standObj.getJSONArray("ConstructorStandings");

                            //System.out.println(resultArray.length());

                            for (int i = 0; i < resultArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject resultObject = resultArray.getJSONObject(i);

                                //System.out.println(resultObject.length());

                                JSONObject constructor = resultObject.getJSONObject("Constructor");

                                //creating a result object and giving them the values from json object
                                Constructor result = new Constructor(
                                        resultObject.getString("position"),
                                        resultObject.getString("points"),
                                        resultObject.getString("wins"),
                                        constructor.getString("name"),
                                        constructor.getString("nationality"));

                                //adding the Constructor to resultlist
                                constructorList.add(result);
                            }

                            //creating custom adapter object
                            ConstructorListViewAdapter adapter = new ConstructorListViewAdapter(constructorList, getActivity().getApplicationContext());

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