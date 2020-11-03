package com.example.goalstarterandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.goalstarterandroidapp.databinding.ActivityHostBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
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
    private Parcelable mFeedLayoutManager;
    // My Goals fragment data
    private static final String MYGOALSURL = "http://23.99.229.212:3000/home/view_goals/";
    private GoalCardRecycleViewAdapter mMyGoalsAdapter;
    private Parcelable mMyGoalsLayoutManager;
    // Google sign in used for log out
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHostBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mContext = this;

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // set up bottom navigation
        setSupportActionBar(mBinding.toolbarHost);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_feed, R.id.navigation_my_goals, R.id.navigation_profile)
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

    public Parcelable getFeedLayoutManager(){
        return mFeedLayoutManager;
    }

    public void setFeedLayoutManager(Parcelable state){
        mFeedLayoutManager = state;
    }


    public void getMyGoalsAdapter(){
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        MyGoalsFragment myGoalsFragment = (MyGoalsFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);

        int userid = getIntent().getIntExtra("userid", -1);
        String requestURL = MYGOALSURL + Integer.toString(userid); // TODO: add user id

        if (mMyGoalsAdapter == null){
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
            myGoalsFragment.attachAdapter(mMyGoalsAdapter);
        }
    }

    public Parcelable getMyGoalsLayoutManager(){
        return mMyGoalsLayoutManager;
    }

    public void setMyGoalsLayoutManager(Parcelable state){
        mMyGoalsLayoutManager = state;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.host_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_log_out:

                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent loginIntent = new Intent(HostActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                            }
                        });

                return true;

            default:
                System.out.println("ERROR LOGGING OUT");
                return super.onOptionsItemSelected(item);
        }
    }

}