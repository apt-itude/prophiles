package com.itude.apt.prophiles.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by athompson on 2/26/17.
 */

public class Permissions {

    public static boolean canWriteSecureSettings(Context context) {
        int writeSecureSettingsPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_SECURE_SETTINGS
        );
        return writeSecureSettingsPermission == PackageManager.PERMISSION_GRANTED;
    }
}
