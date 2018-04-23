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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shera.android.meetin.entities.Project;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

/**
 * Created by Shera on 17.04.2018.
 */

public class ProjectListFragment extends Fragment {

    private RecyclerView mProjectRecyclerView;
    private ProjectAdapter mAdapter;
    private List<Project> mProjectItems = new ArrayList<>();
    private FetchItemsTask fetchItemsTask;

    public static ProjectListFragment newInstance() {
        return new ProjectListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addItems(TaskFilters.ProjectPosition.FRESH);
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
                    if (fetchItemsTask.getStatus()== AsyncTask.Status.FINISHED) {
                        addItems(TaskFilters.ProjectPosition.LAST_PROJECT);
                    }
                }
                else if(!recyclerView.canScrollVertically(-1))
                {
                    if (fetchItemsTask.getStatus()== AsyncTask.Status.FINISHED) {
                       addItems(TaskFilters.ProjectPosition.FIRST_PROJECT);
                    }
                }
            }
        });
        setupAdapter();
        return v;
    }

    public void addItems(TaskFilters.ProjectPosition projectPosition)
    {
        fetchItemsTask = new FetchItemsTask();
        if(mProjectItems.isEmpty())
        {
            fetchItemsTask.execute(getFilters(TaskFilters.ProjectPosition.FRESH));
        }
        else {
            fetchItemsTask.execute(
                    getFilters(projectPosition));
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

    public TaskFilters getFilters(TaskFilters.ProjectPosition projectPosition)
    {
        switch (projectPosition) {
            case LAST_PROJECT:
                return new TaskFilters(mProjectItems.get(mProjectItems.size() - 1).getId(),
                        TaskFilters.ProjectPosition.LAST_PROJECT,
                        null,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false);
            case FIRST_PROJECT:
                return new TaskFilters(mProjectItems.get(0).getId(),
                        TaskFilters.ProjectPosition.FIRST_PROJECT,
                        null,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false);
                default: return new TaskFilters(TaskFilters.ProjectPosition.FRESH);
        }
    }

    private class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mProjectImage;
        private TextView mProjectName;
        private TextView mFundingPercent;
        private ProgressBar mFundingProgressBar;
        private TextView mProjectCategory;
        private TextView mProjectLocation;
        private ImageButton mProjectSave;
        private Project mProject;
        private TextView  mProjectGoal;
        private TextView mProjectDaysLeft;

        public ProjectHolder(View itemView) {
            super(itemView);
            mProjectImage = (ImageView) itemView.findViewById(R.id.project_image);
            mProjectName = (TextView) itemView.findViewById(R.id.project_name);
            mFundingPercent = (TextView) itemView.findViewById(R.id.funding_percent);
            mFundingProgressBar = (ProgressBar) itemView.findViewById(R.id.funding_progress_bar);
            mProjectCategory = (TextView) itemView.findViewById(R.id.project_category);
            mProjectLocation = (TextView) itemView.findViewById(R.id.project_location);
            mProjectSave = (ImageButton)  itemView.findViewById(R.id.save_button);
            mProjectGoal = (TextView) itemView.findViewById(R.id.funding_goal);
            mProjectDaysLeft = (TextView) itemView.findViewById(R.id.days_left);
            itemView.setOnClickListener(this);
        }

        public void bindProject(Project project) {
            mProject = project;
            mProjectName.setText(mProject.getName());
            if(mProject.getImageLinks()!= null) {
                if (mProject.getImageLinks().isEmpty()== false) {
                    Picasso.with(getActivity())
                            .load(mProject.getImageLinks().get(0))
                            .into(mProjectImage);
                }
            }
            Picasso.with(getActivity())
                    .load("https://www.quebecoriginal.com/en/listing/images/800x600/75e8a9e6-ffc5-40d0-aa0e-eeb3518b92e2/august-festival-scene-principale.jpg")
                    .into(mProjectImage);

            mProjectGoal.setText(getString(R.string.goal)+ " " + mProject.getFundingGoal().toString());
            mFundingPercent.setText(String.valueOf(50)+ " %");
            if(mProject.getEndDateTime()!= null) {
                Duration duration = new Duration(DateTime.now(), mProject.getEndDateTime().toDateTime());
                String days = String.valueOf(duration.getStandardDays());
                mProjectDaysLeft.setText(days + getString(R.string.days_left));
            }
           // mFundingPercent.setText(String.valueOf(mProject.countProgress()));
           // mFundingProgressBar.setProgress(mProject.countProgress());

            mFundingProgressBar.setProgress(50);
            if (mProject.getLocation()!=null) {
                mProjectLocation.setText(mProject.getLocation().toString());
            }
            mProjectCategory.setText(mProject.getCategories().toString());
        }

        @Override
        public void onClick(View v) {
            Intent intent = ProjectActivity.newIntent(getActivity(), mProject.getId());
            startActivity(intent);
        }
    }

    private class ProjectAdapter extends RecyclerView.Adapter<ProjectHolder> {

        private List<Project> mProjectItems;

        public ProjectAdapter(List<Project> projectItems) {
            mProjectItems = projectItems;
        }

        public void setGalleryItems(List<Project> projectItems){mProjectItems = projectItems;}

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

    private class FetchItemsTask extends AsyncTask<TaskFilters,Void,List<Project>> {

        private TaskFilters.ProjectPosition projectPosition;

        @Override
        protected List<Project> doInBackground(TaskFilters... params) {
            projectPosition = params[0].getProjectPosition();
            return new ProjectFetch().downloadProjects(params[0]);
        }

        @Override
        protected void onPostExecute(List<Project> items) {
            if(items != null) {
                switch (projectPosition)
                {
                    case FIRST_PROJECT:
                        mProjectItems.addAll(0,items);
                        mAdapter.notifyItemRangeInserted(0,items.size());
                        break;
                    case LAST_PROJECT:
                        mProjectItems.addAll(items);
                        mAdapter.setGalleryItems(mProjectItems);  //update adapter (maybe separate method)
                        mAdapter.notifyDataSetChanged();
                        break;
                    case FRESH:
                        mProjectItems = new ArrayList<>(items);
                        mAdapter.setGalleryItems(mProjectItems);  //update adapter (maybe separate method)
                        mAdapter.notifyDataSetChanged();
                        break;
                }


            }
        }
    }
}

