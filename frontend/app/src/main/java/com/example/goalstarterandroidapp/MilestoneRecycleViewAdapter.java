package com.example.goalstarterandroidapp;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MilestoneRecycleViewAdapter extends RecyclerView.Adapter<MilestoneRecycleViewAdapter.MilestoneViewHolder> {
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
