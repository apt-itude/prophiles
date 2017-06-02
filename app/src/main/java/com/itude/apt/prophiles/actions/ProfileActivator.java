package com.itude.apt.prophiles.actions;

import android.content.Context;
import android.widget.Toast;

import com.itude.apt.prophiles.model.Profile;

/**
 * Created by athompson on 2/5/17.
 */

public class ProfileActivator {

    private Profile mProfile;
    private Context mContext;
    private WifiStateAction mWifiStateAction;
    private BluetoothStateAction mBluetoothStateAction;
    private LocationModeAction mLocationModeAction;
    private VolumeManager mVolumeManager;

    public ProfileActivator(Profile profile, Context context) {
        mProfile = profile;
        mContext = context;
        mWifiStateAction = new WifiStateAction(context);
        mBluetoothStateAction = new BluetoothStateAction();
        mLocationModeAction = new LocationModeAction(context);
        mVolumeManager = new VolumeManager(context);
    }

    public void activate() {
        Toast.makeText(
            mContext,
            "Activating profile " + mProfile.getName(),
            Toast.LENGTH_SHORT
        ).show();

        mWifiStateAction.perform(mProfile.getWifiState());
        mBluetoothStateAction.perform(mProfile.getBluetoothState());
        mLocationModeAction.perform(mProfile.getLocationMode());
        mVolumeManager.setRingVolume(mProfile.getRingVolume());
        mVolumeManager.setMediaVolume(mProfile.getMediaVolume());
        mVolumeManager.setAlarmVolume(mProfile.getAlarmVolume());
    }
}
