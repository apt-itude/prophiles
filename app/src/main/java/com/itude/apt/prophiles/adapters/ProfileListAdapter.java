package com.itude.apt.prophiles.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.itude.apt.prophiles.model.Profile;
import com.itude.apt.prophiles.util.RecyclerViewSingleSelector;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by athompson on 1/9/17.
 */

public abstract class ProfileListAdapter extends RealmRecyclerViewAdapter<Profile, ProfileListAdapter.ViewHolder> {

    private RecyclerViewSingleSelector mSelector;

    public ProfileListAdapter(Context context, OrderedRealmCollection<Profile> profiles) {
        super(context, profiles, true);
    }

    public abstract void onProfileSelected(String id);

    public abstract boolean onProfileLongClick(String id);

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
                    onProfileSelected(profile.getId());
                }
            }
        });

        viewHolder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Profile profile = viewHolder.getProfile();
                return onProfileLongClick(profile.getId());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Profile profile = getData().get(position);
        holder.bindProfile(profile);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mSelector = new RecyclerViewSingleSelector(recyclerView);
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
            mNameTextView.setText(profile.getName());
            setSelected(profile.isSelected());
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
