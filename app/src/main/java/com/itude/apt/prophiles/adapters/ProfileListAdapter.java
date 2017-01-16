package com.itude.apt.prophiles.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.itude.apt.prophiles.model.Profile;

import java.util.List;

/**
 * Created by athompson on 1/9/17.
 */

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {

    private List<Profile> mProfiles;
    private RecyclerView mRecyclerView;
    private int mSelectedPosition = RecyclerView.NO_POSITION;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context mContext;
        private ProfileListAdapter mAdapter;
        private CheckedTextView mNameTextView;
        private Profile mProfile;

        public ViewHolder(View profileView, ProfileListAdapter adapter) {
            super(profileView);
            mContext = profileView.getContext();
            mAdapter = adapter;
            mNameTextView = (CheckedTextView) profileView.findViewById(android.R.id.text1);
            mNameTextView.setOnClickListener(this);
        }

        public void bindProfile(Profile profile) {
            mProfile = profile;
            mNameTextView.setText(profile.name);
        }

        public void setSelected(boolean selected) {
            mNameTextView.setChecked(selected);
        }

        @Override
        public void onClick(View v) {
            setSelected(true);
            mAdapter.setSelected(getAdapterPosition());
        }
    }

    public ProfileListAdapter(List<Profile> profiles) {
        mProfiles = profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        mProfiles = profiles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View profileView = LayoutInflater.from(
            parent.getContext()
        ).inflate(
            android.R.layout.simple_list_item_single_choice,
            parent,
            false
        );

        return new ViewHolder(profileView, this);
    }

    public void setSelected(int position) {
        ViewHolder selectedViewHolder = getSelectedViewHolder();
        if (selectedViewHolder != null) {
            selectedViewHolder.setSelected(false);
        }
        mSelectedPosition = position;
    }

    private ViewHolder getSelectedViewHolder() {
        ViewHolder selectedViewHolder = null;
        if (mRecyclerView != null) {
            selectedViewHolder = (ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(
                mSelectedPosition
            );
        }
        return selectedViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Profile profile = mProfiles.get(position);
        holder.bindProfile(profile);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {
        return mProfiles.size();
    }
}
