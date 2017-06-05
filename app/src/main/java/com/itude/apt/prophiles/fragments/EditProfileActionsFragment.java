package com.itude.apt.prophiles.fragments;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itude.apt.prophiles.R;
import com.itude.apt.prophiles.model.EnableDisableState;
import com.itude.apt.prophiles.model.LocationMode;
import com.itude.apt.prophiles.model.Profile;
import com.itude.apt.prophiles.model.Volume;
import com.itude.apt.prophiles.util.InvalidStreamError;
import com.itude.apt.prophiles.util.Permissions;

import io.realm.Realm;

/**
 * Created by athompson on 6/4/17.
 */

public class EditProfileActionsFragment extends Fragment {

    private static final String TAG = EditProfileActionsFragment.class.getSimpleName();

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        String profileId = getArguments().getString(Profile.ID);
        mProfile = Profile.getById(profileId, mRealm);

        mEnableDisableStrings = getResources().getStringArray(
            R.array.editProfile_enableDisableOptions
        );
        mLocationModeStrings = getResources().getStringArray(
            R.array.editProfile_locationModeOptions
        );

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_editprofile_actions, null);

        setUpWifiStateView(rootView);
        setUpBluetoothStateView(rootView);
        setUpLocationModeView(rootView);
        setUpRingVolumeView(rootView);
        setUpMediaVolumeView(rootView);
        setUpAlarmVolumeView(rootView);

        return rootView;
    }

    private void setUpWifiStateView(View rootView) {
        mWifiStateTextView = (TextView) rootView.findViewById(R.id.textView_editProfile_wifiState);
        updateWifiStateTextView();

        View wifiStateView = rootView.findViewById(R.id.linearLayout_editProfile_wifiState);
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

        dialog.show(getFragmentManager(), "WifiStateDialogFragment");
    }

    private void setWifiState(final EnableDisableState state) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mProfile.setWifiState(state);
            }
        });

    }

    private void setUpBluetoothStateView(View rootView) {
        mBluetoothStateTextView = (TextView) rootView.findViewById(
            R.id.textView_editProfile_bluetoothState
        );
        updateBluetoothStateTextView();

        View wifiStateView = rootView.findViewById(R.id.linearLayout_editProfile_bluetoothState);
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

        dialog.show(getFragmentManager(), "BluetoothStateDialogFragment");
    }

    private void setBluetoothState(final EnableDisableState state) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mProfile.setBluetoothState(state);
            }
        });

    }

    private void setUpLocationModeView(View rootView) {
        mLocationModeTextView = (TextView) rootView.findViewById(
            R.id.textView_editProfile_locationMode
        );
        updateLocationModeTextView();

        View locationModeView = rootView.findViewById(R.id.linearLayout_editProfile_locationMode);
        locationModeView.setOnClickListener(new View.OnClickListener() {
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

        if (Permissions.canWriteSecureSettings(getActivity())) {
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

        dialog.show(getFragmentManager(), "LocationModeDialogFragment");
    }

    private void setLocationMode(final LocationMode mode) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mProfile.setLocationMode(mode);
            }
        });

    }

    private void setUpRingVolumeView(View rootView) {
        mRingVolumeTextView = (TextView) rootView.findViewById(
            R.id.textView_editProfile_ringVolume
        );
        updateVolumeTextView(AudioManager.STREAM_RING);

        View ringVolumeView = rootView.findViewById(R.id.linearLayout_editProfile_ringVolume);
        ringVolumeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVolumeDialogFragment(AudioManager.STREAM_RING);
            }
        });
    }

    private void setUpMediaVolumeView(View rootView) {
        mMediaVolumeTextView = (TextView) rootView.findViewById(
            R.id.textView_editProfile_mediaVolume
        );
        updateVolumeTextView(AudioManager.STREAM_MUSIC);

        View ringVolumeView = rootView.findViewById(R.id.linearLayout_editProfile_mediaVolume);
        ringVolumeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVolumeDialogFragment(AudioManager.STREAM_MUSIC);
            }
        });
    }

    private void setUpAlarmVolumeView(View rootView) {
        mAlarmVolumeTextView = (TextView) rootView.findViewById(
            R.id.textView_editProfile_alarmVolume
        );
        updateVolumeTextView(AudioManager.STREAM_ALARM);

        View ringVolumeView = rootView.findViewById(R.id.linearLayout_editProfile_alarmVolume);
        ringVolumeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVolumeDialogFragment(AudioManager.STREAM_ALARM);
            }
        });
    }

    private void updateVolumeTextView(int stream) {
        Volume volume = mProfile.getVolume(stream);
        getVolumeTextView(stream).setText(volume.toString(getActivity()));
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

        dialog.show(getFragmentManager(), "VolumeDialogFragment");
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
}
