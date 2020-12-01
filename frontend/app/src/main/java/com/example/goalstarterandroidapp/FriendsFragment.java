package com.example.goalstarterandroidapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FriendsFragment extends Fragment {

    private static final String FRIENDLISTURL = "http://52.188.108.13:3000/home/friendslist/";
    private FriendsRecycleViewAdapter mFriendsAdapter;
    private RecyclerView mRecyclerView;

    // parent activity
    private HostActivity parentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (HostActivity) getActivity();

        NavHostFragment navHostFragment = (NavHostFragment) parentActivity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        FriendsFragment myFriendsFragment = (FriendsFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);

        String userInfo = parentActivity.getIntent().getStringExtra("userInfo");
        String userid = null;
        try {
            JSONObject data = new JSONObject(userInfo);
            userid = data.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestURL = FRIENDLISTURL + userid;

        RequestQueue queue = Volley.newRequestQueue(parentActivity);

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
                        System.out.println("Friends LIST" + responseArray);
                        mFriendsAdapter = new FriendsRecycleViewAdapter(parentActivity, responseArray);
                        myFriendsFragment.mRecyclerView.setAdapter(mFriendsAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR RESPONSE");
            }
        });

        queue.add(stringRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        // button to view friend requests
        view.findViewById(R.id.fab_friend_req_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FriendReqActivity.class);
                // get user info
                String userInfo = parentActivity.getIntent().getStringExtra("userInfo");
                intent.putExtra("userInfo", userInfo);
                parentActivity.startActivityForResult(intent, 0);
            }
        });

        // button to send friend requests
        view.findViewById(R.id.fab_add_friends_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SendFriendReqActivity.class);
                // get user info
                String userInfo = parentActivity.getIntent().getStringExtra("userInfo");
                intent.putExtra("userInfo", userInfo);
                parentActivity.startActivityForResult(intent, 0);
            }
        });

        // recycler view
        mRecyclerView = view.findViewById(R.id.recycler_view_friends_fragment);
        // set layout manager for recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // add spacing to the bottom of recycler view (16dp)
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int)(16 * getResources().getDisplayMetrics().density));
        mRecyclerView.addItemDecoration(bottomOffsetDecoration);

        return view;
    }

}