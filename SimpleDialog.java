package com.oxylane.android.cubeinstore.ui.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.oxylane.android.cubeinstore.R;
import com.oxylane.android.cubeinstore.ui.dialog.BaseDialogFragment;
import com.oxylane.android.cubeinstore.ui.fragment.DefaultDialogBuilder;

/**
 * @author Maxime Jallu
 * @since 02/08/2016
 * <p>
 * Create for CubeInStore - Android (Decathlon)
 * <p>
 * Use this Class for : <br/>
 * ... {DOCUMENTATION}
 */
public class SimpleDialog extends BaseDialogFragment {

    public static final String ARG_TITLE = "arg_title";
    public static final String ARG_MESSAGE = "arg_message";
    public static final String ARG_NEGATIVE_VISIBLE = "arg_negative";

    private String mTitle;
    private String mMessage;
    private boolean mNegativeShow;
    private SimpleDialogPositive mDialogPositiveCallback;
    private SimpleDialogNegative mSimpleDialogNegative;

    public static SimpleDialog newInstance(String title, String message) {
        return newInstance(title, message, true);
    }

    public static SimpleDialog newInstance(String title, String message, boolean showNegative) {
        SimpleDialog dialog = new SimpleDialog();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putBoolean(ARG_NEGATIVE_VISIBLE, showNegative);
        dialog.setArguments(args);
        return dialog;
    }

    public void setDialogPositiveCallback(SimpleDialogPositive dialogPositiveCallback) {
        mDialogPositiveCallback = dialogPositiveCallback;
    }

    public void setSimpleDialogNegative(SimpleDialogNegative simpleDialogNegative) {
        mSimpleDialogNegative = simpleDialogNegative;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = getArguments().getString(ARG_TITLE);
        mMessage = getArguments().getString(ARG_MESSAGE);
        mNegativeShow = getArguments().getBoolean(ARG_NEGATIVE_VISIBLE, true);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = DefaultDialogBuilder.getDefaultBuilder(getActivity(), mTitle, mMessage);
        new AlertDialog.Builder(getContext());

//        final View rootView = getLayoutInflater(savedInstanceState).inflate(R.layout.default_dialog, null);
//
//        TextView titleView = (TextView) rootView.findViewById(R.id.default_dialog_title);
//        TextView textView = (TextView) rootView.findViewById(R.id.default_dialog_text);
//        titleView.setText(mTitle);
//        textView.setText(mMessage);
//
//        dialog.setView(rootView);

        //dialog.setTitle(mTitle);
        //dialog.setMessage(mMessage);

        dialog.setPositiveButton(mNegativeShow ? R.string.yes : android.R.string.ok, (dialog1, which) -> {
            if (getContext() instanceof SimpleDialogPositive) {
                ((SimpleDialogPositive) getContext()).onClickPositiveDialogButton();
            }
            if (mDialogPositiveCallback != null) {
                mDialogPositiveCallback.onClickPositiveDialogButton();
            }
        });

        if (mNegativeShow) {
            dialog.setNegativeButton(R.string.no, (dialog1, which) -> {
                if (getContext() instanceof SimpleDialogNegative) {
                    ((SimpleDialogNegative) getContext()).onClickNegativeDialogButton();
                }
                if (mSimpleDialogNegative != null) {
                    mSimpleDialogNegative.onClickNegativeDialogButton();
                }
            });
        }

        return dialog.create();
    }

    public interface SimpleDialogPositive {
        void onClickPositiveDialogButton();
    }

    public interface SimpleDialogNegative {
        void onClickNegativeDialogButton();
    }

    //TODO : NEUTRAL BUTTON ACTION ... si besoin
}
