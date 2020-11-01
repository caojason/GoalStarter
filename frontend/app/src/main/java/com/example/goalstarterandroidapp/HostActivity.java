package com.example.goalstarterandroidapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.goalstarterandroidapp.databinding.ActivityHostBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;

public class HostActivity extends AppCompatActivity {
    private static final String TAG = HostActivity.class.getName();
    private Context mContext;

    private ActivityHostBinding mBinding;
    private RequestQueue mQueue;
    // Feed fragment data
    private static final String FEEDURL = "http://23.99.229.212:3000/home/";
    private GoalCardRecycleViewAdapter mFeedAdapter;
    // My Goals fragment data
    private static final String MYGOALSURL = "http://23.99.229.212:3000/home/view_goals/";
    private GoalCardRecycleViewAdapter mMyGoalsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHostBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mContext = this;

        // set up bottom navigation
        setSupportActionBar(mBinding.toolbarHost);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_my_goals, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_feed)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        // set up request queue
        mQueue = Volley.newRequestQueue(this);


    }

    public void getFeedAdapter(){
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        FeedFragment feedFragment = (FeedFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);


        if (mFeedAdapter == null){
            JsonArrayRequest getFeed = new JsonArrayRequest(Request.Method.GET, FEEDURL, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    mFeedAdapter = new GoalCardRecycleViewAdapter(mContext, response);
                    feedFragment.attachAdapter(mFeedAdapter);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Did not get JSON array");
                    error.printStackTrace();
                }
            });
            // send request
            mQueue.add(getFeed);
        }
        else{
            feedFragment.attachAdapter(mFeedAdapter);
        }
    }

    public void getMyGoalsAdapter(){
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        MyGoalsFragment myGoalsFragment = (MyGoalsFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);

        String requestURL = MYGOALSURL + ""; // TODO: add user id

        if (mFeedAdapter == null){
            JsonArrayRequest getFeed = new JsonArrayRequest(Request.Method.GET, requestURL, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    mMyGoalsAdapter = new GoalCardRecycleViewAdapter(mContext, response);
                    myGoalsFragment.attachAdapter(mFeedAdapter);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Did not get JSON array");
                    error.printStackTrace();
                }
            });
            // send request
            mQueue.add(getFeed);
        }
        else{
            myGoalsFragment.attachAdapter(mFeedAdapter);
        }
    }

}