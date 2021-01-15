package com.example.goalstarterandroidapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FeedFragment extends Fragment {

    private RecyclerView mRecyclerView;

    // parent activity
    private HostActivity parentActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (HostActivity) getActivity();

        String[] userInfo = parentActivity.getIntent().getStringArrayExtra("userInfo");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

//        // floating action button
//        view.findViewById(R.id.fab_my_goal_fragment).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), CreateGoalActivity.class);
//                startActivity(intent);
//            }
//        });

        // recycler view
        mRecyclerView = view.findViewById(R.id.recycler_view_feed_fragment);
        // set layout manager for recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // attach previous state if it exists
        Parcelable state = parentActivity.getFeedLayoutManager();
        if(state != null){
            mRecyclerView.getLayoutManager().onRestoreInstanceState(state);
        }

        // set recycler view adapter
        parentActivity.getFeedAdapter();

        // add spacing to the bottom of recycler view (10dp)
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int)(10 * getResources().getDisplayMetrics().density));
        mRecyclerView.addItemDecoration(bottomOffsetDecoration);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        parentActivity.setFeedLayoutManager(mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    public void attachAdapter(GoalCardRecycleViewAdapter adapter){
        mRecyclerView.setAdapter(adapter);
    }

    public void swapAdapter(GoalCardRecycleViewAdapter adapter){
        mRecyclerView.swapAdapter(adapter, true);
    }
}