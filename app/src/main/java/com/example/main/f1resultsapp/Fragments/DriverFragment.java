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
import com.example.main.f1resultsapp.Adapters.DriverListViewAdapter;
import com.example.main.f1resultsapp.Models.Driver;
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

public class DriverFragment extends Fragment {

    //the URL having the json data
    private static final String JSON_URL = "https://ergast.com/api/f1/current/driverStandings.json";

    private RequestQueue vQueue;

    String data = "";

    //listview object
    ListView listView;

    //the driver list where we will store all the driver objects after parsing json
    List<Driver> driverList;

    public DriverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View driverview = inflater.inflate(R.layout.fragment_driver, container, false);

        listView = driverview.findViewById(R.id.driverlistView);
        driverList = new ArrayList<>();
        vQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();

        //this method will fetch and parse the data
        loadDriverList(driverview);

        // Inflate the layout for this fragment
        return driverview;
    }
    private void loadDriverList(View driverview) {
        //getting the progressbar
        final ProgressBar progressBar = driverview.findViewById(R.id.progressBar);

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

                            JSONArray standArray = standtable.getJSONArray("StandingsLists");

                            JSONObject standObj =  standArray.getJSONObject(0);

                            JSONArray resultArray = standObj.getJSONArray("DriverStandings");

                            //System.out.println(resultArray.length());

                            for (int i = 0; i < resultArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject resultObject = resultArray.getJSONObject(i);

                                //System.out.println(resultObject.length());

                                JSONObject driver = resultObject.getJSONObject("Driver");

                                JSONArray constructor = resultObject.getJSONArray("Constructors");

                                JSONObject conObj = constructor.getJSONObject(0);

                                //creating a result object and giving them the values from json object
                                Driver result = new Driver(
                                        resultObject.getString("position"),
                                        resultObject.getString("points"),
                                        resultObject.getString("wins"),
                                        driver.getString("givenName"),
                                        driver.getString("familyName"),
                                        conObj.getString("name"),
                                        driver.getString("driverId"));
                                //System.out.print(driver.getString("driverId"));

                                //adding the result to drivertlist
                                driverList.add(result);


                            }

                            //creating custom adapter object
                            DriverListViewAdapter adapter = new DriverListViewAdapter(driverList, getActivity().getApplicationContext());

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