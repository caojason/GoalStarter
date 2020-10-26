package com.example.goalstarterandroidapp;

import android.content.Context;
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

import java.util.ArrayList;

public class GoalCardRecycleViewAdapter extends RecyclerView.Adapter<GoalCardRecycleViewAdapter.GoalCardHolder> {
    private static final String LOGTAG = "GOAL CARD RECYCLER VIEW";
    private Context mContext;
    private JSONArray mData;

    GoalCardRecycleViewAdapter(Context context, JSONArray data){
        this.mContext = context;
        this.mData = data;
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

    class GoalCardHolder extends RecyclerView.ViewHolder {
        private GoalCardBinding mBinding;

        public GoalCardHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = GoalCardBinding.bind(itemView);
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
    }
}