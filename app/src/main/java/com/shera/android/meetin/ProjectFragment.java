package com.shera.android.meetin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shera.android.meetin.entities.Project;
import com.shera.android.meetin.entities.Reward;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectFragment extends Fragment {

    private static final String ARG_PROJECT_ID = "project_id";
    private RecyclerView mRewardRecyclerView;
    private RewardAdapter mAdapter;
    private List<Reward> mRewardItems = new ArrayList<>();

    public static ProjectFragment newInstance(UUID projectId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROJECT_ID, projectId);
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_PROJECT_ID);
        // --------------------------------------QUESTIONABLE--------------------------------------
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project, container, false);
        mRewardRecyclerView = (RecyclerView) v.findViewById(R.id.reward_recycler_view);
        mRewardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRewardRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
               if(!recyclerView.canScrollVertically(-1))
                {
                    // --------------------------------------IMPLEMENT--------------------------------------
                }
            }
        });
        setupAdapter();
        return v;
    }


    private void setupAdapter()
    {
        if (mAdapter == null) {
            mAdapter = new RewardAdapter(mRewardItems);
            mRewardRecyclerView.setAdapter(mAdapter);
        } else if  (mRewardRecyclerView.getAdapter() == null) { //for after rotation
            mRewardRecyclerView.setAdapter(mAdapter);
        }
    }

    private class RewardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

       private Reward mReward;
        // --------------------------------------IMPLEMENT--------------------------------------

        public RewardHolder(View itemView) {
            super(itemView);
            // --------------------------------------IMPLEMENT--------------------------------------
            itemView.setOnClickListener(this);
        }

        public void bindReward(Reward reward) {
            mReward = reward;
          // --------------------------------------IMPLEMENT--------------------------------------
        }

        @Override
        public void onClick(View v) {
            // --------------------------------------IMPLEMENT--------------------------------------
        }
    }

    private class RewardAdapter extends RecyclerView.Adapter<RewardHolder> {

        private List<Reward> mRewardItems;

        public RewardAdapter(List<Reward> rewardItems) {
            mRewardItems = rewardItems;
        }

        public void setRewardItems(List<Reward> rewardItems){mRewardItems = rewardItems;}

        @Override
        public RewardHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_reward, viewGroup, false);
            return new RewardHolder(view);
        }
        @Override
        public void onBindViewHolder(RewardHolder rewardHolder, int position) {
            Reward reward = mRewardItems.get(position);
            rewardHolder.bindReward(reward);
        }
        @Override
        public int getItemCount() {
            return mRewardItems.size();
        }
    }

    private class FetchProjectTask extends AsyncTask<UUID,Void,Project> {



        @Override
        protected Project doInBackground(UUID... params) {
            return new ProjectFetch().downloadProjectById(params[0]);
        }

        @Override
        protected void onPostExecute(Project project) {
            if(project != null) {
                // --------------------------------------IMPLEMENT--------------------------------------

            }
        }
    }



}
