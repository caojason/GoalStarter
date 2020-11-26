package com.example.goalstarterandroidapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goalstarterandroidapp.databinding.CommentBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.CommentViewHolder> {
    private static final String TAG = CommentRecyclerViewAdapter.class.getName();
    private final Context mContext;
    private final JSONArray mComments;

    public CommentRecyclerViewAdapter(Context context, JSONArray comments){
        this.mContext = context;
        this.mComments = comments;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        try {
            String combinedComment = (String) mComments.get(position);
            // comments are in the form "<author>: <comment>"
            // here we find where the colon is so we can split the string
            int colonPosition = combinedComment.indexOf(":");
            if(colonPosition != -1){
                String author = combinedComment.substring(0, colonPosition).trim();
                String comment = combinedComment.substring(colonPosition + 1).trim();
                holder.bind(author, comment);
            }
            else {
                Log.d(TAG, "error, comment formatted incorrectly");
                Log.d(TAG, "comment: " + combinedComment);
                holder.bind("Error", "Could not display comment");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mComments.length();
    }


    // custom view holder class
    class CommentViewHolder extends RecyclerView.ViewHolder{
        private final CommentBinding mBinding;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mBinding = CommentBinding.bind(itemView);
        }

        // takes a comment and the author of the comment and adds it to a child of the recyclerview
        public void bind(String author, String comment){
            mBinding.textViewCommentAuthor.setText(author);
            mBinding.textViewCommentContent.setText(comment);
        }
    }
}
