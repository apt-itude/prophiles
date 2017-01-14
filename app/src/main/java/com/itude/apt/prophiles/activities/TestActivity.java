package com.itude.apt.prophiles.activities;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.itude.apt.prophiles.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_profile_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });

        final Activity activity = this;

        final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        Switch wifiSwitch = (Switch) findViewById(R.id.wifiSwitch);
        wifiSwitch.setChecked(wifiManager.isWifiEnabled());
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wifiManager.setWifiEnabled(isChecked);
            }
        });

        int modifyPhoneStatePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.MODIFY_PHONE_STATE
        );
        Switch mobileDataSwitch = (Switch) findViewById(R.id.mobileDataSwitch);
        mobileDataSwitch.setEnabled(
            modifyPhoneStatePermission == PackageManager.PERMISSION_GRANTED
        );

        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Switch bluetoothSwitch = (Switch) findViewById(R.id.bluetoothSwitch);
        bluetoothSwitch.setChecked(bluetoothAdapter.isEnabled());
        bluetoothSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bluetoothAdapter.enable();
                } else {
                    bluetoothAdapter.disable();
                }
            }
        });


        final Spinner locationModeSpinner = (Spinner) findViewById(R.id.locationModeSpinner);

        ArrayAdapter<CharSequence> locationModeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.options_location_mode,
            android.R.layout.simple_spinner_item
        );
        locationModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationModeSpinner.setAdapter(locationModeAdapter);

        locationModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.Secure.putInt(
                    activity.getContentResolver(),
                    Settings.Secure.LOCATION_MODE,
                    convertIndexToLocationMode(position)
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        int currentLocationModeIndex = convertLocationModeToIndex(Settings.Secure.getInt(
            activity.getContentResolver(),
            Settings.Secure.LOCATION_MODE,
            Settings.Secure.LOCATION_MODE_OFF
        ));
        locationModeSpinner.setSelection(currentLocationModeIndex);

        int writeSecureSettingsPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_SECURE_SETTINGS
        );
        locationModeSpinner.setEnabled(
            writeSecureSettingsPermission == PackageManager.PERMISSION_GRANTED
        );
    }

    private static int convertIndexToLocationMode(int index) {
        switch (index) {
            case 0:
                return Settings.Secure.LOCATION_MODE_OFF;
            case 1:
                return Settings.Secure.LOCATION_MODE_SENSORS_ONLY;
            case 2:
                return Settings.Secure.LOCATION_MODE_BATTERY_SAVING;
            case 3:
                return Settings.Secure.LOCATION_MODE_HIGH_ACCURACY;
            default:
                return Settings.Secure.LOCATION_MODE_OFF;
        }
    }

    private static int convertLocationModeToIndex(int mode) {
        switch (mode) {
            case Settings.Secure.LOCATION_MODE_OFF:
                return 0;
            case Settings.Secure.LOCATION_MODE_SENSORS_ONLY:
                return 1;
            case Settings.Secure.LOCATION_MODE_BATTERY_SAVING:
                return 2;
            case Settings.Secure.LOCATION_MODE_HIGH_ACCURACY:
                return 3;
            default:
                return 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
