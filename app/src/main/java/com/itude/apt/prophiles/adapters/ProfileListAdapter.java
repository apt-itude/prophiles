package com.itude.apt.prophiles.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itude.apt.prophiles.model.Profile;

import java.util.List;

/**
 * Created by athompson on 1/9/17.
 */

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {

    private List<Profile> mProfiles;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;

        public ViewHolder(View profileView) {
            super(profileView);
            mNameTextView = (TextView) profileView.findViewById(android.R.id.text1);
        }

        public void bindProfile(Profile profile) {
            mNameTextView.setText(profile.name);
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

        return new ViewHolder(profileView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Profile profile = mProfiles.get(position);
        holder.bindProfile(profile);
    }

    @Override
    public int getItemCount() {
        return mProfiles.size();
    }
}
