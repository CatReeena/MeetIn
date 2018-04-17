package com.shera.android.meetin;


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

import com.squareup.picasso.Picasso;

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
    private int currentPage = 1;
    private FetchItemsTask fetchItemsTask;

    public static ProjectListFragment newInstance() {
        return new ProjectListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchItemsTask = new FetchItemsTask();
        fetchItemsTask.execute(new TaskFilters(null,null)); //--------------IMPLEMENT PARAMS----------------------
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
                    if (fetchItemsTask.getStatus()== AsyncTask.Status.FINISHED) {   //add items
                        //--------------IMPLEMENT----------------------
                    }
                }
            }
        });
        setupAdapter();
        return v;
    }

    private void setupAdapter()
    {
        if (mAdapter == null) {
            mAdapter = new ProjectAdapter(mProjectItems);
            mProjectRecyclerView.setAdapter(mAdapter);
        } else {
            if (mProjectRecyclerView.getAdapter() == null) { //for after rotation
                mProjectRecyclerView.setAdapter(mAdapter);
            }
            mAdapter.setGalleryItems(mProjectItems);  //update adapter (maybe separate method)
            mAdapter.notifyDataSetChanged();
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

        public ProjectHolder(View itemView) {
            super(itemView);
            mProjectImage = (ImageView) itemView.findViewById(R.id.project_image);
            mProjectName = (TextView) itemView.findViewById(R.id.project_name);
            mFundingPercent = (TextView) itemView.findViewById(R.id.funding_percent);
            mFundingProgressBar = (ProgressBar) itemView.findViewById(R.id.funding_progress_bar);
            mProjectCategory = (TextView) itemView.findViewById(R.id.project_category);
            mProjectLocation = (TextView) itemView.findViewById(R.id.project_location);
            mProjectSave = (ImageButton)  itemView.findViewById(R.id.save_button);
            itemView.setOnClickListener(this);
        }

        public void bindProject(Project project) {
            mProject = project;
            Picasso.with(getActivity())
                    .load(mProject.imageLinks[0])
                    .into(mProjectImage);
        }

        @Override
        public void onClick(View v) {
            //--------------IMPLEMENT----------------------
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


        @Override
        protected List<Project> doInBackground(TaskFilters... params) {

            Category category = params[0].category;
            return new ArrayList<Project>();
        }

        @Override
        protected void onPostExecute(List<Project> items) {
            mProjectItems.addAll(items);
            setupAdapter();
        }
    }



}

