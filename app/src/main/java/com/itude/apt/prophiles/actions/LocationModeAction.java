package com.itude.apt.prophiles.actions;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.itude.apt.prophiles.model.LocationMode;

/**
 * Created by athompson on 2/23/17.
 */

class LocationModeAction {

    private static String TAG = LocationModeAction.class.getSimpleName();

    private Context mContext;
    private ContentResolver mContentResolver;

    LocationModeAction(Context context) {
        mContext = context;
        mContentResolver = context.getContentResolver();
    }

    void perform(LocationMode mode) {
        if (mode == LocationMode.NO_OVERRIDE) {
            return;
        }

        if (!isPermissionGranted()) {
            Log.w(TAG, "Permissions have not been granted to modify location mode");
            return;
        }

        switch (mode) {
            case OFF:
                setLocationMode(Settings.Secure.LOCATION_MODE_OFF);
                break;
            case DEVICE_ONLY:
                setLocationMode(Settings.Secure.LOCATION_MODE_SENSORS_ONLY);
                break;
            case BATTERY_SAVING:
                setLocationMode(Settings.Secure.LOCATION_MODE_BATTERY_SAVING);
                break;
            case HIGH_ACCURACY:
                setLocationMode(Settings.Secure.LOCATION_MODE_HIGH_ACCURACY);
                break;
        }
    }

    private void setLocationMode(int value) {
        Settings.Secure.putInt(mContentResolver, Settings.Secure.LOCATION_MODE, value);
    }

    private boolean isPermissionGranted() {
        int writeSecureSettingsPermission = ContextCompat.checkSelfPermission(
            mContext,
            Manifest.permission.WRITE_SECURE_SETTINGS
        );
        return writeSecureSettingsPermission == PackageManager.PERMISSION_GRANTED;
    }
}
