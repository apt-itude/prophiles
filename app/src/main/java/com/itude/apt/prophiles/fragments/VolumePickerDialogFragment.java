package com.itude.apt.prophiles.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.itude.apt.prophiles.R;
import com.itude.apt.prophiles.model.Volume;

/**
 * Created by athompson on 2/20/17.
 */

public class VolumePickerDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_CURRENT = "current";
    private static final String ARG_MAX = "max";

    private OnSelectedListener mOnSelectedListener;
    private CheckBox mNoOverrideCheckBox;
    private SeekBar mSeekBar;

    public static VolumePickerDialogFragment newInstance(int titleId,
                                                         Volume current,
                                                         int max,
                                                         OnSelectedListener onSelectedListener) {
        VolumePickerDialogFragment fragment = new VolumePickerDialogFragment();
        fragment.setOnSelectedListener(onSelectedListener);

        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, titleId);
        args.putInt(ARG_CURRENT, current.toInt());
        args.putInt(ARG_MAX, max);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
            .setView(inflateView())
            .setTitle(args.getInt(ARG_TITLE))
            .setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int volumeLevel = mNoOverrideCheckBox.isChecked() ?
                                      Volume.NO_OVERRIDE :
                                      mSeekBar.getProgress();
                    mOnSelectedListener.onSelected(new Volume(volumeLevel));
                }
            })
            .setNegativeButton(R.string.all_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                }
            });

        return builder.create();
    }

    private View inflateView() {
        Bundle args = getArguments();
        Volume current = new Volume(args.getInt(ARG_CURRENT));
        int max = args.getInt(ARG_MAX);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_volumepickerdialog, null);

        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar_volumePickerDialog_volume);
        mSeekBar.setMax(max);

        mNoOverrideCheckBox = (CheckBox) view.findViewById(
            R.id.checkBox_volumePickerDialog_noOverride
        );
        mNoOverrideCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSeekBar.setEnabled(!isChecked);
            }
        });

        if (current.isNoOverride()) {
            mNoOverrideCheckBox.setChecked(true);
        } else {
            mNoOverrideCheckBox.setChecked(false);
            mSeekBar.setProgress(current.toInt());
        }

        return view;
    }


    public interface OnSelectedListener {
        void onSelected(Volume volume);
    }
}
