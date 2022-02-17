package com.wons.myposproject.main_fragments.posfregment.dialog_utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.MessagePattern;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.myposproject.R;
import com.wons.myposproject.main_fragments.posfregment.dialog_utils.PosDialogCallback;

import java.util.ArrayList;

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
        Button button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonD, button0;
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
        Button[] buttons = {button0, button1, button2, button3, button4, button5, button6, button7, button8, button9};
        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    if (tv.getText().toString().isEmpty()) {
                        if (v.getId() == R.id.btn_0) {
                            return;
                        }
                    }
                    tv.setText(tv.getText().toString() + ((Button) v).getText().toString());
                }
            });
        }
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(tv.getText().toString().isEmpty())) {
                    tv.setText(tv.getText().toString().substring(0, tv.getText().toString().length() - 1));
                }
            }
        });
        ((TextView) view.findViewById(R.id.tv_setNumber)).setHint("수량을 적어주세요!!");
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

    public AlertDialog getDialogForBoltQuantity(Context context, PosDialogCallbackForBolt callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("볼트 수량을 입력해주세요");
        builder.setMessage("수량같음은 적은 수량과 일치하는 수량입니다!");
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_view_bolt, null);
        EditText et_boltQuantity = view.findViewById(R.id.et_boltQuantity);
        CheckBox cb_nutSame = view.findViewById(R.id.cb_nutSameQuantity);
        CheckBox cb_nut2 = view.findViewById(R.id.cb_nut2Quantity);
        CheckBox cb_swSame = view.findViewById(R.id.cb_swSameQuantity);
        CheckBox cb_sw2 = view.findViewById(R.id.cb_sw2Quantity);
        CheckBox cb_pwSame = view.findViewById(R.id.cb_pwSameQuantity);
        CheckBox cb_pw2 = view.findViewById(R.id.cb_pw2Quantity);

        cb_nutSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_nut2.setChecked(false);
            }
        });
        cb_nut2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_nutSame.setChecked(false);
            }
        });
        cb_pwSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_pw2.setChecked(false);
            }
        });
        cb_pw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_pwSame.setChecked(false);
            }
        });
        cb_swSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_sw2.setChecked(false);
            }
        });
        cb_sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_swSame.setChecked(false);
            }
        });
        builder.setView(view);

        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("추가", new DialogInterface.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!et_boltQuantity.getText().toString().isEmpty()) {
                    ArrayList<CheckCode> arrayList = new ArrayList<>();
                    CheckBox[] checkBoxes = {cb_pwSame, cb_nutSame, cb_swSame, cb_nut2, cb_pw2, cb_sw2};
                    for (CheckBox checkBox : checkBoxes) {
                        if (checkBox.isChecked()) {
                            switch (checkBox.getId()) {
                                case R.id.cb_nut2Quantity: {
                                    arrayList.add(CheckCode.NUT_2_QUANTITY);
                                    break;
                                }
                                case R.id.cb_pw2Quantity: {
                                    arrayList.add(CheckCode.PW_2_QUANTITY);
                                    break;
                                }
                                case R.id.cb_sw2Quantity: {
                                    arrayList.add(CheckCode.SW_2_QUANTITY);
                                    break;
                                }

                                case R.id.cb_nutSameQuantity: {
                                    arrayList.add(CheckCode.NUT_SAME_QUANTITY);
                                    break;
                                }

                                case R.id.cb_pwSameQuantity: {
                                    arrayList.add(CheckCode.PW_SAME_QUANTITY);
                                    break;
                                }

                                case R.id.cb_swSameQuantity: {
                                    arrayList.add(CheckCode.SW_SAME_QUANTITY);
                                    break;
                                }
                            }
                        }
                    }
                    callback.callBack(arrayList, et_boltQuantity.getText().toString().trim());

                }
            }
        });
        return builder.create();
    }

    public AlertDialog getDialogForQuantity(Context context, PosDialogCallbackForBolt callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("수량을 입력해주세요");
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_view_schedule, null);
        EditText et_quantity = view.findViewById(R.id.et_schedule);
        et_quantity.setInputType(0x00000002);
        builder.setView(view);
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!et_quantity.getText().toString().isEmpty()) {
                    try {
                        Integer.parseInt(et_quantity.getText().toString().trim());
                    } catch (Exception e) {
                        getDialogForQuantity(context, callback).show();
                        Toast.makeText(context, "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                        Log.e("DialogForQuantity", e.getMessage());
                    }
                    callback.callBack(new ArrayList<>(), et_quantity.getText().toString().trim());
                } else {
                    getDialogForQuantity(context, callback).show();
                    Toast.makeText(context, "수량을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();
    }
}
