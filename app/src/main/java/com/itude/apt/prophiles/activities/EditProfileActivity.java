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

public class EditProfileActivity extends AppCompatActivity {

    public static String EXTRA_PROFILE_ID = "PROFILE_ID";

    private EditText mNameEditText;
    private Profile mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setUpActionBar();

        Intent intent = getIntent();

        Long profileId = intent.getLongExtra(EXTRA_PROFILE_ID, -1);
        if (profileId > 0) {
            mProfile = Profile.findById(Profile.class, profileId);
        } else {
            mProfile = new Profile();
        }

        setUpNameEditText();
    }

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setUpNameEditText() {
        mNameEditText = (EditText) findViewById(R.id.nameEditText);

        String name = mProfile.name;
        if (name != null && !name.isEmpty()) {
            mNameEditText.setText(mProfile.name);
        }
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
        mProfile.name = mNameEditText.getText().toString();;
        mProfile.save();
    }
}
