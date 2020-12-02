package com.example.goalstarterandroidapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goalstarterandroidapp.databinding.ActivitySendFriendReqBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SendFriendReqActivity extends AppCompatActivity {

    private static final String URL = "http://52.188.108.13:3000/home/send_requests";
    private ActivitySendFriendReqBinding mBinding;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySendFriendReqBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mRequestQueue = Volley.newRequestQueue(this);

        mBinding.editTextFriendEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                if("".equals(s.toString())){
                    mBinding.buttonSendRequest.setEnabled(false);
                }
                else {
                    mBinding.buttonSendRequest.setEnabled(true);
                }
            }
        });
    }

    public void SendFriendReq(View view){
        JSONObject postData = new JSONObject();
        String requestBody = null;

        String userInfo = getIntent().getStringExtra("userInfo");
        String userEmail = null;
        try {
            JSONObject data = new JSONObject(userInfo);
            userEmail = data.getString("email");
            postData.put("email", mBinding.editTextFriendEmail.getText().toString());
            postData.put("userEmail", userEmail);
            requestBody = postData.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String finalRequestBody = requestBody;
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getBaseContext(), "Successfully sent friend request!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        error.printStackTrace();
                        System.out.println(error.getMessage());
                        Toast.makeText(getBaseContext(), "Request Failed: Enter a valid account email", Toast.LENGTH_SHORT).show();
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