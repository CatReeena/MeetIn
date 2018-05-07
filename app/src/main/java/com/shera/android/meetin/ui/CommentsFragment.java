package com.shera.android.meetin.ui;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shera.android.meetin.CommentsFilters;
import com.shera.android.meetin.ItemsFetch;
import com.shera.android.meetin.Position;
import com.shera.android.meetin.R;
import com.shera.android.meetin.entities.Comment;
import com.shera.android.meetin.entities.Person;
import com.shera.android.meetin.entities.Project;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import agency.tango.android.avatarview.views.AvatarView;

public class CommentsFragment extends Fragment {

    private static final String ARG_PROJECT = "project";
    private RecyclerView mCommentRecyclerView;
    private CommentAdapter mAdapter;
    private Project mProject;
    private List<Comment> mCommentItems = new ArrayList<>();
    private FetchCommentsTask fetchCommentsTask;

    public static CommentsFragment newInstance(Project project) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROJECT, project);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
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
        View v = inflater.inflate(R.layout.fragment_comments_list, container, false);
        mCommentRecyclerView = v.findViewById(R.id.comment_recycler_view);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCommentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (fetchCommentsTask == null
                            || fetchCommentsTask.getStatus()== AsyncTask.Status.FINISHED) {
                        addItems(Position.LAST);
                    }
                }
                else if(!recyclerView.canScrollVertically(-1))
                {
                    if (fetchCommentsTask == null ||
                            fetchCommentsTask.getStatus()== AsyncTask.Status.FINISHED) {
                        addItems(Position.FIRST);
                    }
                }
            }
        });
        mProject = (Project) getArguments().getSerializable(ARG_PROJECT);
        mCommentItems = mProject.getComments();
        setupAdapter();
        return v;
    }

    public void addItems(Position position)
    {
        fetchCommentsTask = new FetchCommentsTask();
        if(mCommentItems.isEmpty())
        {
            fetchCommentsTask.execute(getFilters(Position.FRESH));
        }
        else {
            fetchCommentsTask.execute(
                    getFilters(position));
        }
    }

    private void setupAdapter()
    {
        if (mAdapter == null) {
            mAdapter = new CommentAdapter(mCommentItems);
            mCommentRecyclerView.setAdapter(mAdapter);
        } else if  (mCommentRecyclerView.getAdapter() == null) { //for after rotation
            mCommentRecyclerView.setAdapter(mAdapter);
        }
    }

    public CommentsFilters getFilters(Position position)
    {
        switch (position) {
            case LAST:
                return new CommentsFilters(Position.LAST,
                        mProject.getId(),
                        mCommentItems.get(mCommentItems.size() - 1).getId());
            case FIRST:
                return new CommentsFilters(Position.FIRST,
                        mProject.getId(),
                        mCommentItems.get(0).getId());
            default: return new CommentsFilters(Position.FRESH);
        }
    }

    private class CommentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Comment mComment;
        private ImageView mAvatar;
        private TextView mNickname;
        private TextView mCommentText;
        private TextView mCommentTime;
        private AvatarView mAuthorAvatar;


        public CommentHolder(View itemView) {
            super(itemView);
            mAvatar = itemView.findViewById(R.id.avatar_img);
            mNickname = itemView.findViewById(R.id.nickname);
            mCommentText = itemView.findViewById(R.id.comment_text);
            mCommentTime = itemView.findViewById(R.id.comment_time);
            mAuthorAvatar = itemView.findViewById(R.id.avatar_img);
            itemView.setOnClickListener(this);
        }

        public void bindComment(Comment comment) {
            mComment = comment;
            if (mComment.getAuthor().getPersonImageLink() != null)
            {
                Picasso.with(getActivity())
                        .load(mComment.getAuthor().getPersonImageLink())
                        .into(mAvatar);
            }else {
                mAvatar.setBackgroundResource(R.drawable.ic_account_circle_gray_24px);
            }
            Person author = mComment.getAuthor();
            mNickname.setText(getString(R.string.name_and_surname,
                    author.getName(),
                    author.getSurname()));
            mCommentText.setText(mComment.getMessage());
            //--------------------------------------CHANGE--------------------------------------
            mCommentTime.setText(mComment.getDateTime().toString());
        }

        @Override
        public void onClick(View v) {
            // --------------------------------------IMPLEMENT--------------------------------------
        }
    }

    private class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {

        private List<Comment> mCommentItems;

        public CommentAdapter(List<Comment> commentItems) {
            mCommentItems = commentItems;
        }

        public void setCommentItems(List<Comment> commentItems){mCommentItems = commentItems;}

        @Override
        public CommentHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_comment, viewGroup, false);
            return new CommentHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentHolder commentHolder, int position) {
            Comment comment = mCommentItems.get(position);
            commentHolder.bindComment(comment);
        }
        @Override
        public int getItemCount() {
            return mCommentItems.size();
        }
    }

    private class FetchCommentsTask extends AsyncTask<CommentsFilters,Void,List<Comment>> {

        private Position position;

        @Override
        protected List<Comment> doInBackground(CommentsFilters... params) {
            position = params[0].getPosition();
            return new ItemsFetch().downloadComments(params[0]);
        }

        @Override
        protected void onPostExecute(List<Comment> items) {
            if(items != null) {
                switch (position)
                {
                    case FIRST:
                        mCommentItems.addAll(0,items);
                        mAdapter.notifyItemRangeInserted(0,items.size());
                        break;
                    case LAST:
                        mCommentItems.addAll(items);
                        mAdapter.setCommentItems(mCommentItems);  //update adapter (maybe separate method)
                        mAdapter.notifyDataSetChanged();
                        break;
                    case FRESH:
                        mCommentItems = new ArrayList<>(items);
                        mAdapter.setCommentItems(mCommentItems);  //update adapter (maybe separate method)
                        mAdapter.notifyDataSetChanged();
                        break;
                }


            }
        }
    }

}

