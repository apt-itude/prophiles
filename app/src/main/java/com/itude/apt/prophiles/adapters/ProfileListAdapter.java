package com.itude.apt.prophiles.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.itude.apt.prophiles.model.Profile;

import java.util.List;

/**
 * Created by athompson on 1/9/17.
 */

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {

    private List<Profile> mProfiles;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context mContext;
        private Profile mProfile;
        private TextView mNameTextView;

        public ViewHolder(View profileView) {
            super(profileView);
            mContext = profileView.getContext();
            mNameTextView = (TextView) profileView.findViewById(android.R.id.text1);
        }

        public void bindProfile(Profile profile) {
            mProfile = profile;
            mNameTextView.setText(profile.name);
            mNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(
                mContext,
                "Selected profile " + mProfile.name,
                Toast.LENGTH_SHORT
            ).show();
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
