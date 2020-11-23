package com.example.goalstarterandroidapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.goalstarterandroidapp.databinding.ActivityGoalDetailBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class GoalDetailActivity extends AppCompatActivity {
    private static final String TAG = GoalDetailActivity.class.getName();
    private ActivityGoalDetailBinding mBinding;
    private JSONObject mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityGoalDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // set up tool bar
        setSupportActionBar(mBinding.toolbarGoalDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // set up data
        try {
            mData = new JSONObject(getIntent().getStringExtra("goal"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // bind goal card data
        try{
            mBinding.goalCard.textViewGoalCardUser.setText(mData.getString("author"));
            mBinding.goalCard.textViewGoalCardCategory.setText(mData.getString("tag"));
            mBinding.goalCard.textViewGoalCardGoal.setText(mData.getString("title"));
            mBinding.goalCard.textViewGoalCardDetails.setText(mData.getString("content"));
            Log.d(TAG, mData.toString());
        } catch (JSONException e){
            e.printStackTrace();
        }

        // bind milestone recyclerview data
        try {
            Log.d(TAG, mData.get("milestones").getClass().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}