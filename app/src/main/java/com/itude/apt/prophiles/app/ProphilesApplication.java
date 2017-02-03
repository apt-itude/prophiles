package com.itude.apt.prophiles.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by athompson on 1/30/17.
 */

public class ProphilesApplication extends Application {

    public static final String REALM_NAME = "prophiles.realm";

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
            .name(REALM_NAME)
            .schemaVersion(1)
            .build();
        Realm.setDefaultConfiguration(config);
    }
}
