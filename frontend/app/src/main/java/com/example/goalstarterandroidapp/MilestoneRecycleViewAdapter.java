package com.example.goalstarterandroidapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

public class MilestoneRecycleViewAdapter extends RecyclerView.Adapter<MilestoneRecycleViewAdapter.MilestoneViewHolder> {
    private final Context mContext;
    private final JSONArray mData;

    public MilestoneRecycleViewAdapter(Context context, JSONArray data){
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public MilestoneRecycleViewAdapter.MilestoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MilestoneRecycleViewAdapter.MilestoneViewHolder holder, int position) {
        // not yet implemented
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MilestoneViewHolder extends RecyclerView.ViewHolder{

        public MilestoneViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
