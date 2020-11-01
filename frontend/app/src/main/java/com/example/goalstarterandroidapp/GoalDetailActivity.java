package com.example.goalstarterandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.goalstarterandroidapp.databinding.ActivityGoalDetailBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class GoalDetailActivity extends AppCompatActivity {
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

        mBinding.textViewTest.setText(mData.toString());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}