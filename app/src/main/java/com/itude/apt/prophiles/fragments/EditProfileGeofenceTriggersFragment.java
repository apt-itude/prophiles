package com.itude.apt.prophiles.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itude.apt.prophiles.R;
import com.itude.apt.prophiles.model.Profile;

import io.realm.Realm;

/**
 * Created by athompson on 6/4/17.
 */

public class EditProfileGeofenceTriggersFragment extends Fragment {

    private static final String TAG = EditProfileGeofenceTriggersFragment.class.getSimpleName();

    private Realm mRealm;
    private Profile mProfile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        String profileId = getArguments().getString(Profile.ID);
        mProfile = Profile.getById(profileId, mRealm);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_editprofile_geofencetriggers, null);

        return rootView;
    }
}
