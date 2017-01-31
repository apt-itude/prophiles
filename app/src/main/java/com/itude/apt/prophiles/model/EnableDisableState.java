package com.itude.apt.prophiles.model;

/**
 * Created by athompson on 1/30/17.
 */

public enum EnableDisableState {
    ENABLE(0),
    DISABLE(1),
    NO_OVERRIDE(2);

    private final int mValue;

    EnableDisableState(int value) {
        mValue = value;
    }

    public int toInt() {
        return mValue;
    }

    public static EnableDisableState fromInt(int value) {
        switch (value) {
            case 0:
                return ENABLE;
            case 1:
                return DISABLE;
            case 2:
                return NO_OVERRIDE;
        }
        return null;
    }
}
