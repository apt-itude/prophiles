package com.itude.apt.prophiles.services;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.itude.apt.prophiles.R;
import com.itude.apt.prophiles.actions.ProfileActivator;
import com.itude.apt.prophiles.fragments.SingleChoiceDialogFragment;
import com.itude.apt.prophiles.model.Profile;

import io.realm.Realm;
import io.realm.RealmResults;

@TargetApi(Build.VERSION_CODES.N)
public class QuickTileService extends TileService {

    private Realm mRealm;

    @Override
    public void onCreate() {
        super.onCreate();
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onStartListening() {
        Tile tile = getQsTile();

        Profile selectedProfile = Profile.getSelected(mRealm);
        tile.setLabel(selectedProfile.getName());

        tile.setState(Tile.STATE_ACTIVE);

        tile.updateTile();
    }

    @Override
    public void onClick() {
        super.onClick();
        showDialog(createProfileSelectionDialog());
    }

    private Dialog createProfileSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
            getApplicationContext(),
            R.style.DialogTheme
        );

        final RealmResults<Profile> profiles = mRealm.where(Profile.class).findAll();

        String[] names = new String[profiles.size()];
        int selection = -1; // Indicates that no item should be selected initially

        for (int i = 0; i < profiles.size(); i++) {
            Profile profile = profiles.get(i);

            names[i] = profile.getName();

            if (profile.isSelected()) {
                selection = i;
            }
        }

        builder.setSingleChoiceItems(
            names,
            selection,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Profile profile = profiles.get(which);

                    Profile.select(profile.getId(), mRealm);
                    new ProfileActivator(profile, getApplicationContext()).activate();

                    dialog.dismiss();
                }
            }
        );

        return builder.create();
    }

    @Override
    public void onDestroy() {
        mRealm.close();
        super.onDestroy();
    }
}
