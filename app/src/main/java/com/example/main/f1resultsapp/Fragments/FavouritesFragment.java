package com.example.main.f1resultsapp.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.main.f1resultsapp.Adapters.FavouriteListViewAdapter;
import com.example.main.f1resultsapp.Models.Favourite;
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

public class FavouritesFragment extends Fragment {

    //the URL having the json data
    public static final String JSON_URL = "https://ergast.com/api/f1/drivers/" ;

    public static String favDriv = "";

    DatabaseReference databaseReference;

    private RequestQueue vQueue;
    //listview object
    ListView listView;

    //the Favourite list where we will store all the Favourite objects after parsing json
    List<Favourite> favouriteList;

    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View favview = inflater.inflate(R.layout.fragment_favourites, container, false);

        //System.out.print(favDriv);

        //initializing listview and Constructor list
        listView = favview.findViewById(R.id.favouritelistView);
        favouriteList = new ArrayList<>();
        vQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();

        openClick();

        //this method will fetch and parse the data
        loadFavouriteList(favview);

        return favview;
    }
    private void loadFavouriteList(View favview) {
        //getting the progressbar
        final ProgressBar progressBar = favview.findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);



        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, favDriv,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            System.out.print(JSON_URL);

                            JSONObject mrdat = obj.getJSONObject("MRData");
                            JSONObject standtable = mrdat.getJSONObject("DriverTable");

                            //we have the array named Constructor inside the object
                            //so here we are getting that json array
                            JSONArray driverArray = standtable.getJSONArray("Drivers");

                            //System.out.println(driverArray.length());

                            JSONObject drivObj = driverArray.getJSONObject(0);

                            //JSONArray resultArray = drivObj.getJSONArray("ConstructorStandings");

                            //for (int i = 0; i < resultArray.length(); i++) {
                            //getting the json object of the particular index inside the array
                            //JSONObject resultObject = resultArray.getJSONObject(i);

                            //System.out.println(drivObj.length());

                            //JSONObject constructor = drivObj.getJSONObject("Constructor");

                            //creating a result object and giving them the values from json object
                            Favourite favourite = new Favourite(
                                    drivObj.getString("permanentNumber"),
                                    drivObj.getString("givenName"),
                                    drivObj.getString("familyName"),
                                    drivObj.getString("dateOfBirth"),
                                    drivObj.getString("nationality"),
                                    drivObj.getString("url"));

                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            //databaseReference.keepSynced(true);


                            //adding the Constructor to resultlist
                            favouriteList.add(favourite);

                            //creating custom adapter object
                            FavouriteListViewAdapter adapter = new FavouriteListViewAdapter(favouriteList, getActivity().getApplicationContext());

                            //adding the adapter to listview
                            listView.setAdapter(adapter);

                        } catch (
                                JSONException e)

                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        //adding the string request to request queue
        vQueue.add(stringRequest);
    }
    private void openClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
        @Override
           public void onItemClick(AdapterView<?> parent, View view, int position,long id)
        {
            Favourite favourite = favouriteList.get(position);

            Uri url = Uri.parse(favourite.getUrl());

            Intent inte = new Intent(Intent.ACTION_VIEW, url);
                    startActivity(inte);
        }
    });
}
}

