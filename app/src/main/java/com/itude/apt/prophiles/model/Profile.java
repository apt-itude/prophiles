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

    private int wifiState = EnableDisableState.NO_OVERRIDE.toInt();

    private int bluetoothState = EnableDisableState.NO_OVERRIDE.toInt();

    private int locationMode = LocationMode.NO_OVERRIDE.toInt();

    private int ringVolume = Volume.NO_OVERRIDE;

    private int mediaVolume = Volume.NO_OVERRIDE;

    private int alarmVolume = Volume.NO_OVERRIDE;

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
        return EnableDisableState.fromInt(wifiState);
    }

    public void setWifiState(EnableDisableState state) {
        wifiState = state.toInt();
    }

    public EnableDisableState getBluetoothState() {
        return EnableDisableState.fromInt(bluetoothState);
    }

    public void setBluetoothState(EnableDisableState state) {
        bluetoothState = state.toInt();
    }

    public LocationMode getLocationMode() {
        return LocationMode.fromInt(locationMode);
    }

    public void setLocationMode(LocationMode mode) {
        locationMode = mode.toInt();
    }

    public Volume getRingVolume() {
        return Volume.fromInt(ringVolume);
    }

    public void setRingVolume(Volume volume) {
        ringVolume = volume.toInt();
    }

    public Volume getMediaVolume() {
        return Volume.fromInt(mediaVolume);
    }

    public void setMediaVolume(Volume volume) {
        mediaVolume = volume.toInt();
    }

    public Volume getAlarmVolume() {
        return Volume.fromInt(alarmVolume);
    }

    public void setAlarmVolume(Volume volume) {
        alarmVolume = volume.toInt();
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
