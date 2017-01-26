package com.itude.apt.prophiles.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.itude.apt.prophiles.R;
import com.itude.apt.prophiles.model.Profile;

public class CreateProfileActivity extends AppCompatActivity {

    private EditText mNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        setUpActionBar();

        mNameEditText = (EditText) findViewById(R.id.nameEditText);
    }

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
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
        String name = mNameEditText.getText().toString();
        Profile newProfile = new Profile();
        newProfile.name = name;
        newProfile.save();
    }
}
