package com.example.goalstarterandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.goalstarterandroidapp.databinding.ActivityCommentBinding;

public class CommentActivity extends AppCompatActivity {
    private static final String TAG = CommentActivity.class.getName();
    private ActivityCommentBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // set up tool bar
        setSupportActionBar(mBinding.toolbarComments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}