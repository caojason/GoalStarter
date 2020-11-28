package com.example.goalstarterandroidapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goalstarterandroidapp.databinding.FriendListBinding;
import com.example.goalstarterandroidapp.databinding.FriendreqListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendsReqRecycleViewAdapter extends RecyclerView.Adapter<FriendsReqRecycleViewAdapter.FriendReqCardHolder>{
    private static final String LOGTAG = "FRIEND RECYCLER VIEW";
    private final Context mContext;
    private final JSONArray mData;

    FriendsReqRecycleViewAdapter(Context context, JSONArray data){
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public FriendReqCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsReqRecycleViewAdapter.FriendReqCardHolder(LayoutInflater.from(mContext).inflate(R.layout.friendreq_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendReqCardHolder holder, int position) {
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

    class FriendReqCardHolder extends RecyclerView.ViewHolder {
        private final FriendreqListBinding mBinding;

        public FriendReqCardHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = FriendreqListBinding.bind(itemView);
        }

        public void bind(String data) {
            mBinding.textViewFriendreqName.setText(data);
        }
    }
}
