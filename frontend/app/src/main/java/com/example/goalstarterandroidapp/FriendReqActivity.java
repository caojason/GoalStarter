package com.example.goalstarterandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;

public class FriendReqActivity extends AppCompatActivity {

    private static final String FRIENDREQLISTURL = "http://52.188.108.13:3000/home/friendslist/";
    private FriendsReqRecycleViewAdapter mFriendsReqAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_req);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Context currentContext = this;
        String requestURL = FRIENDREQLISTURL + "userid";

        RequestQueue queue = Volley.newRequestQueue(currentContext);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray responseArray = null;
                        try {
                            responseArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mFriendsReqAdapter = new FriendsReqRecycleViewAdapter(currentContext, responseArray);
                        mRecyclerView.setAdapter(mFriendsReqAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR RESPONSE");
            }
        });

        queue.add(stringRequest);

    }
}