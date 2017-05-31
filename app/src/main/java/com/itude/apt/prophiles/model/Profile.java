package com.itude.apt.prophiles.model;

import android.media.AudioManager;
import android.util.Log;

import com.itude.apt.prophiles.util.InvalidStreamError;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by athompson on 1/9/17.
 */

public class Profile extends RealmObject {
    
    public static final String ID = "id";

    private static final String TAG = Profile.class.getSimpleName();

    @PrimaryKey
    private String id;

    private String name;

    private int mWifiState = EnableDisableState.NO_OVERRIDE.toInt();

    private int mBluetoothState = EnableDisableState.NO_OVERRIDE.toInt();

    private int mLocationMode = LocationMode.NO_OVERRIDE.toInt();

    private int mRingVolume = Volume.NO_OVERRIDE;

    private int mMediaVolume = Volume.NO_OVERRIDE;

    private int mAlarmVolume = Volume.NO_OVERRIDE;

    private boolean isSelected = false;


    public Profile() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnableDisableState getWifiState() {
        return EnableDisableState.fromInt(mWifiState);
    }

    public void setWifiState(EnableDisableState state) {
        mWifiState = state.toInt();
    }

    public EnableDisableState getBluetoothState() {
        return EnableDisableState.fromInt(mBluetoothState);
    }

    public void setBluetoothState(EnableDisableState state) {
        mBluetoothState = state.toInt();
    }

    public LocationMode getLocationMode() {
        return LocationMode.fromInt(mLocationMode);
    }

    public void setLocationMode(LocationMode mode) {
        mLocationMode = mode.toInt();
    }

    public Volume getRingVolume() {
        return Volume.fromInt(mRingVolume);
    }

    public void setRingVolume(Volume volume) {
        mRingVolume = volume.toInt();
    }

    public Volume getMediaVolume() {
        return Volume.fromInt(mMediaVolume);
    }

    public void setMediaVolume(Volume volume) {
        mMediaVolume = volume.toInt();
    }

    public Volume getAlarmVolume() {
        return Volume.fromInt(mAlarmVolume);
    }

    public void setAlarmVolume(Volume volume) {
        mAlarmVolume = volume.toInt();
    }

    public Volume getVolume(int stream) {
        switch (stream) {
            case AudioManager.STREAM_RING:
                return getRingVolume();
            case AudioManager.STREAM_MUSIC:
                return getMediaVolume();
            case AudioManager.STREAM_ALARM:
                return getAlarmVolume();
            default:
                Log.e(TAG, "Can't get stream volume");
                throw new InvalidStreamError(stream);
        }
    }

    public void setVolume(int stream, Volume volume) {
        switch (stream) {
            case AudioManager.STREAM_RING:
                setRingVolume(volume);
                break;
            case AudioManager.STREAM_MUSIC:
                setMediaVolume(volume);
                break;
            case AudioManager.STREAM_ALARM:
                setAlarmVolume(volume);
                break;
            default:
                Log.e(TAG, "Can't set stream volume");
                throw new InvalidStreamError(stream);
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public static Profile getById(String id, Realm realm) {
        return realm.where(Profile.class).equalTo("id", id).findFirst();
    }

    public static Profile getSelected(Realm realm) {
        return realm.where(Profile.class).equalTo("isSelected", true).findFirst();
    }

    public static void select(String id, Realm realm) {
        final Profile toSelect = getById(id, realm);

        if (toSelect == null) {
            return;
        }

        final Profile currentlySelected = getSelected(realm);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (currentlySelected != null) {
                    currentlySelected.setSelected(false);
                }
                toSelect.setSelected(true);
            }
        });
    }
}
