package com.example.goalstarterandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.goalstarterandroidapp.databinding.ActivityFeedBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {
    private static final String LOGTAG  = "FEED ACTIVITY LOG TAG";
    private ActivityFeedBinding mBinding;
    private ArrayList<JSONObject> mFeed;
    private GoalCardRecycleViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


        // set layout manager for recycler view
        mBinding.recyclerViewFeed.setLayoutManager(new LinearLayoutManager(this));

        // get feed from backend (currently using fake data)
        mFeed = new ArrayList<>();
        String[] authors = {
                "Tamar Mcdermott",
                "Naima Shaw",
                "Declan Eastwood",
                "Samir Cabrera",
                "Qasim Bryant",
                "Harlow Burrows",
                "Dominick Greenaway",
                "Irving Lamb",
                "Robyn Wiggins",
                "Jerry Ahmad"
        };
        String[] tags = {
                "Sports",
                "Academics",
                "Arts",
                "Music",
                "Video Game",
                "Cooking",
                "Health",
                "Health",
                "Arts",
                "Academics"
        };
        String[] titles = {
                "Learn badminton",
                "Graduate university",
                "Learn to draw",
                "Learn piano",
                "Reach Challenger in Legue",
                "Learn to bake",
                "Lose 10 pounds",
                "Jog every day for a month",
                "Learn to Paint",
                "Get into medical school"
        };
        JSONObject feedObject;
        for(int i = 0; i < 10; i++){
            feedObject = new JSONObject();
            try{
                feedObject.put("author", authors[i]);
                feedObject.put("tag", tags[i]);
                feedObject.put("title", titles[i]);
                feedObject.put("content", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi bibendum neque id luctus laoreet. Aenean convallis mattis ullamcorper. Maecenas in ex odio. Integer quis malesuada augue. Praesent eu dignissim ipsum. Vivamus non dui vehicula eros dictum aliquam. Phasellus dolor turpis, posuere at feugiat ut, tincidunt id lacus. Donec sed diam.");
            }
            catch (JSONException e){
                Log.d(LOGTAG, "Exception in creating json objects");
            }
            mFeed.add(feedObject);
        }

        // initialize adapter and add it to recycler view
        GoalCardRecycleViewAdapter mAdapter = new GoalCardRecycleViewAdapter(this, mFeed);
        mBinding.recyclerViewFeed.setAdapter(mAdapter);


    }


}