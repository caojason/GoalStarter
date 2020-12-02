package com.example.goalstarterandroidapp;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendReqActivity extends AppCompatActivity {

    private static final String FRIENDREQLISTURL = "http://52.188.108.13:3000/home/pending/";
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


        String userInfo = getIntent().getStringExtra("userInfo");
        String userid = null;
        try {
            JSONObject data = new JSONObject(userInfo);
            userid = data.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestURL = FRIENDREQLISTURL + userid;

        RequestQueue queue = Volley.newRequestQueue(currentContext);

        String finalUserid = userid;
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
                        System.out.println("Friend REQ LIST" + responseArray);
                        mFriendsReqAdapter = new FriendsReqRecycleViewAdapter(currentContext, responseArray, finalUserid);
                        mRecyclerView = findViewById(R.id.recycler_view_friendreq_activity);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(currentContext));
                        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int)(16 * getResources().getDisplayMetrics().density));
                        mRecyclerView.addItemDecoration(bottomOffsetDecoration);
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