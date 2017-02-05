package com.itude.apt.prophiles.actions;

import android.content.Context;

import com.itude.apt.prophiles.model.Profile;

/**
 * Created by athompson on 2/5/17.
 */

public class ProfileActivator {

    private Profile mProfile;
    private WifiStateAction mWifiStateAction;

    public ProfileActivator(Profile profile, Context context) {
        mProfile = profile;
        mWifiStateAction = new WifiStateAction(context);
    }

    public void activate() {
        mWifiStateAction.perform(mProfile.getWifiState());
    }
}
