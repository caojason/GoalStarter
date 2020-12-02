package com.example.goalstarterandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goalstarterandroidapp.databinding.ActivityCommentBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    private static final String TAG = CommentActivity.class.getName();
    private ActivityCommentBinding mBinding;
    private static final String COMMENTURL = "http://52.188.108.13:3000/home/comment/"; // need to append userid before use
    private RequestQueue mQueue;
    private String username;
    private String userid;
    private int position;
    private int source;
    private JSONObject jsonGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // get args from intent
        String goal = getIntent().getStringExtra("goal");
        username = getIntent().getStringExtra("username");
        userid = getIntent().getStringExtra("userid");
        position = getIntent().getIntExtra("position", -1);
        source = getIntent().getIntExtra("adapter type", -1); // 0 for feed, 1 for my goals

        // set up request queue
        mQueue = Volley.newRequestQueue(this);

        // set up tool bar
        setSupportActionBar(mBinding.toolbarComments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // use linear layout manager to get vertical list
        mBinding.recyclerViewComment.setLayoutManager(new LinearLayoutManager(this));
        // code to set adapter
        try {
            // get the comments from goal
            jsonGoal = new JSONObject(goal);
            JSONArray comments = jsonGoal.getJSONArray("comments");
            if(comments.length() > 0 && comments.getString(0).equals("NULL")){
                comments.remove(0);
            }
            // set adapter to custom adapter using the comment.xml layout
            mBinding.recyclerViewComment.setAdapter(new CommentRecyclerViewAdapter(this, comments));
        } catch (JSONException e) {
            jsonGoal = null; // hopefully this never happens
            e.printStackTrace();
        }

        // add spacing to the bottom of recycler view (10dp)
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int)(10 * getResources().getDisplayMetrics().density));
        mBinding.recyclerViewComment.addItemDecoration(bottomOffsetDecoration);

        // disable send button when no comment is entered
        mBinding.editTextComment.addTextChangedListener(new TextWatcher() {
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
                // disable send button when no comment is entered
                if(commentExists(s)){
                    mBinding.buttonComment.setEnabled(true);
                }
                else {
                    mBinding.buttonComment.setEnabled(false);
                }
            }
        });
    }

    public void createNewComment(View v){
        // create comment string
        String commentContent = mBinding.editTextComment.getText().toString();
        String combinedComment = username +  ": " + commentContent;

        // make post request
        String requestUrl = COMMENTURL + userid;
//        JSONObject requestBody = new JSONObject();
        Map<String, String> requestBody = new HashMap<>();
        try{
            String goalid = jsonGoal.getString("id");
            requestBody.put("id", goalid);
            requestBody.put("author", username);
            requestBody.put("comment", commentContent);
        } catch(JSONException e){
            e.printStackTrace();
            Log.d(TAG, "failed to put some fields in body");
        }

        StringRequest putComment = new StringRequest(Request.Method.PUT, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // add to goal and show changes on screen
                try{
                    jsonGoal.getJSONArray("comments").put(combinedComment);
                }catch (JSONException e){
                    e.printStackTrace();
                    Log.d(TAG, "could not add new comment to json object");
                }
                mBinding.recyclerViewComment.getAdapter().notifyDataSetChanged();
                mBinding.editTextComment.getText().clear();
                Toast.makeText(getApplicationContext(), "Successfully added comment", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "put comment failed, request body:" + requestBody.toString());
                Log.d(TAG, "goal to place comment on:" + jsonGoal.toString());
                Log.d(TAG, "url used:" + requestUrl);
                Log.d(TAG, "error: " + error.toString());
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Failed to added comment", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getParams(){
                return requestBody;
            }
        };

        mQueue.add(putComment);

    }

    private boolean commentExists(@Nullable Editable text){
        // check if comment is not empty
        return text != null && text.length() > 0;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        // organize data to return to parent activity
        Intent intent = new Intent();
        intent.putExtra("goal", jsonGoal.toString());
        intent.putExtra("adapter type", source);
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        // go back to parent activity
        finish();
    }

}