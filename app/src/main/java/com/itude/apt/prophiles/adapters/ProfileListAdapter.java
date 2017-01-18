package com.itude.apt.prophiles.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.itude.apt.prophiles.model.Profile;
import com.itude.apt.prophiles.util.RecyclerViewSingleSelector;

import java.util.List;

/**
 * Created by athompson on 1/9/17.
 */

public abstract class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {

    private List<Profile> mProfiles;
    private RecyclerViewSingleSelector mSelector;

    protected ProfileListAdapter(List<Profile> profiles) {
        mProfiles = profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        mProfiles = profiles;
    }

    public abstract void onProfileSelected(Profile profile);
    public abstract boolean onProfileLongClick(Profile profile);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View profileView = LayoutInflater.from(
            parent.getContext()
        ).inflate(
            android.R.layout.simple_list_item_single_choice,
            parent,
            false
        );

        final ViewHolder viewHolder = new ViewHolder(profileView);

        viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelector != null) {
                    int position = viewHolder.getAdapterPosition();
                    mSelector.select(position);

                    Profile profile = viewHolder.getProfile();
                    onProfileSelected(profile);
                }
            }
        });

        viewHolder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Profile profile = viewHolder.getProfile();
                return onProfileLongClick(profile);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Profile profile = mProfiles.get(position);
        holder.bindProfile(profile);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mSelector = new RecyclerViewSingleSelector(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mProfiles.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder
        implements RecyclerViewSingleSelector.Selectable {

        private CheckedTextView mNameTextView;
        private Profile mProfile;

        ViewHolder(View profileView) {
            super(profileView);
            mNameTextView = (CheckedTextView) profileView.findViewById(android.R.id.text1);
        }

        @Override
        public void setSelected(boolean selected) {
            mNameTextView.setChecked(selected);
        }

        void bindProfile(Profile profile) {
            mProfile = profile;
            mNameTextView.setText(profile.name);
        }

        Profile getProfile() {
            return mProfile;
        }

        void setOnClickListener(View.OnClickListener listener) {
            mNameTextView.setOnClickListener(listener);
        }

        void setOnLongClickListener(View.OnLongClickListener listener) {
            mNameTextView.setOnLongClickListener(listener);
        }
    }
}
