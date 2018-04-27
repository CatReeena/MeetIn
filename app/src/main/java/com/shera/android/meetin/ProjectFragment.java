package com.shera.android.meetin;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shera.android.meetin.entities.Project;
import com.shera.android.meetin.entities.Reward;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectFragment extends Fragment {

    private static final String ARG_PROJECT_ID = "project_id";
    private RecyclerView mRewardRecyclerView;
    private RewardAdapter mAdapter;
    private List<Reward> mRewardItems = new ArrayList<>();
    private Project mProject;
    private ImageView mProjectImageView;
    private TextView mProjectName;
    private TextView mFundingPercent;
    private ProgressBar mFundingProgressBar;
    private TextView mProjectRaisedMoney;
    private TextView mProjectDaysLeft;
    private TextView mProjectDescription;
    private ConstraintLayout mProjectCommentsLayout;
    private ConstraintLayout mProjectUpdatesLayout;
    private ConstraintLayout mProjectVideosLayout;
    private TextView mProjectComments;
    private TextView mProjectUpdates;
    private TextView mProjectVideos;
    private Callbacks mCallbacks;


    public static ProjectFragment newInstance(Long projectId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROJECT_ID, projectId);
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface Callbacks {
        void onUnexistingProjectBehavior();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Long crimeId = (Long) getArguments().getSerializable(ARG_PROJECT_ID);
        mProject = new ProjectFetch().downloadProjectById(crimeId);
        if (mProject==null)
        {
            mCallbacks.onUnexistingProjectBehavior();
        }


        // --------------------------------------QUESTIONABLE--------------------------------------
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project, container, false);
        mProjectImageView =  v.findViewById(R.id.project_image_detailed);
        mProjectName = v.findViewById(R.id.project_name_detailed);
        mFundingPercent = v.findViewById(R.id.funding_percent);
        mFundingProgressBar = v.findViewById(R.id.funding_progress_bar);
        mProjectRaisedMoney = v.findViewById(R.id.raised_money);
        mProjectDaysLeft = v.findViewById(R.id.days_left);
        mProjectDescription = v.findViewById(R.id.project_description);
        mProjectCommentsLayout = v.findViewById(R.id.comments_layout);
        mProjectUpdatesLayout = v.findViewById(R.id.updates_layout);
        mProjectVideosLayout = v.findViewById(R.id.videos_layout);
        mProjectComments = v.findViewById(R.id.project_comments);
        mProjectUpdates = v.findViewById(R.id.project_updates);
        mProjectVideos = v.findViewById(R.id.project_videos);
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
        mProjectName.setText(mProject.getName());
        mProjectDescription.setText(mProject.getDescription());
        if( mProject.getFundingGoal() != null) {
            mProjectRaisedMoney.setText(getString(R.string.raised_money_and_total,
                    mProject.getRaisedMoney().toString(),
                    mProject.getFundingGoal().toString()));
        }
        if (mProject.getEndDateTime()!= null) {
            Duration duration = new Duration(DateTime.now(), mProject.getEndDateTime().toDateTime());
            int days = (int) duration.getStandardDays(); //---------------------NOT_SURE_ABOUT_THIS---------------------------------------
            Resources res = getResources();
            if(days > 0) {
                mProjectDaysLeft.setText(res.getQuantityString(R.plurals.days_left, days, days));
            }
            else{
                mProjectDaysLeft.setText(res.getQuantityString(R.plurals.days_left, 0, 0));
            }
        }
        Picasso.with(getActivity())
                .load("https://www.quebecoriginal.com/en/listing/images/800x600/75e8a9e6-ffc5-40d0-aa0e-eeb3518b92e2/august-festival-scene-principale.jpg")
                .into(mProjectImageView);

        if (!mProject.getComments().isEmpty())
        {
            mProjectComments.setEnabled(true);
            mProjectCommentsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // --------------------------------------IMPLEMENT--------------------------------------
                }
            });
        }

        if (!mProject.getUpdates().isEmpty())
        {
            mProjectUpdates.setEnabled(true);
            mProjectUpdatesLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // --------------------------------------IMPLEMENT--------------------------------------
                }
            });
        }
        if (!mProject.getVideoLinks().isEmpty())
        {
            mProjectVideos.setEnabled(true);
            mProjectVideosLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // --------------------------------------IMPLEMENT--------------------------------------
                }
            });
        }
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





}
