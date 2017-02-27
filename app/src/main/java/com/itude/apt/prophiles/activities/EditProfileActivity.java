package com.itude.apt.prophiles.activities;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.itude.apt.prophiles.R;
import com.itude.apt.prophiles.fragments.SingleChoiceDialogFragment;
import com.itude.apt.prophiles.fragments.VolumePickerDialogFragment;
import com.itude.apt.prophiles.model.EnableDisableState;
import com.itude.apt.prophiles.model.LocationMode;
import com.itude.apt.prophiles.model.Profile;
import com.itude.apt.prophiles.model.Volume;

import io.realm.Realm;

public class EditProfileActivity extends AppCompatActivity {

    private Realm mRealm;
    private Profile mProfile;

    private String[] mEnableDisableStrings;
    private String[] mLocationModeStrings;

    private AudioManager mAudioManager;

    private EditText mNameEditText;
    private TextView mWifiStateTextView;
    private TextView mBluetoothStateTextView;
    private TextView mLocationModeTextView;
    private TextView mRingVolumeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        mRealm = Realm.getDefaultInstance();
        initializeProfile();

        mEnableDisableStrings = getResources().getStringArray(
            R.array.editProfile_enableDisableOptions
        );
        mLocationModeStrings = getResources().getStringArray(
            R.array.editProfile_locationModeOptions
        );

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        setUpActionBar();
        setUpNameEditText();
        setUpWifiStateView();
        setUpBluetoothStateView();
        setUpLocationModeView();
        setUpRingVolumeView();
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editProfile);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setUpNameEditText() {
        mNameEditText = (EditText) findViewById(R.id.editText_editProfile_name);

        String name = mProfile.getName();
        if (name != null && !name.isEmpty()) {
            mNameEditText.setText(name);
        }
    }

    private void setUpWifiStateView() {
        mWifiStateTextView = (TextView) findViewById(R.id.textView_editProfile_wifiState);
        updateWifiStateTextView();

        View wifiStateView = findViewById(R.id.linearLayout_editProfile_wifiState);
        wifiStateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWifiStateDialogFragment();
            }
        });
    }

    private void updateWifiStateTextView() {
        String currentStateString = mEnableDisableStrings[mProfile.getWifiState().toInt()];
        mWifiStateTextView.setText(currentStateString);

    }

    private void showWifiStateDialogFragment() {
        DialogFragment dialog = SingleChoiceDialogFragment.newInstance(
            mEnableDisableStrings,
            mProfile.getWifiState().toInt(),
            new SingleChoiceDialogFragment.OnSelectedListener() {
                @Override
                public void onSelected(final int which) {
                    setWifiState(EnableDisableState.fromInt(which));
                    updateWifiStateTextView();
                }
            }
        );

        dialog.show(getSupportFragmentManager(), "WifiStateDialogFragment");
    }

    private void setWifiState(final EnableDisableState state) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mProfile.setWifiState(state);
            }
        });

    }

    private void setUpBluetoothStateView() {
        mBluetoothStateTextView = (TextView) findViewById(R.id.textView_editProfile_bluetoothState);
        updateBluetoothStateTextView();

        View wifiStateView = findViewById(R.id.linearLayout_editProfile_bluetoothState);
        wifiStateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBluetoothStateDialogFragment();
            }
        });
    }

    private void updateBluetoothStateTextView() {
        String currentStateString = mEnableDisableStrings[mProfile.getBluetoothState().toInt()];
        mBluetoothStateTextView.setText(currentStateString);

    }

    private void showBluetoothStateDialogFragment() {
        DialogFragment dialog = SingleChoiceDialogFragment.newInstance(
            mEnableDisableStrings,
            mProfile.getBluetoothState().toInt(),
            new SingleChoiceDialogFragment.OnSelectedListener() {
                @Override
                public void onSelected(final int which) {
                    setBluetoothState(EnableDisableState.fromInt(which));
                    updateBluetoothStateTextView();
                }
            }
        );

        dialog.show(getSupportFragmentManager(), "BluetoothStateDialogFragment");
    }

    private void setBluetoothState(final EnableDisableState state) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mProfile.setBluetoothState(state);
            }
        });

    }

    private void setUpLocationModeView() {
        mLocationModeTextView = (TextView) findViewById(R.id.textView_editProfile_locationMode);
        updateLocationModeTextView();

        View view = findViewById(R.id.linearLayout_editProfile_locationMode);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationModeDialogFragment();
            }
        });
    }

    private void updateLocationModeTextView() {
        String currentModeString = mLocationModeStrings[mProfile.getLocationMode().toInt()];
        mLocationModeTextView.setText(currentModeString);

    }

    private void showLocationModeDialogFragment() {
        DialogFragment dialog = SingleChoiceDialogFragment.newInstance(
            mLocationModeStrings,
            mProfile.getLocationMode().toInt(),
            new SingleChoiceDialogFragment.OnSelectedListener() {
                @Override
                public void onSelected(final int which) {
                    setLocationMode(LocationMode.fromInt(which));
                    updateLocationModeTextView();
                }
            }
        );

        dialog.show(getSupportFragmentManager(), "LocationModeDialogFragment");
    }

    private void setLocationMode(final LocationMode mode) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mProfile.setLocationMode(mode);
            }
        });

    }

    private void setUpRingVolumeView() {
        mRingVolumeTextView = (TextView) findViewById(R.id.textView_editProfile_ringVolume);
        updateRingVolumeTextView();

        View ringVolumeView = findViewById(R.id.linearLayout_editProfile_ringVolume);
        ringVolumeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRingVolumeDialogFragment();
            }
        });
    }

    private void updateRingVolumeTextView() {
        Volume volume = mProfile.getRingVolume();
        mRingVolumeTextView.setText(volume.toString(this));
    }

    private void showRingVolumeDialogFragment() {
        DialogFragment dialog = VolumePickerDialogFragment.newInstance(
            R.string.editProfile_ringVolume,
            mProfile.getRingVolume(),
            mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING),
            new VolumePickerDialogFragment.OnSelectedListener() {
                @Override
                public void onSelected(Volume volume) {
                    setRingVolume(volume);
                    updateRingVolumeTextView();
                }
            }
        );

        dialog.show(getSupportFragmentManager(), "RingVolumeDialogFragment");
    }

    private void setRingVolume(final Volume volume) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mProfile.setRingVolume(volume);
            }
        });
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
