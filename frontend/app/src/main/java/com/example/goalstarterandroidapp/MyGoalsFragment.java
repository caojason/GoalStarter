package com.example.goalstarterandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

public class MyGoalsFragment extends Fragment {

    private static final String TAG = MyGoalsFragment.class.getName();
    private RequestQueue mQueue;

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
                startActivity(intent);
            }
        });

        // recycler view
        mRecyclerView = view.findViewById(R.id.recycler_view_my_goals);
        // set layout manager for recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // set recycler view adapter
//        parentActivity.getFeedAdapter(); //TODO: uncomment this once the backend is set up and correct URL is added

        // add spacing to the bottom of recycler view (80dp)
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int)(80 * getResources().getDisplayMetrics().density));
        mRecyclerView.addItemDecoration(bottomOffsetDecoration);

        return view;
    }

    public void attachAdapter(GoalCardRecycleViewAdapter adapter){
        mRecyclerView.setAdapter(adapter);
    }
}