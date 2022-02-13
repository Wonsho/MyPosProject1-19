package com.wons.myposproject.main_fragments.posfregment.dialog_utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wons.myposproject.R;
import com.wons.myposproject.main_fragments.posfregment.dialog_utils.PosDialogCallback;

public final class PosDialogUtils {
    public AlertDialog getAlertDialogForBasketDelete(Context context, PosDialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("삭제하시겠습니까?");
        builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.callBack(true);
            }
        });
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    public AlertDialog getDialogForItemQuantity(Context context, PosDialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("수량을 입력해주세요");
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_quantity_view, null);
        TextView tv = view.findViewById(R.id.tv_setNumber);
        Button button1, button2,button3, button4, button5, button6, button7, button8, button9,buttonD, button0;
        button1 = view.findViewById(R.id.btn_1);
        button2 = view.findViewById(R.id.btn_2);
        button3 = view.findViewById(R.id.btn_3);
        button4 = view.findViewById(R.id.btn_4);
        button5 = view.findViewById(R.id.btn_5);
        button6 = view.findViewById(R.id.btn_6);
        button7 = view.findViewById(R.id.btn_7);
        button8 = view.findViewById(R.id.btn_8);
        button9 = view.findViewById(R.id.btn_9);
        buttonD = view.findViewById(R.id.btn_delete);
        button0 = view.findViewById(R.id.btn_0);
        Button[] buttons = {button0,button1, button2,button3, button4, button5, button6, button7, button8, button9};
        for(Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    if(tv.getText().toString().isEmpty()) {
                        if(v.getId() == R.id.btn_0) {
                            return;
                        }
                    }
                    tv.setText(tv.getText().toString()+((Button)v).getText().toString());
                }
            });
        }
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(tv.getText().toString().isEmpty())) {
                   tv.setText( tv.getText().toString().substring(0, tv.getText().toString().length()-1));
                }
            }
        });
        ((TextView)view.findViewById(R.id.tv_setNumber)).setHint("수량을 적어주세요!!");
        builder.setView(view);
        builder.setNegativeButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.callBackString(tv.getText().toString().trim());
            }
        });
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
