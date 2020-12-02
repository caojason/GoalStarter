package com.example.goalstarterandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.goalstarterandroidapp.databinding.ActivityGoalDetailBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoalDetailActivity extends AppCompatActivity {
    private static final String TAG = GoalDetailActivity.class.getSimpleName();
    private JSONObject mGoal;
    private int position;
    private int source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGoalDetailBinding mBinding;
        mBinding = ActivityGoalDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // get args from intent
        position = getIntent().getIntExtra("position", -1);
        source = getIntent().getIntExtra("adapter type", -1); // 0 for feed, 1 for my goals

        // set up tool bar
        setSupportActionBar(mBinding.toolbarGoalDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // set up data
        try {
            mGoal = new JSONObject(getIntent().getStringExtra("goal"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // bind goal card data
        try{
            mBinding.goalCard.textViewGoalCardUser.setText(mGoal.getString("author"));
            mBinding.goalCard.textViewGoalCardCategory.setText(mGoal.getString("tag"));
            mBinding.goalCard.textViewGoalCardGoal.setText(mGoal.getString("title"));
            mBinding.goalCard.textViewGoalCardDetails.setText(mGoal.getString("content"));
            mBinding.goalCard.textViewNumOfLikes.setText(mGoal.getInt("likes") + " like(s)");
            mBinding.goalCard.textViewNumOfComments.setText(mGoal.getJSONArray("comments").length() + " comment(s)");
            mBinding.goalCard.constraintLayoutButtons.setVisibility(View.GONE);
//            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(16, 16 ,16, 16);
//            mBinding.goalCard.constraintLayoutNumOfLikesAndComements.setLayoutParams(params);
            // set bottom margin programmatically to 16 dp
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mBinding.goalCard.constraintLayoutNumOfLikesAndComements.getLayoutParams();
            params.bottomMargin = (int)(16 * getResources().getDisplayMetrics().density);
            mBinding.goalCard.constraintLayoutNumOfLikesAndComements.setLayoutParams(params);

            Log.d(TAG, mGoal.toString());
        } catch (JSONException e){
            e.printStackTrace();
        }

        // bind milestone recyclerview data
        mBinding.recyclerViewGoalDetail.setLayoutManager(new LinearLayoutManager(this));
        try {
            JSONArray milestones = mGoal.getJSONArray("milestones");
            JSONArray schedule = mGoal.getJSONArray("schedule");
            mBinding.recyclerViewGoalDetail.setAdapter(new MilestoneRecycleViewAdapter(this, milestones, schedule, mGoal));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // add spacing to the bottom of recycler view (16dp)
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int)(10 * getResources().getDisplayMetrics().density));
        mBinding.recyclerViewGoalDetail.addItemDecoration(bottomOffsetDecoration);

        // if goal is from the my goals page, user can complete them
        if(source == 1){
            ItemTouchHelper helper = new ItemTouchHelper(new MilestoneTouchHelper((MilestoneRecycleViewAdapter) mBinding.recyclerViewGoalDetail.getAdapter(), mGoal, this));
            helper.attachToRecyclerView(mBinding.recyclerViewGoalDetail);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        // organize data to return to parent activity
        Intent intent = new Intent();
        intent.putExtra("goal", mGoal.toString());
        intent.putExtra("adapter type", source);
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        // go back to parent activity
        finish();
    }
}