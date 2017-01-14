package com.itude.apt.prophiles.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.itude.apt.prophiles.R;
import com.itude.apt.prophiles.adapters.ProfileListAdapter;
import com.itude.apt.prophiles.model.Profile;

import java.util.List;

public class ProfileListActivity extends AppCompatActivity {

    private ProfileListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView profileListView = (RecyclerView) findViewById(R.id.content_main_profile_list);
        profileListView.setHasFixedSize(true);

        RecyclerView.LayoutManager profileListLayoutManager = new LinearLayoutManager(this);
        profileListView.setLayoutManager(profileListLayoutManager);

        List<Profile> profiles = Profile.listAll(Profile.class);
        mAdapter = new ProfileListAdapter(profiles);
        profileListView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_profile_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProfile();
            }
        });
    }

    private void createProfile() {
        Intent intent = new Intent(this, CreateProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Profile> profiles = Profile.listAll(Profile.class);
        mAdapter.setProfiles(profiles);
        mAdapter.notifyDataSetChanged();
    }
}
