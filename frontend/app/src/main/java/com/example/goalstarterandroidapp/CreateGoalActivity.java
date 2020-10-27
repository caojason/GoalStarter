package com.example.goalstarterandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goalstarterandroidapp.databinding.ActivityCreateGoalBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateGoalActivity extends AppCompatActivity {
    private static final String TAG = "CREATE GOAL LOG TAG";
    private ActivityCreateGoalBinding mBinding;
    private RequestQueue mRequestQueue;
    private static final String URL = "http://23.99.229.212:3000/home/create_goal/123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCreateGoalBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.toolbarCreateGoal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRequestQueue = Volley.newRequestQueue(this);

        mBinding.editTextGoalTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if("".equals(s.toString())){
                    mBinding.buttonCreateGoal.setEnabled(false);
                }
                else {
                    mBinding.buttonCreateGoal.setEnabled(true);
                }
            }
        });
    }

    public void createGoal(View view) {
        JSONObject postData = new JSONObject();
        String requestBody;
        try {
            postData.put("title", mBinding.editTextGoalTitle.getText().toString());
            postData.put("author", "Eric Liu");
            postData.put("content", mBinding.editTextGoalContent.getText().toString());
            postData.put("milestones", "N/A");
            postData.put("schedule", "N/A");
            postData.put("tag", "employment");
            requestBody = postData.toString();

            StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
                            Toast.makeText(getBaseContext(), "Successfully created goal!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d(TAG, "post request failed");
                            error.printStackTrace();
                            Toast.makeText(getBaseContext(), "Create goal failed", Toast.LENGTH_SHORT).show();
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
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };

            mRequestQueue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}