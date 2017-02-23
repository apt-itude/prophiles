package com.itude.apt.prophiles.model;

import android.content.Context;

import com.itude.apt.prophiles.R;

/**
 * Created by athompson on 2/20/17.
 */

public class Volume {

    public static final int NO_OVERRIDE = -1;

    private int mLevel;

    public Volume(int level) {
        mLevel = level;
    }

    public static Volume fromInt(int level) {
        return new Volume(level);
    }

    public int toInt() {
        return mLevel;
    }

    public boolean isNoOverride() {
        return mLevel == NO_OVERRIDE;
    }

    public String toString(Context context) {
        if (isNoOverride()) {
            return context.getString(R.string.all_no_override);
        } else {
            return Integer.toString(mLevel);
        }
    }
}
