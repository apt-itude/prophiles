package com.itude.apt.prophiles.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.itude.apt.prophiles.R;

/**
 * Created by athompson on 6/4/17.
 */

public class TextEntryDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_PREFILL_TEXT = "prefillText";

    private OnOkListener mOnOkListener;
    private EditText mEditText;

    public static TextEntryDialogFragment newInstance(int titleId, OnOkListener onOkListener) {
        TextEntryDialogFragment fragment = new TextEntryDialogFragment();
        fragment.setOnOkListener(onOkListener);

        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, titleId);
        fragment.setArguments(args);

        return fragment;
    }

    public static TextEntryDialogFragment newInstance(int titleId,
                                                      String prefillText,
                                                      OnOkListener onOkListener) {
        TextEntryDialogFragment fragment = new TextEntryDialogFragment();
        fragment.setOnOkListener(onOkListener);

        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, titleId);
        args.putString(ARG_PREFILL_TEXT, prefillText);
        fragment.setArguments(args);

        return fragment;
    }

    private void setOnOkListener(OnOkListener onOkListener) {
        mOnOkListener = onOkListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
            .setTitle(args.getInt(ARG_TITLE))
            .setView(inflateView())
            .setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mOnOkListener != null) {
                        mOnOkListener.onOk(mEditText.getText().toString());
                    }
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

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_textentrydialog, null);

        mEditText = (EditText) view.findViewById(R.id.editText_textEntryDialog_text);

        String prefillText = args.getString(ARG_PREFILL_TEXT, "");
        mEditText.setText(prefillText);

        return view;
    }

    public interface OnOkListener {
        void onOk(String text);
    }
}
