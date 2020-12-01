package com.example.goalstarterandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyGoalsFragment extends Fragment {

    private static final String TAG = MyGoalsFragment.class.getName();

    private RecyclerView mRecyclerView;

    // parent activity
    private HostActivity parentActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (HostActivity) getActivity();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_goals, container, false);

        // floating action button
        view.findViewById(R.id.fab_my_goal_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateGoalActivity.class);
                // get user info
                String userInfo = parentActivity.getIntent().getStringExtra("userInfo");
                intent.putExtra("userInfo", userInfo);
                parentActivity.startActivityForResult(intent, 0);
            }
        });

        // recycler view
        mRecyclerView = view.findViewById(R.id.recycler_view_my_goals);
        // set layout manager for recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // attach previous state if it exists
        Parcelable state = parentActivity.getMyGoalsLayoutManager();
        if(state != null){
            mRecyclerView.getLayoutManager().onRestoreInstanceState(state);
        }

        // set recycler view adapter
        parentActivity.getMyGoalsAdapter();



        // add spacing to the bottom of recycler view (80dp)
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int)(80 * getResources().getDisplayMetrics().density));
        mRecyclerView.addItemDecoration(bottomOffsetDecoration);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        parentActivity.setMyGoalsLayoutManager(mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    public void attachAdapter(GoalCardRecycleViewAdapter adapter){
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void swapAdapter(GoalCardRecycleViewAdapter adapter){
        mRecyclerView.swapAdapter(adapter, true);
    }
}