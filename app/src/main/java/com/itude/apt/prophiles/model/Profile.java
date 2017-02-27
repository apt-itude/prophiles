package com.itude.apt.prophiles.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by athompson on 1/9/17.
 */

public class Profile extends RealmObject {

    public static final String ID = "id";

    @PrimaryKey
    private String id;

    private String name;

    private int mWifiState = EnableDisableState.NO_OVERRIDE.toInt();

    private int mBluetoothState = EnableDisableState.NO_OVERRIDE.toInt();

    private int mLocationMode = LocationMode.NO_OVERRIDE.toInt();

    private int mRingVolume = Volume.NO_OVERRIDE;


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

    public void setRingVolume(Volume ringVolume) {
        mRingVolume = ringVolume.toInt();
    }
}
