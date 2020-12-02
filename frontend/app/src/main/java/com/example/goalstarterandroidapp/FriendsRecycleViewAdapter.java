package com.example.goalstarterandroidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goalstarterandroidapp.databinding.FriendListBinding;

import org.json.JSONArray;
import org.json.JSONException;

public class FriendsRecycleViewAdapter extends RecyclerView.Adapter<FriendsRecycleViewAdapter.FriendCardHolder>{
    private final Context mContext;
    private final JSONArray mData;

    FriendsRecycleViewAdapter(Context context, JSONArray data){
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public FriendCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsRecycleViewAdapter.FriendCardHolder(LayoutInflater.from(mContext).inflate(R.layout.friend_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendCardHolder holder, int position) {
        try {
            holder.bind((String) mData.get(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mData.length();
    }

    class FriendCardHolder extends RecyclerView.ViewHolder {
        private final FriendListBinding mBinding;

        public FriendCardHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = FriendListBinding.bind(itemView);
        }

        public void bind(String data) {
            mBinding.textViewFriendName.setText(data);
        }
    }
}
