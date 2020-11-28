package com.example.goalstarterandroidapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MilestoneTouchHelper extends ItemTouchHelper.Callback {
    private static final String TAG = MilestoneTouchHelper.class.getSimpleName();
    private MilestoneRecycleViewAdapter mAdapter;
    private JSONObject mGoal;
    private Context mContext;
    private RequestQueue mQueue;


    public MilestoneTouchHelper(MilestoneRecycleViewAdapter adapter, JSONObject goal, Context context){
        this.mAdapter = adapter;
        this.mGoal = goal;
        this.mContext = context;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags;
        swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//        try {
//            if(viewHolder.getAdapterPosition() == mGoal.getInt("index")){
//
//            }
//            else {
//                swipeFlags = 0;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.d(TAG, "did not get index from goal");
//            swipeFlags = 0;
//        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int swipedIndex = viewHolder.getAdapterPosition();
        int index = -1;
        String goalid = null;
        try {
            goalid = mGoal.getString("id");
            index = mGoal.getInt("index");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(index == -1){
            Log.d(TAG, "failed to get index in onSwiped");
        }
        // user is trying to complete a goal that is already completed
        else if(swipedIndex < index){
            Toast.makeText(mContext, "You have already completed this Milestone", Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();
        }
        // update the goal
        else if (swipedIndex == index){
            String url = "http://52.188.108.13:3000/home/update";
            // make request body
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("id", goalid);
            requestBody.put("index", Integer.toString(index + 1));
            Log.d(TAG, requestBody.toString());
            // instantiate a request
            StringRequest putIndex = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        int newIndex = mGoal.getInt("index") + 1;
                        Log.d(TAG, "updated index: " + newIndex);
                        mGoal.put("index", newIndex);
                        ((MilestoneRecycleViewAdapter.MilestoneViewHolder)viewHolder).complete();
                        mAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "update index put request failed");
                    error.printStackTrace();
                }
            }){
                @Override
                public Map<String, String> getParams(){
                    return requestBody;
                }
            };

            mQueue.add(putIndex);
        }
        // user is trying to complete a milestone before completing the previous ones
        else if(swipedIndex > index){
            Toast.makeText(mContext, "Please complete previous milestones first", Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // We only want the active item
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof MilestoneViewHolder) {
                MilestoneViewHolder itemViewHolder = (MilestoneViewHolder) viewHolder;
                itemViewHolder.onItemSelected();
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof MilestoneViewHolder) {
            MilestoneViewHolder itemViewHolder = (MilestoneViewHolder) viewHolder;
            itemViewHolder.onItemClear();
        }
    }
}
