package com.itude.apt.prophiles.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.itude.apt.prophiles.R;
import com.itude.apt.prophiles.fragments.EditProfileActionsFragment;
import com.itude.apt.prophiles.fragments.EditProfileGeofenceTriggersFragment;
import com.itude.apt.prophiles.fragments.TextEntryDialogFragment;
import com.itude.apt.prophiles.model.Profile;

import io.realm.Realm;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = EditProfileActivity.class.getSimpleName();

    private Realm mRealm;
    private Profile mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        mRealm = Realm.getDefaultInstance();
        initializeProfile();

        setTitle(mProfile.getName());
        setUpActionBar();
        setUpPagerAdapter();
    }

    private void initializeProfile() {
        Intent intent = getIntent();

        String profileId = intent.getStringExtra(Profile.ID);
        if (profileId == null) {
            Log.e(TAG, "Intent is missing '" + Profile.ID + "' extra");
            finish();
        } else {
            mProfile = Profile.getById(profileId, mRealm);
        }

    }

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editProfile);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setUpPagerAdapter() {
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager_editProfile);
        pager.setAdapter(new EditProfilePagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_editprofile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rename:
                showRenameDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showRenameDialog() {
        TextEntryDialogFragment fragment = TextEntryDialogFragment.newInstance(
            R.string.editProfile_name,
            mProfile.getName(),
            new TextEntryDialogFragment.OnOkListener() {
                @Override
                public void onOk(String text) {
                    rename(text);
                }
            }
        );

        fragment.show(getSupportFragmentManager(), "RenameProfileDialogFragment");
    }

    private void rename(final String name) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mProfile.setName(name);
            }
        });
        setTitle(name);
    }

    private class EditProfilePagerAdapter extends FragmentPagerAdapter {

        public EditProfilePagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return makeActionsFragment();
                case 1:
                    return makeGeofenceTriggersFragment();
                default:
                    return null;
            }
        }

        private Fragment makeActionsFragment() {
            Fragment fragment = new EditProfileActionsFragment();
            fragment.setArguments(makeArgsWithProfileId());
            return fragment;
        }

        private Bundle makeArgsWithProfileId() {
            Bundle args = new Bundle();
            args.putString(Profile.ID, mProfile.getId());
            return args;
        }

        private Fragment makeGeofenceTriggersFragment() {
            Fragment fragment = new EditProfileGeofenceTriggersFragment();
            fragment.setArguments(makeArgsWithProfileId());
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] titles = getResources().getStringArray(R.array.editProfile_pageTitles);
            return titles[position];
        }
    }
}
