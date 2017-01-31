package com.itude.apt.prophiles.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.itude.apt.prophiles.R;

/**
 * Created by athompson on 1/29/17.
 */

public class SingleChoiceDialogFragment extends DialogFragment {

    private static final String ARG_CHOICES = "choices";
    private static final String ARG_SELECTED = "selected";

    private OnSelectedListener mOnSelectedListener;

    public static SingleChoiceDialogFragment newInstance(String[] choices,
                                                         int selected,
                                                         OnSelectedListener onSelectedListener) {
        SingleChoiceDialogFragment fragment = new SingleChoiceDialogFragment();
        fragment.setOnSelectedListener(onSelectedListener);

        Bundle args = new Bundle();
        args.putStringArray(ARG_CHOICES, choices);
        args.putInt(ARG_SELECTED, selected);
        fragment.setArguments(args);

        return fragment;
    }

    private void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        mOnSelectedListener = onSelectedListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String[] choices = args.getStringArray(ARG_CHOICES);
        int selected = args.getInt(ARG_SELECTED);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(
            choices,
            selected,
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mOnSelectedListener.onSelected(which);
                    dismiss();
                }
            }
        );

        return builder.create();
    }

    public interface OnSelectedListener {
        void onSelected(int which);
    }
}
