package com.shera.android.meetin.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.os.ResultReceiver;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shera.android.meetin.FetchAddressIntentService;
import com.shera.android.meetin.ItemsFetch;
import com.shera.android.meetin.R;
import com.shera.android.meetin.entities.Project;
import com.shera.android.meetin.entities.Reward;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;

import agency.tango.android.avatarview.views.AvatarView;

import static com.shera.android.meetin.Constants.LOCATION_DATA_EXTRA;
import static com.shera.android.meetin.Constants.LOCATION_PERMISSIONS;
import static com.shera.android.meetin.Constants.RECEIVER;
import static com.shera.android.meetin.Constants.REQUEST_LOCATION_PERMISSIONS;
import static com.shera.android.meetin.Constants.RESULT_DATA_KEY;
import static com.shera.android.meetin.Constants.SUCCESS_RESULT;

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
    private TextView mProjectOwners;
    private AvatarView mProjectOwnersAvatar;
    private TextView mProjectDescription;
    private ConstraintLayout mProjectCommentsLayout;
    private ConstraintLayout mProjectUpdatesLayout;
    private ConstraintLayout mProjectVideosLayout;
    private TextView mProjectComments;
    private TextView mProjectUpdates;
    private TextView mProjectVideos;
    private TextView mProjectCategory;
    private TextView mProjectLocation;
    private TextView mRewardsHeader;
    private Callbacks mCallbacks;
    private AddressResultReceiver mResultReceiver = new AddressResultReceiver(new Handler());

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
        // --------------------------------------QUESTIONABLE--------------------------------------
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project, container, false);
        v.setVisibility(View.INVISIBLE);
        mProjectImageView =  v.findViewById(R.id.project_image_detailed);
        mProjectName = v.findViewById(R.id.project_name_detailed);
        mFundingPercent = v.findViewById(R.id.funding_percent);
        mFundingProgressBar = v.findViewById(R.id.funding_progress_bar);
        mProjectRaisedMoney = v.findViewById(R.id.raised_money);
        mProjectDaysLeft = v.findViewById(R.id.days_left);
        mProjectOwners = v.findViewById(R.id.project_owners);;
        mProjectOwnersAvatar = v.findViewById(R.id.project_owners_avatar);
        mProjectDescription = v.findViewById(R.id.project_description);
        mProjectCommentsLayout = v.findViewById(R.id.comments_layout);
        mProjectUpdatesLayout = v.findViewById(R.id.updates_layout);
        mProjectVideosLayout = v.findViewById(R.id.videos_layout);
        mProjectComments = v.findViewById(R.id.project_comments);
        mProjectUpdates = v.findViewById(R.id.project_updates);
        mProjectVideos = v.findViewById(R.id.project_videos);
        mRewardRecyclerView = v.findViewById(R.id.reward_recycler_view);
        mProjectCategory =  v.findViewById(R.id.full_categories);
        mProjectLocation =  v.findViewById(R.id.full_location);
        mRewardsHeader = v.findViewById(R.id.reward_header);
        mRewardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRewardRecyclerView.setNestedScrollingEnabled(false);
        Long projectId = (Long) getArguments().getSerializable(ARG_PROJECT_ID);
        new FetchProjectTask().execute(projectId);
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

    protected void startIntentService() {
        Intent intent = new Intent(getActivity(), FetchAddressIntentService.class);
        intent.putExtra(RECEIVER, mResultReceiver);
        intent.putExtra(LOCATION_DATA_EXTRA, mProject.getLocation());
        getActivity().startService(intent);
    }

    class AddressResultReceiver extends ResultReceiver {
        @SuppressLint("RestrictedApi")
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultData == null) {
                return;
            }

            if (resultCode == SUCCESS_RESULT) {
                Address mAddressOutput = resultData.getParcelable(RESULT_DATA_KEY);
                mProjectLocation.setText(mAddressOutput.getAddressLine(0));

            }
        }
    }

    private class RewardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

       private Reward mReward;
       private TextView mRewardSum;
       private TextView mRewardDescription;
       private TextView mRewardDeliveryDate;
       private TextView mRewardShippedTo;
       private TextView mRewardBakers;


        public RewardHolder(View itemView) {
            super(itemView);
            mRewardSum = itemView.findViewById(R.id.reward_sum);
            mRewardDescription = itemView.findViewById(R.id.reward_description);
            mRewardDeliveryDate = itemView.findViewById(R.id.reward_delivery_date);
            mRewardShippedTo = itemView.findViewById(R.id.reward_shipped_to);
            mRewardBakers = itemView.findViewById(R.id.reward_backers);
            itemView.setOnClickListener(this);
        }

        public void bindReward(Reward reward) {
            mReward = reward;
            //----------------------------------------------------DELETE_AFTER??----------------------------
            mRewardSum.setText(getString(R.string.reward_sum, mReward.getMinimalContribution().toString()));
            mRewardDescription.setText(mReward.getDescription());
            if(mReward.getDeliveryDate() != null){
                mRewardDeliveryDate.setText(mReward.getDeliveryDate());
            }
            if(mReward.getShippingLocation() != null)
            {
                mRewardShippedTo.setText(mReward.getShippingLocation());
            }
            if(mReward.getLimit() != null) {
                Resources res = getResources();
                mRewardBakers.setText(res.getQuantityString(R.plurals.reward_taken_times,  mReward.getContributions().size(),  mReward.getContributions().size(), mReward.getLimit() ));
            }
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

    private class FetchProjectTask extends AsyncTask<Long,Void,Project> {

        @Override
        protected Project doInBackground(Long... params) {
            return new ItemsFetch().downloadProjectById(params[0]);
        }

        @Override
        protected void onPostExecute(Project project) {
            mProject = project;
            if (mProject == null)
            {
                mCallbacks.onUnexistingProjectBehavior();
            }
            else {
                getView().setVisibility(View.VISIBLE);
                mProjectName.setText(mProject.getName());
                mProjectDescription.setText(mProject.getDescription());
                if (mProject.getFundingGoal() != null) {
                    mProjectRaisedMoney.setText(getString(R.string.raised_money_and_total,
                            mProject.getRaisedMoney().toString(),
                            mProject.getFundingGoal().toString()));
                }
                mFundingProgressBar.setProgress(mProject.getProgressPercent());
                mFundingPercent.setText(getString(R.string.funding_percent, mProject.getProgressPercent()));
                if (mProject.getEndDateTime() != null) {
                    Duration duration = new Duration(DateTime.now(), mProject.getEndDateTime().toDateTime());
                    int days = (int) duration.getStandardDays(); //---------------------NOT_SURE_ABOUT_THIS---------------------------------------
                    Resources res = getResources();
                    if (days > 0) {
                        mProjectDaysLeft.setText(res.getQuantityString(R.plurals.days_left, days, days));
                    } else {
                        mProjectDaysLeft.setText(res.getQuantityString(R.plurals.days_left, 0, 0));
                    }
                }
                if (mProject.getProjectImageLink()!= null) {
                    {
                        Picasso.with(getActivity())
                                .load(mProject.getProjectImageLink())
                                .into(mProjectImageView);
                    }
                }
                mProjectOwners.setPaintFlags(mProjectOwners.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                mProjectOwners.setText(mProject.getOwner().getName());
                if(!mProject.getTeamMembers().isEmpty()) {
                mProjectOwners.setText(TextUtils.join(", ",
                        mProject.getTeamMembers()));
                }
                if (mProject.getOwner().getPersonImageLink()!= null) {
                    Picasso.with(getActivity())
                            .load(mProject.getOwner().getPersonImageLink())
                            .placeholder(R.drawable.ic_account_circle_gray_24px)
                            .into(mProjectOwnersAvatar);
                }else
                {
                    mProjectOwnersAvatar.setImageResource(R.drawable.ic_account_circle_white_24px);
                }
                if (!mProject.getCategories().isEmpty()) {
                    String categories = TextUtils.join(", ",
                            mProject.getCategories());
                    Resources res = getResources();
                    mProjectCategory.setText(res.getQuantityString(R.plurals.categories,
                            mProject.getCategories().size(), categories));

                }
                if (mProject.getLocation()!= null){
                    startIntentService();
                }

                if (!mProject.getComments().isEmpty()) {
                    mProjectComments.setEnabled(true);
                    mProjectCommentsLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        Intent intent = CommentsActivity.newIntent(getActivity(), mProject);
                        startActivity(intent);
                        }
                    });
                }

                if (!mProject.getUpdates().isEmpty()) {
                    mProjectUpdates.setEnabled(true);
                    mProjectUpdatesLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // --------------------------------------IMPLEMENT--------------------------------------
                        }
                    });
                }
                if (!mProject.getVideoLinks().isEmpty()) {
                    mProjectVideos.setEnabled(true);
                    mProjectVideosLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // --------------------------------------IMPLEMENT--------------------------------------
                        }
                    });
                }
                mRewardItems = mProject.getRewards();
                if (mRewardItems.isEmpty()) {
                    mRewardsHeader.setVisibility(View.INVISIBLE);
                }
                setupAdapter();
            }
        }
    }
}
