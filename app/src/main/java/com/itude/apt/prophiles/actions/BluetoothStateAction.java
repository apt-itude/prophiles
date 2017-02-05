package com.itude.apt.prophiles.actions;

import android.bluetooth.BluetoothAdapter;

import com.itude.apt.prophiles.model.EnableDisableState;

/**
 * Created by athompson on 2/5/17.
 */

class BluetoothStateAction {

    private BluetoothAdapter mBluetoothAdapter;

    BluetoothStateAction() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    void perform(EnableDisableState state) {
        switch (state) {
            case ENABLE:
                mBluetoothAdapter.enable();
                break;
            case DISABLE:
                mBluetoothAdapter.disable();
                break;
            case NO_OVERRIDE:
        }
    }
}
