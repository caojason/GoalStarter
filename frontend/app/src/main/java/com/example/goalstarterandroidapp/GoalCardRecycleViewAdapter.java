package com.example.goalstarterandroidapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goalstarterandroidapp.databinding.GoalCardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoalCardRecycleViewAdapter extends RecyclerView.Adapter<GoalCardRecycleViewAdapter.GoalCardHolder> {
    private static final String LOGTAG = "GOAL CARD RECYCLER VIEW";
    private final Context mContext;
    private final JSONArray mData;
    private final JSONObject mUserInfo;
    private final int mAdapterType; // 0 for feed, 1 for my goals

    // constructor
    public GoalCardRecycleViewAdapter(Context context, JSONArray data, JSONObject userInfo, int adapterType){
        this.mContext = context;
        this.mData = data;
        this.mUserInfo = userInfo;
        this.mAdapterType = adapterType;
    }

    @NonNull
    @Override
    public GoalCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoalCardHolder(LayoutInflater.from(mContext).inflate(R.layout.goal_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GoalCardHolder holder, int position) {
        try {
            holder.bind((JSONObject) mData.get(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mData.length();
    }

    public JSONArray getData(){
        return mData;
    }

    class GoalCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final GoalCardBinding mBinding;

        public GoalCardHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = GoalCardBinding.bind(itemView);
            itemView.setOnClickListener(this);
            mBinding.buttonGoalCardComment.setOnClickListener(this::onClickComment);
        }

        public void bind(JSONObject data) {
            try{
                mBinding.textViewGoalCardUser.setText(data.getString("author"));
                mBinding.textViewGoalCardCategory.setText(data.getString("tag"));
                mBinding.textViewGoalCardGoal.setText(data.getString("title"));
                mBinding.textViewGoalCardDetails.setText(data.getString("content"));
            }
            catch (JSONException e){
                Log.d(LOGTAG, "unable to retrieve required data from json object");
            }

        }

        @Override
        public void onClick(View v) {
            try {
                // get json object
                int mPosition = getAdapterPosition();
                JSONObject goal = (JSONObject) mData.get(mPosition);
                // convert to string
                String argGoal = goal.toString();
                // start the goal detail activity
                Intent intent = new Intent(mContext, GoalDetailActivity.class);
                intent.putExtra("goal", argGoal);
                mContext.startActivity(intent);
            }
            catch (JSONException e){
                e.printStackTrace();
            }

        }

        public void onClickComment(View v){
            try {
                // get json object for goal
                int mPosition = getAdapterPosition();
                JSONObject goal = (JSONObject) mData.get(mPosition);
                // convert to string for sending
                String argGoal = goal.toString();
                // get name of current user
                String username = mUserInfo.getString("name");
                // start the goal detail activity
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("goal", argGoal);
                intent.putExtra("username", username);
                intent.putExtra("position", mPosition);
                intent.putExtra("adapter type", mAdapterType);
                mContext.startActivity(intent);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
