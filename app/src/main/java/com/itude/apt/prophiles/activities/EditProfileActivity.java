package com.itude.apt.prophiles.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.itude.apt.prophiles.R;
import com.itude.apt.prophiles.model.Profile;

import io.realm.Realm;

public class EditProfileActivity extends AppCompatActivity {

    private Realm mRealm;
    private Profile mProfile;
    private EditText mNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mRealm = Realm.getDefaultInstance();
        initializeProfile();

        setUpActionBar();
        setUpNameEditText();
    }

    private void initializeProfile() {
        Intent intent = getIntent();

        String profileId = intent.getStringExtra(Profile.ID);
        if (profileId == null) {
            mProfile = new Profile();
        } else {
            mProfile = mRealm.where(Profile.class).equalTo(Profile.ID, profileId).findFirst();
        }

    }

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setUpNameEditText() {
        mNameEditText = (EditText) findViewById(R.id.nameEditText);

        String name = mProfile.getName();
        if (name != null && !name.isEmpty()) {
            mNameEditText.setText(name);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_profile_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void save() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mProfile.setName(mNameEditText.getText().toString());
                realm.copyToRealmOrUpdate(mProfile);
            }
        });
    }
}
