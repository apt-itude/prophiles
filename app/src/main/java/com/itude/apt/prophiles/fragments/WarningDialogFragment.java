package com.itude.apt.prophiles.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.itude.apt.prophiles.R;

/**
 * Created by athompson on 2/26/17.
 */

public class WarningDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";

    public static WarningDialogFragment newInstance(int titleId, int messageId) {
        WarningDialogFragment fragment = new WarningDialogFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, titleId);
        args.putInt(ARG_MESSAGE, messageId);
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
            .setTitle(args.getInt(ARG_TITLE))
            .setMessage(args.getInt(ARG_MESSAGE))
            .setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                }
            });

        return builder.create();
    }
}
