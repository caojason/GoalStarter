package com.example.goalstarterandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goalstarterandroidapp.databinding.FriendListBinding;
import com.example.goalstarterandroidapp.databinding.FriendreqListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class FriendsReqRecycleViewAdapter extends RecyclerView.Adapter<FriendsReqRecycleViewAdapter.FriendReqCardHolder>{
    private static final String LOGTAG = "FRIEND RECYCLER VIEW";
    private final Context mContext;
    private final JSONArray mData;
    private static String userid;

    FriendsReqRecycleViewAdapter(Context context, JSONArray data, String userid){
        this.mContext = context;
        this.mData = data;
        this.userid = userid;
    }

    @NonNull
    @Override
    public FriendReqCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsReqRecycleViewAdapter.FriendReqCardHolder(LayoutInflater.from(mContext).inflate(R.layout.friendreq_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendReqCardHolder holder, int position) {

        try {
            System.out.println("DATA: " + mData.get(position) + "  Postition: " + position);
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
        private RequestQueue mRequestQueue;
        private final String ACCEPTURL = "http://52.188.108.13:3000/home/confirm_requests/" + userid;
        private final String DECLINEURL = "http://52.188.108.13:3000/home/deny_requests/" + userid;

        public FriendReqCardHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = FriendreqListBinding.bind(itemView);
            mRequestQueue = Volley.newRequestQueue(mContext);
        }

        public void bind(String data) {
            mBinding.textViewFriendreqName.setText(data);

            mBinding.acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status = "accept";
                    handleButtonClick(mBinding.textViewFriendreqName.getText().toString(), ACCEPTURL, status);
                }
            });

            mBinding.declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status = "decline";
                    handleButtonClick(mBinding.textViewFriendreqName.getText().toString(), DECLINEURL, status);
                }
            });
        }

        private void handleButtonClick(String email, String url, String status){
            String acceptToast = "Accepted " + email + "'s friend request!";
            String declineToast = "Declined " + email + "'s friend request!";
            JSONObject Data = new JSONObject();
            String requestBody = null;
            try {
                Data.put("email", email);
                Data.put("userid", userid);
                requestBody = Data.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String finalRequestBody = requestBody;
            StringRequest postRequest = new StringRequest(Request.Method.PUT, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            if ("accept".equals(status)) {
                                Toast.makeText(mContext, acceptToast, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, declineToast, Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            error.printStackTrace();
                            Toast.makeText(mContext, "Friend Request Response failed", Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return finalRequestBody == null ? null : finalRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", finalRequestBody, "utf-8");
                        return null;
                    }
                }
            };

            mRequestQueue.add(postRequest);
        }

    }
}
