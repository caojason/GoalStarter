package com.example.goalstarterandroidapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goalstarterandroidapp.databinding.GoalCardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GoalCardRecycleViewAdapter extends RecyclerView.Adapter<GoalCardRecycleViewAdapter.GoalCardHolder> {
    private static final String TAG = GoalCardRecycleViewAdapter.class.getSimpleName();
    private final Context mContext;
    private final JSONArray mData;
    private final JSONObject mUserInfo;
    private final int mAdapterType; // 0 for feed, 1 for my goals
    private RequestQueue mQueue;
    private final GoalCardRecycleViewAdapter curAdapter;

    // constructor
    public GoalCardRecycleViewAdapter(Context context, JSONArray data, JSONObject userInfo, int adapterType){
        this.mContext = context;
        this.mData = data;
        this.mUserInfo = userInfo;
        this.mAdapterType = adapterType;
        this.mQueue = Volley.newRequestQueue(mContext);
        this.curAdapter = this;
    }

    @NonNull
    @Override
    public GoalCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoalCardHolder(LayoutInflater.from(mContext).inflate(R.layout.goal_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GoalCardHolder holder, int position) {
        try {
            JSONObject goal = (JSONObject) mData.get(position);
            JSONArray comments = goal.getJSONArray("comments");
            if(comments.length() > 0 && comments.get(0).equals("NULL")){
                comments.remove(0);
            }
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

    // custom view holder class
    class GoalCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final GoalCardBinding mBinding;

        public GoalCardHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = GoalCardBinding.bind(itemView);
            itemView.setOnClickListener(this);
            mBinding.buttonGoalCardComment.setOnClickListener(this::onClickComment);
            mBinding.buttonGoalCardLike.setOnClickListener(this::onClickLike);
        }

        public void bind(JSONObject data) {
            try{
                mBinding.textViewGoalCardUser.setText(data.getString("author"));
                mBinding.textViewGoalCardCategory.setText(data.getString("tag"));
                mBinding.textViewGoalCardGoal.setText(data.getString("title"));
                mBinding.textViewGoalCardDetails.setText(data.getString("content"));
                mBinding.textViewNumOfLikes.setText(data.getInt("likes") + " like(s)");
                mBinding.textViewNumOfComments.setText(data.getJSONArray("comments").length() + " comment(s)");
            }
            catch (JSONException e){
                Log.d(TAG, "unable to retrieve required data from json object");
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
                Log.d(TAG, argGoal);
                intent.putExtra("position", mPosition);
                intent.putExtra("adapter type", mAdapterType);
                ((HostActivity)mContext).startActivityForResult(intent, 1);
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
                // get id of current user
                String userid = mUserInfo.getString("userid");
                // start the goal detail activity
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("goal", argGoal);
                Log.d(TAG, argGoal);
                intent.putExtra("username", username);
                intent.putExtra("userid", userid);
                intent.putExtra("position", mPosition);
                intent.putExtra("adapter type", mAdapterType);
                ((HostActivity)mContext).startActivityForResult(intent, 1);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }

        public void onClickLike(View v){
            Log.d(TAG, "like was clicked");
            try {
                // get number of likes
                int mPosition = getAdapterPosition();
                JSONObject goal = (JSONObject) mData.get(mPosition);
                // get id of current user
                String userid = mUserInfo.getString("userid");
                // url for HTTP request
                String url = "http://52.188.108.13:3000/home/like/" + userid;
                // create request body
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("id", goal.getString("id")); // put id of goal in body
                // make request
                StringRequest increaseLikes = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // increment the amount of likes in the goal
                            int likes = goal.getInt("likes") + 1;
                            goal.put("likes", likes);
                            curAdapter.notifyDataSetChanged();
                            Log.d(TAG, "like put request success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d(TAG, "like put request failed");
                    }
                }){
                    @Override
                    public Map<String, String> getParams(){
                        return requestBody;
                    }
                };

                mQueue.add(increaseLikes);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
