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

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public FeedFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FeedFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FeedFragment newInstance(String param1, String param2) {
//        FeedFragment fragment = new FeedFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    private static final String FRIENDLISTURL = "http://52.188.108.13:3000/friendslist/";

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
}