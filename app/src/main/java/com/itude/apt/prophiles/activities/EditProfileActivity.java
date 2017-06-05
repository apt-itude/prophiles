package com.itude.apt.prophiles.activities;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.itude.apt.prophiles.R;
import com.itude.apt.prophiles.fragments.SingleChoiceDialogFragment;
import com.itude.apt.prophiles.fragments.TextEntryDialogFragment;
import com.itude.apt.prophiles.fragments.VolumePickerDialogFragment;
import com.itude.apt.prophiles.fragments.WarningDialogFragment;
import com.itude.apt.prophiles.model.EnableDisableState;
import com.itude.apt.prophiles.model.LocationMode;
import com.itude.apt.prophiles.model.Profile;
import com.itude.apt.prophiles.model.Volume;
import com.itude.apt.prophiles.util.InvalidStreamError;
import com.itude.apt.prophiles.util.Permissions;

import io.realm.Realm;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = EditProfileActivity.class.getSimpleName();

    private Realm mRealm;
    private Profile mProfile;

    private String[] mEnableDisableStrings;
    private String[] mLocationModeStrings;

    private AudioManager mAudioManager;

    private TextView mWifiStateTextView;
    private TextView mBluetoothStateTextView;
    private TextView mLocationModeTextView;
    private TextView mRingVolumeTextView;
    private TextView mMediaVolumeTextView;
    private TextView mAlarmVolumeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        mRealm = Realm.getDefaultInstance();
        initializeProfile();

        setTitle(mProfile.getName());

        mEnableDisableStrings = getResources().getStringArray(
            R.array.editProfile_enableDisableOptions
        );
        mLocationModeStrings = getResources().getStringArray(
            R.array.editProfile_locationModeOptions
        );

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        setUpActionBar();
        setUpWifiStateView();
        setUpBluetoothStateView();
        setUpLocationModeView();
        setUpRingVolumeView();
        setUpMediaVolumeView();
        setUpAlarmVolumeView();
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
        DialogFragment dialog;

        if (Permissions.canWriteSecureSettings(this)) {
            dialog = SingleChoiceDialogFragment.newInstance(
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
        } else {
            dialog = WarningDialogFragment.newInstance(
                R.string.editProfile_permissionNotGranted,
                R.string.editProfile_grantWriteSecureSettingsPermission
            );
        }

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
        updateVolumeTextView(AudioManager.STREAM_RING);

        View ringVolumeView = findViewById(R.id.linearLayout_editProfile_ringVolume);
        ringVolumeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVolumeDialogFragment(AudioManager.STREAM_RING);
            }
        });
    }

    private void setUpMediaVolumeView() {
        mMediaVolumeTextView = (TextView) findViewById(R.id.textView_editProfile_mediaVolume);
        updateVolumeTextView(AudioManager.STREAM_MUSIC);

        View ringVolumeView = findViewById(R.id.linearLayout_editProfile_mediaVolume);
        ringVolumeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVolumeDialogFragment(AudioManager.STREAM_MUSIC);
            }
        });
    }

    private void setUpAlarmVolumeView() {
        mAlarmVolumeTextView = (TextView) findViewById(R.id.textView_editProfile_alarmVolume);
        updateVolumeTextView(AudioManager.STREAM_ALARM);

        View ringVolumeView = findViewById(R.id.linearLayout_editProfile_alarmVolume);
        ringVolumeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVolumeDialogFragment(AudioManager.STREAM_ALARM);
            }
        });
    }

    private void updateVolumeTextView(int stream) {
        Volume volume = mProfile.getVolume(stream);
        getVolumeTextView(stream).setText(volume.toString(this));
    }

    private TextView getVolumeTextView(int stream) {
        switch (stream) {
            case AudioManager.STREAM_RING:
                return mRingVolumeTextView;
            case AudioManager.STREAM_MUSIC:
                return mMediaVolumeTextView;
            case AudioManager.STREAM_ALARM:
                return mAlarmVolumeTextView;
            default:
                Log.e(TAG, "Can't get volume TextView");
                throw new InvalidStreamError(stream);
        }
    }

    private void showVolumeDialogFragment(final int stream) {
        DialogFragment dialog = VolumePickerDialogFragment.newInstance(
            getVolumeTitleId(stream),
            mProfile.getVolume(stream),
            mAudioManager.getStreamMaxVolume(stream),
            new VolumePickerDialogFragment.OnSelectedListener() {
                @Override
                public void onSelected(Volume volume) {
                    setVolume(stream, volume);
                    updateVolumeTextView(stream);
                }
            }
        );

        dialog.show(getSupportFragmentManager(), "RingVolumeDialogFragment");
    }

    private int getVolumeTitleId(int stream) {
        switch (stream) {
            case AudioManager.STREAM_RING:
                return R.string.editProfile_ringVolume;
            case AudioManager.STREAM_MUSIC:
                return R.string.editProfile_mediaVolume;
            case AudioManager.STREAM_ALARM:
                return R.string.editProfile_alarmVolume;
            default:
                Log.e(TAG, "Can't get volume title");
                throw new InvalidStreamError(stream);
        }
    }

    private void setVolume(final int stream, final Volume volume) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mProfile.setVolume(stream, volume);
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
}
