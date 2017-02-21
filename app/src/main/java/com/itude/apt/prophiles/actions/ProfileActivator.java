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
    private VolumeManager mVolumeManager;

    public ProfileActivator(Profile profile, Context context) {
        mProfile = profile;
        mWifiStateAction = new WifiStateAction(context);
        mBluetoothStateAction = new BluetoothStateAction();
        mVolumeManager = new VolumeManager(context);
    }

    public void activate() {
        mWifiStateAction.perform(mProfile.getWifiState());
        mBluetoothStateAction.perform(mProfile.getBluetoothState());
        mVolumeManager.setRingVolume(mProfile.getRingVolume());
    }
}
