package com.lhjx.testdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

/**
 * Author: Created by fangmingdong on 2018/7/31-下午2:10
 * Description:
 */
public class TestDialogFragment extends DialogFragment {

    private static final String MSG = "msg";

    private ITestDialog mITestDialog;

    public static TestDialogFragment newInstance(String defaultValue) {
        Bundle args = new Bundle();
        args.putString(MSG, defaultValue);
        TestDialogFragment fragment = new TestDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setITestDialog(ITestDialog ITestDialog) {
        mITestDialog = ITestDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String msg = arguments.getString(MSG, "");
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final EditText et = new EditText(context);
        et.setText(msg);
        et.setSelection(msg.length());

        builder.setView(et);
        builder.setTitle("修改网络");
        builder.setPositiveButton(R.string.td_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mITestDialog != null) {
                    mITestDialog.onResult(et.getText().toString());
                }
            }
        });
        builder.setNegativeButton(R.string.td_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        setCancelable(false);

        return builder.create();
    }
}
