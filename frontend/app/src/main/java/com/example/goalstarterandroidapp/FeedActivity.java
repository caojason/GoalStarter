package com.example.goalstarterandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goalstarterandroidapp.databinding.ActivityFeedBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {
    private Context mContext = this;
    private static final String LOGTAG  = "FEED ACTIVITY LOG TAG";
    private ActivityFeedBinding mBinding;
    private ArrayList<JSONObject> mFeed;
    private GoalCardRecycleViewAdapter mAdapter;
    private String mFeedUrl = "http://23.99.229.212:3000/home/";
    private RequestQueue mQueue;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.toolbarFeed);

        mBinding.fabFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CreateGoalActivity.class);
                startActivity(intent);
            }
        });


        // firebase test
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
            this.getPreferences(Context.MODE_PRIVATE).edit().putString("fb", newToken).apply();
        });

        Log.d("newToken", this.getPreferences(Context.MODE_PRIVATE).getString("fb", "empty :("));

        mQueue = Volley.newRequestQueue(this);
        mQueue.start();

        // set layout manager for recycler view
        mBinding.recyclerViewFeed.setLayoutManager(new LinearLayoutManager(this));

        // real request code
        // create request
        JsonArrayRequest getFeed = new JsonArrayRequest(Request.Method.GET, mFeedUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mAdapter = new GoalCardRecycleViewAdapter(mContext, response);
                mBinding.recyclerViewFeed.setAdapter(mAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOGTAG, "Did not get JSON array");
                error.printStackTrace();
            }
        });
        // send request
        mQueue.add(getFeed);

        // add padding to the bottom of recycler view
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int)(80 * getResources().getDisplayMetrics().density));
        mBinding.recyclerViewFeed.addItemDecoration(bottomOffsetDecoration);


        // fake feed data
//        mFeed = new ArrayList<>();
//        String[] authors = {
//                "Tamar Mcdermott",
//                "Naima Shaw",
//                "Declan Eastwood",
//                "Samir Cabrera",
//                "Qasim Bryant",
//                "Harlow Burrows",
//                "Dominick Greenaway",
//                "Irving Lamb",
//                "Robyn Wiggins",
//                "Jerry Ahmad"
//        };
//        String[] tags = {
//                "Sports",
//                "Academics",
//                "Arts",
//                "Music",
//                "Video Game",
//                "Cooking",
//                "Health",
//                "Health",
//                "Arts",
//                "Academics"
//        };
//        String[] titles = {
//                "Learn badminton",
//                "Graduate university",
//                "Learn to draw",
//                "Learn piano",
//                "Reach Challenger in Legue",
//                "Learn to bake",
//                "Lose 10 pounds",
//                "Jog every day for a month",
//                "Learn to Paint",
//                "Get into medical school"
//        };
//        JSONObject feedObject;
//        for(int i = 0; i < 10; i++){
//            feedObject = new JSONObject();
//            try{
//                feedObject.put("author", authors[i]);
//                feedObject.put("tag", tags[i]);
//                feedObject.put("title", titles[i]);
//                feedObject.put("content", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi bibendum neque id luctus laoreet. Aenean convallis mattis ullamcorper. Maecenas in ex odio. Integer quis malesuada augue. Praesent eu dignissim ipsum. Vivamus non dui vehicula eros dictum aliquam. Phasellus dolor turpis, posuere at feugiat ut, tincidunt id lacus. Donec sed diam.");
//            }
//            catch (JSONException e){
//                Log.d(LOGTAG, "Exception in creating json objects");
//            }
//            mFeed.add(feedObject);
//        }

        // initialize adapter and add it to recycler view
//         GoalCardRecycleViewAdapter mAdapter = new GoalCardRecycleViewAdapter(this, mFeed);
//         mBinding.recyclerViewFeed.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feed_menu, menu);
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
                                Intent loginIntent = new Intent(FeedActivity.this, MainActivity.class);
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