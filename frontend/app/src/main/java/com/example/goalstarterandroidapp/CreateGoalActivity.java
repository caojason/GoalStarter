package com.example.goalstarterandroidapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
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
import com.example.goalstarterandroidapp.databinding.ActivityCreateGoalBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

public class CreateGoalActivity extends AppCompatActivity {
    private static final String TAG = CreateGoalActivity.class.getName();
    private ActivityCreateGoalBinding mBinding;
    private RequestQueue mRequestQueue;
    private static final String URL = "http://52.188.108.13:3000/home/create_goal/";
    private DatePickerDialog picker;
    private String[] date = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCreateGoalBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.toolbarCreateGoal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRequestQueue = Volley.newRequestQueue(this);

        setDatePicker();

        mBinding.editTextGoalTitle.addTextChangedListener(new TextWatcher() {
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
                    mBinding.buttonCreateGoal.setEnabled(false);
                }
                else {
                    mBinding.buttonCreateGoal.setEnabled(true);
                }
            }
        });

    }

    private void setDatePicker() {
        mBinding.milestone1Date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CreateGoalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mBinding.milestone1Date.setText("Milestone 1 deadline:  " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                date[0] = createDate(day, month + 1, year);
            }
        });

        mBinding.milestone2Date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CreateGoalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mBinding.milestone2Date.setText("Milestone 2 deadline:  " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                date[1] = createDate(day, month + 1, year);
            }
        });

        mBinding.milestone3Date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CreateGoalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mBinding.milestone3Date.setText("Milestone 3 deadline:  " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                date[2] = createDate(day, month + 1, year);
            }
        });

        mBinding.goalDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CreateGoalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mBinding.goalDate.setText("Goal deadline:  " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                date[3] = createDate(day, month + 1, year);
            }
        });
    }

    // note from Shuyao: you can get the user id by calling: getIntent().getIntExtra("userid", -1)
    // the above code should return the userid. if it fails to get it, it returns -1;
    public void createGoal(View view) {
        JSONObject postData = new JSONObject();
        String[] milestones = {mBinding.editTextGoalMilestone1.getText().toString(), mBinding.editTextGoalMilestone2.getText().toString(), mBinding.editTextGoalMilestone3.getText().toString(), "END GOAL"};
        String[] schedule = new String[4];
        System.arraycopy(date, 0, schedule, 0, date.length);
        date = new String[4];
        String requestBody;
        String userInfo = getIntent().getStringExtra("userInfo");
        Log.d(TAG, userInfo);
        JSONObject userInfoJSON = null;
        String userid = null;

        try {
            userInfoJSON = new JSONObject(userInfo);
            userid = userInfoJSON.getString("userid");
            Log.d(TAG, userid);
            String url = URL + userid;

            // create a new goal JSON object
            postData.put("id", userid);
            postData.put("title", mBinding.editTextGoalTitle.getText().toString());
            postData.put("author",  userInfoJSON.getString("name"));
            postData.put("content", mBinding.editTextGoalContent.getText().toString());
            postData.put("milestones", new JSONArray(milestones));
            postData.put("schedule", new JSONArray(schedule));
            postData.put("tag", mBinding.editTextGoalTag.getText().toString());
            postData.put("likes", 0);

            requestBody = postData.toString();
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
                            Toast.makeText(getBaseContext(), "Successfully created goal!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Goal: " + requestBody);
                            Intent intent = new Intent();
                            intent.setData(Uri.parse(requestBody));
                            setResult(RESULT_OK, intent);
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
            Log.d(TAG, "JSON operation failed");
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Create goal failed", Toast.LENGTH_SHORT).show();
        }
    }

    public String createDate(int day, int month, int year) {
        String monthWord;
        switch (month) {
            case 1:
                monthWord = "January";
                break;
            case 2:
                monthWord = "February";
                break;
            case 3:
                monthWord = "March";
                break;
            case 4:
                monthWord = "April";
                break;
            case 5:
                monthWord = "May";
                break;
            case 6:
                monthWord = "June";
                break;
            case 7:
                monthWord = "July";
                break;
            case 8:
                monthWord = "August";
                break;
            case 9:
                monthWord = "September";
                break;
            case 10:
                monthWord = "October";
                break;
            case 11:
                monthWord = "November";
                break;
            case 12:
                monthWord = "December";
                break;
            default:
                monthWord = "ERROR";
                break;
        }

        return monthWord + " " + day + ", " + year;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}