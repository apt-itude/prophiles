package com.itude.apt.prophiles.actions;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.itude.apt.prophiles.model.EnableDisableState;

/**
 * Created by athompson on 2/5/17.
 */

class WifiStateAction {

    private WifiManager mWifiManager;

    WifiStateAction(Context context) {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    void perform(EnableDisableState state) {
        switch (state) {
            case ENABLE:
                mWifiManager.setWifiEnabled(true);
                break;
            case DISABLE:
                mWifiManager.setWifiEnabled(false);
                break;
            case NO_OVERRIDE:
        }
    }
}
