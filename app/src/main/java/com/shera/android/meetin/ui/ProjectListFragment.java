package com.shera.android.meetin.ui;




import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.os.ResultReceiver;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shera.android.meetin.FetchAddressIntentService;
import com.shera.android.meetin.ItemsFetch;
import com.shera.android.meetin.Position;
import com.shera.android.meetin.ProjectsFilters;
import com.shera.android.meetin.R;
import com.shera.android.meetin.entities.Project;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shera on 17.04.2018.
 */

public class ProjectListFragment extends Fragment {

    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME =
            "com.shera.android.meetin";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";

    private RecyclerView mProjectRecyclerView;
    private ProjectAdapter mAdapter;
    private List<Project> mProjectItems = new ArrayList<>();
    private FetchProjectsTask fetchProjectsTask;

    public static ProjectListFragment newInstance() {
        return new ProjectListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addItems(Position.FRESH);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project_list, container, false);
        mProjectRecyclerView = (RecyclerView) v.findViewById(R.id.project_recycler_view);
        mProjectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProjectRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (fetchProjectsTask.getStatus()== AsyncTask.Status.FINISHED) {
                        addItems(Position.LAST);
                    }
                }
                else if(!recyclerView.canScrollVertically(-1))
                {
                    if (fetchProjectsTask.getStatus()== AsyncTask.Status.FINISHED) {
                       addItems(Position.FIRST);
                    }
                }
            }
        });
        setupAdapter();
        return v;
    }

    public void addItems(Position position)
    {
        fetchProjectsTask = new FetchProjectsTask();
        if(mProjectItems.isEmpty())
        {
            fetchProjectsTask.execute(getFilters(Position.FRESH));
        }
        else {
            fetchProjectsTask.execute(
                    getFilters(position));
        }
    }

    private void setupAdapter()
    {
        if (mAdapter == null) {
            mAdapter = new ProjectAdapter(mProjectItems);
            mProjectRecyclerView.setAdapter(mAdapter);
        } else if  (mProjectRecyclerView.getAdapter() == null) { //for after rotation
                mProjectRecyclerView.setAdapter(mAdapter);
            }
    }

    public ProjectsFilters getFilters(Position position)
    {
        switch (position) {
            case LAST:
                return new ProjectsFilters(mProjectItems.get(mProjectItems.size() - 1).getId(),
                        Position.LAST,
                        null,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false);
            case FIRST:
                return new ProjectsFilters(mProjectItems.get(0).getId(),
                        Position.FIRST,
                        null,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false);
                default: return new ProjectsFilters(Position.FRESH);
        }
    }

    private class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private AddressResultReceiver mResultReceiver = new AddressResultReceiver(new Handler());
        private ImageView mProjectImage;
        private TextView mProjectName;
        private TextView mFundingPercent;
        private ProgressBar mFundingProgressBar;
        private TextView mProjectCategory;
        private TextView mProjectLocation;
        private ImageButton mProjectSave;
        private Project mProject;
        private TextView mProjectRaisedMoney;
        private TextView mProjectDaysLeft;

        public ProjectHolder(View itemView) {
            super(itemView);
            mProjectImage = itemView.findViewById(R.id.project_image);
            mProjectName = itemView.findViewById(R.id.project_name);
            mFundingPercent = itemView.findViewById(R.id.funding_percent);
            mFundingProgressBar = itemView.findViewById(R.id.funding_progress_bar);
            mProjectCategory = itemView.findViewById(R.id.project_category);
            mProjectLocation = itemView.findViewById(R.id.project_location);
            mProjectSave = itemView.findViewById(R.id.save_button);
            mProjectRaisedMoney = itemView.findViewById(R.id.raised_money);
            mProjectDaysLeft = itemView.findViewById(R.id.days_left);
            itemView.setOnClickListener(this);
        }

        public void bindProject(Project project) {
            mProject = project;
            mProjectName.setText(mProject.getName());
            if (mProject.getProjectImageLink()!= null) {
                {
                    Picasso.with(getActivity())
                            .load(mProject.getProjectImageLink())
                            .into(mProjectImage);
                }
            }
            Picasso.with(getActivity())
                    .load("https://www.quebecoriginal.com/en/listing/images/800x600/75e8a9e6-ffc5-40d0-aa0e-eeb3518b92e2/august-festival-scene-principale.jpg")
                    .into(mProjectImage);

            mProjectRaisedMoney.setText(getString(R.string.raised_money, mProject.getRaisedMoney().toString()));
            mFundingPercent.setText(getString(R.string.funding_percent, mProject.getProgressPercent()));
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
            mFundingProgressBar.setProgress(mProject.getProgressPercent());

            if (!mProject.getCategories().isEmpty()) {
                mProjectCategory.setText(mProject.getCategories().toString());
            }

            if (mProject.getLocation()!= null){
                startIntentService();
            }
        }

        protected void startIntentService() {
            Intent intent = new Intent(getActivity(), FetchAddressIntentService.class);
            intent.putExtra(RECEIVER, mResultReceiver);
            intent.putExtra(LOCATION_DATA_EXTRA, mProject.getLocation());
            getActivity().startService(intent);
        }

        @Override
        public void onClick(View v) {
            Intent intent = ProjectActivity.newIntent(getActivity(), mProject.getId());
            startActivity(intent);
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
                    if(mAddressOutput.getLocality() != null) {
                        mProjectLocation.setText(mAddressOutput.getLocality());
                    }else if(mAddressOutput.getAdminArea() != null){
                        mProjectLocation.setText(mAddressOutput.getAdminArea());
                    }
                }
            }
        }
    }

    private class ProjectAdapter extends RecyclerView.Adapter<ProjectHolder> {

        private List<Project> mProjectItems;

        public ProjectAdapter(List<Project> projectItems) {
            mProjectItems = projectItems;
        }

        public void setProjectItems(List<Project> projectItems){mProjectItems = projectItems;}

        @Override
        public ProjectHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_project, viewGroup, false);
            return new ProjectHolder(view);
        }
        @Override
        public void onBindViewHolder(ProjectHolder photoHolder, int position) {
            Project project = mProjectItems.get(position);
            photoHolder.bindProject(project);
        }
        @Override
        public int getItemCount() {
            return mProjectItems.size();
        }
    }

    private class FetchProjectsTask extends AsyncTask<ProjectsFilters,Void,List<Project>> {

        private Position position;

        @Override
        protected List<Project> doInBackground(ProjectsFilters... params) {
            position = params[0].getPosition();
            return new ItemsFetch().downloadProjects(params[0]);
        }

        @Override
        protected void onPostExecute(List<Project> items) {
            if(items != null) {
                switch (position)
                {
                    case FIRST:
                        mProjectItems.addAll(0,items);
                        mAdapter.notifyItemRangeInserted(0,items.size());
                        break;
                    case LAST:
                        mProjectItems.addAll(items);
                        mAdapter.setProjectItems(mProjectItems);  //update adapter (maybe separate method)
                        mAdapter.notifyDataSetChanged();
                        break;
                    case FRESH:
                        mProjectItems = new ArrayList<>(items);
                        mAdapter.setProjectItems(mProjectItems);  //update adapter (maybe separate method)
                        mAdapter.notifyDataSetChanged();
                        break;
                }


            }
        }
    }
}

