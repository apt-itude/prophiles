package com.itude.apt.prophiles.actions;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.itude.apt.prophiles.model.Volume;

/**
 * Created by athompson on 2/20/17.
 */

class VolumeManager {

    private Context mContext;
    private AudioManager mAudioManager;
    private NotificationManager mNotificationManager;

    VolumeManager(Context context) {
        mContext = context;
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mNotificationManager = (NotificationManager) context.getSystemService(
            Context.NOTIFICATION_SERVICE
        );
    }

    void setRingVolume(Volume volume) {
        setVolume(AudioManager.STREAM_RING, volume);
    }

    private void setVolume(int stream, Volume volume) {
        if (volume.isNoOverride()) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            !mNotificationManager.isNotificationPolicyAccessGranted()) {
            grantNotificationPolicyAccess();
            return;
        }

        mAudioManager.setStreamVolume(stream, volume.toInt(), 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void grantNotificationPolicyAccess() {
        mContext.startActivity(new Intent(
            android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS
        ));
    }
}
