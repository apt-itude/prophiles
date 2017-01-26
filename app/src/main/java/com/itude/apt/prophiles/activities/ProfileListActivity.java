package com.itude.apt.prophiles.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.itude.apt.prophiles.R;
import com.itude.apt.prophiles.adapters.ProfileListAdapter;
import com.itude.apt.prophiles.model.Profile;

public class ProfileListActivity extends AppCompatActivity {

    private ProfileListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        setUpActionBar();
        setUpRecyclerView();
        setUpFloatingActionButton();
    }

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.content_main_profile_list);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ProfileListAdapter() {

            @Override
            public void onProfileSelected(Profile profile, int position) {
                Toast.makeText(
                    ProfileListActivity.this,
                    "Selected profile " + profile.name,
                    Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public boolean onProfileLongClick(Profile profile, int position) {
                startProfileActionMode(profile, position);
                return true;
            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    private void startProfileActionMode(final Profile profile, final int position) {
        startSupportActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_profile_list_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        editProfile(profile);
                        mode.finish();
                        return true;
                    case R.id.action_delete:
                        Toast.makeText(
                            ProfileListActivity.this,
                            "Deleting profile " + profile.name,
                            Toast.LENGTH_SHORT
                        ).show();

                        mAdapter.deleteProfile(position);

                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }
        });
    }

    private void setUpFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_profile_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProfile();
            }
        });
    }

    private void createProfile() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void editProfile(Profile profile) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra(EditProfileActivity.EXTRA_PROFILE_ID, profile.getId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.refresh();
    }
}
