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

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class ProfileListActivity extends AppCompatActivity {

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        mRealm = Realm.getDefaultInstance();

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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        OrderedRealmCollection<Profile> profiles = mRealm.where(Profile.class).findAll();
        recyclerView.setAdapter(new ProfileListAdapter(this, profiles) {

            @Override
            public void onProfileSelected(String id) {
                Profile profile = getProfile(id);
                Toast.makeText(
                    ProfileListActivity.this,
                    "Selected profile " + profile.getName(),
                    Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public boolean onProfileLongClick(String id) {
                startProfileActionMode(id);
                return true;
            }
        });
    }

    private void startProfileActionMode(final String profileId) {
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
                        editProfile(profileId);
                        mode.finish();
                        return true;
                    case R.id.action_delete:
                        deleteProfile(profileId);
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

    private Profile getProfile(String id) {
        return mRealm.where(Profile.class).equalTo(Profile.ID, id).findFirst();
    }

    private void createProfile() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void editProfile(String id) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra(Profile.ID, id);
        startActivity(intent);
    }

    private void deleteProfile(final String id) {
        // TODO: 1/31/17 add onSuccess and onError
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Profile.class)
                    .equalTo(Profile.ID, id)
                    .findAll()
                    .deleteAllFromRealm();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
