package com.itude.apt.prophiles.model;

/**
 * Created by athompson on 2/23/17.
 */

public enum LocationMode {
    OFF(0),
    DEVICE_ONLY(1),
    BATTERY_SAVING(2),
    HIGH_ACCURACY(3),
    NO_OVERRIDE(4);

    private final int mValue;

    LocationMode(int value) {
        mValue = value;
    }

    public int toInt() {
        return mValue;
    }

    public static LocationMode fromInt(int value) {
        switch (value) {
            case 0:
                return OFF;
            case 1:
                return DEVICE_ONLY;
            case 2:
                return BATTERY_SAVING;
            case 3:
                return HIGH_ACCURACY;
            case 4:
                return NO_OVERRIDE;
        }
        return null;
    }
}
