package com.itude.apt.prophiles.actions;

import android.content.Context;

import com.itude.apt.prophiles.model.Profile;

/**
 * Created by athompson on 2/5/17.
 */

public class ProfileActivator {

    private Profile mProfile;
    private WifiStateAction mWifiStateAction;
    private BluetoothStateAction mBluetoothStateAction;
    private LocationModeAction mLocationModeAction;
    private VolumeManager mVolumeManager;

    public ProfileActivator(Profile profile, Context context) {
        mProfile = profile;
        mWifiStateAction = new WifiStateAction(context);
        mBluetoothStateAction = new BluetoothStateAction();
        mLocationModeAction = new LocationModeAction(context);
        mVolumeManager = new VolumeManager(context);
    }

    public void activate() {
        mWifiStateAction.perform(mProfile.getWifiState());
        mBluetoothStateAction.perform(mProfile.getBluetoothState());
        mLocationModeAction.perform(mProfile.getLocationMode());
        mVolumeManager.setRingVolume(mProfile.getRingVolume());
        mVolumeManager.setMediaVolume(mProfile.getMediaVolume());
        mVolumeManager.setAlarmVolume(mProfile.getAlarmVolume());
    }
}
