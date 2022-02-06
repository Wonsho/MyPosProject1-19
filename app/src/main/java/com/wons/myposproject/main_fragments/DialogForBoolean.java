package com.wons.myposproject.main_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;

public class DialogForBoolean {


    public static AlertDialog dialogForYesOrNo(
            @NonNull String title,
            String msg,
            Activity activity,
            DialogCallback callback,
            @NonNull String yesStr,
            @NonNull String noStr
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(noStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onResult(false);
            }
        });
        builder.setNegativeButton(yesStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onResult(true);
            }
        });
        return builder.create();
    }
    DialogForBoolean() {

    }
}
