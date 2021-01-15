package com.example.goalstarterandroidapp;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goalstarterandroidapp.databinding.MilestoneBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MilestoneRecycleViewAdapter extends RecyclerView.Adapter<MilestoneRecycleViewAdapter.MilestoneViewHolder> {
    private final Context mContext;
    private final JSONArray mData;
    private final JSONArray mSchedule;
    private final JSONObject mGoal;

    public MilestoneRecycleViewAdapter(Context context, JSONArray data, JSONArray schedule, JSONObject goal){
        this.mContext = context;
        this.mData = data;
        this.mSchedule = schedule;
        this.mGoal = goal;
    }

    @NonNull
    @Override
    public MilestoneRecycleViewAdapter.MilestoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MilestoneViewHolder(LayoutInflater.from(mContext).inflate(R.layout.milestone, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MilestoneRecycleViewAdapter.MilestoneViewHolder holder, int position) {
        try {
            holder.bind(position + 1, mData.getString(position), mSchedule.getString(position)); // position + 1 because humans start counting at 1, not 0
            int index = mGoal.getInt("index");
            if(position < index){
                holder.complete();
            }
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

    class MilestoneViewHolder extends RecyclerView.ViewHolder implements TouchMilestoneViewHolder {
        private final MilestoneBinding mBinding;

        public MilestoneViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mBinding = MilestoneBinding.bind(itemView);
        }

        public void bind(int itemIndex, String content, String date){
            mBinding.textViewMilestoneTitle.setText("Milestone " + Integer.toString(itemIndex) + ":");
            mBinding.textViewMilestoneContent.setText(content);
            mBinding.textViewMilestoneDate.setText("Due date: " + date);
        }

        public void complete(){
            mBinding.textViewMilestoneTitle.setPaintFlags(mBinding.textViewMilestoneTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mBinding.textViewMilestoneContent.setPaintFlags(mBinding.textViewMilestoneContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mBinding.textViewMilestoneDate.setPaintFlags(mBinding.textViewMilestoneDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        public void onItemSelected(){
            mBinding.getRoot().setDragged(true);
        }

        public void onItemClear(){
            mBinding.getRoot().setDragged(false);
        }
    }
}
