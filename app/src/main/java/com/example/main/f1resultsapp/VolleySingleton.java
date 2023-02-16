package com.example.main.f1resultsapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton vInstance;
    private RequestQueue vRequestQueue;

    private VolleySingleton(Context context) {
        vRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (vInstance == null) {
            vInstance = new VolleySingleton(context);
        }

        return vInstance;
    }

    public RequestQueue getRequestQueue() {
        return vRequestQueue;
    }
}
