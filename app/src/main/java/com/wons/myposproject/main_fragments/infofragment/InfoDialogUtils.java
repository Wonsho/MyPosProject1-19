package com.wons.myposproject.main_fragments.infofragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.myposproject.R;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.BarCodeItem;

import java.text.DecimalFormat;

enum ChangeCode {
    UNIT_PRICE,
    NAME;
}

public class InfoDialogUtils {

    public AlertDialog getAskYesOrNoDialog(Context context, InfoDiaLogCallbackForBoolean callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.callBack(false);
            }
        });
        builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.callBack(true);
            }
        });
        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setTitle("삭제하시겠습니까?");
        return builder.create();
    }
    //todo 바코드를 쓰는 다이로그
    public AlertDialog getDialogForWriteBarcode(Context context, InfoDialogCallbackForString callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("바코드를 입력해주세요");
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
        builder.setView(view);
        builder.setNegativeButton("추가 및 조회", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.callBack(tv.getText().toString().trim());
            }
        });
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }


    //todo 수정을 위한 다이로그(수정할 항목을 고름 ) --> 수정할 항목을 수정하는 다이로그를 띄움
    public AlertDialog getDialogForUpdate(Context context, BarCodeItem item, InfoDiaLogCallbackForGetItem callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("수정할 항목을 선택해주세요");
        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("단가 수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogForChangeItem(context, new InfoDialogCallbackForString() {
                    @Override
                    public void callBack(String str) {
                        if (!str.isEmpty()) {
                            callback.callback(new BarCodeItem(item.barCode, item.name, str.trim()));
                            Toast.makeText(context, "단가가 수정되었습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, ChangeCode.UNIT_PRICE);
            }
        });
        builder.setNegativeButton("제품명 수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogForChangeItem(context, new InfoDialogCallbackForString() {
                    @Override
                    public void callBack(String str) {
                        if (!str.isEmpty()) {
                            callback.callback(new BarCodeItem(item.barCode, str.trim(), item.unitPrice));
                            Toast.makeText(context, "제품명이 수정되었습니다", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }, ChangeCode.NAME);
                //todo 수정하는 다이로그 띄우기
            }
        });
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_for_change_item_name_view, null);
        ((TextView) (view.findViewById(R.id.tv_itemName))).setText(item.name);
        ((TextView) (view.findViewById(R.id.tv_itemBarcodeNum))).setText(item.barCode);
        ((TextView) (view.findViewById(R.id.tv_itemUnitPrice))).setText(item.unitPrice);
        builder.setView(view);
        return builder.create();
    }


    //todo 수정할 항목을 수정하는 다이로그
    private void dialogForChangeItem(Context context, InfoDialogCallbackForString callback, ChangeCode changeCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("수정하실 내용을 적어주세요");
        builder.setPositiveButton("돌아가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_view_schedule, null);
        EditText editText = view.findViewById(R.id.et_schedule);
        builder.setView(view);
        builder.setNegativeButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (!((EditText) view.findViewById(R.id.et_schedule)).getText().toString().isEmpty()) {
                    switch (changeCode) {
                    case NAME: {
                    callback.callBack(editText.getText().toString().trim());
                    break;
                    }
                    case UNIT_PRICE: {
                        try {
                            Integer.parseInt(editText.getText().toString());
                            callback.callBack(editText.getText().toString().trim());
                        } catch (Exception e) {
                            Toast.makeText(context, "단가는 숫자로 적어주세요", Toast.LENGTH_SHORT).show();
                            dialogForChangeItem(context, callback, changeCode);
                        }
                        break;
                    }
                }
                } else {
                    dialogForChangeItem(context, callback, changeCode);
                    Toast.makeText(context, "항목을 제대로 적어주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create().show();
    }

    //todo insert하는 다이로그 --> 저장할꺼냐고 확인으로 묻는 다이로그 띄우기 */
    public AlertDialog getDialogForInsertBarcodeItem(Context context, String barcode, InfoDiaLogCallbackForGetItem callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("저장할 제품의 정보를 입력해주세요");
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_barcode_item_write_info_before_insert_view, null);
        EditText et_name = view.findViewById(R.id.et_itemName);
        EditText et_unitPrice = view.findViewById(R.id.et_itemUnitPrice);
        builder.setView(view);
        builder.setNegativeButton("저장하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //todo 스틀링값을 가져와서 둘다 적혀있는지, 단가가 숫자로 적혀있는지 체크 후 확인 다이로그 띄움
                if (!(et_name.getText().toString().isEmpty()) && !(et_unitPrice.getText().toString().isEmpty())) {
                    try {
                        Integer.parseInt(et_unitPrice.getText().toString().trim());
                        showCheckItem(context, et_name.getText().toString().trim(), et_unitPrice.getText().toString().trim(), barcode, new InfoDiaLogCallbackForBoolean() {
                            @Override
                            public void callBack(boolean yesOrNo) {
                                if (yesOrNo) {
                                    Log.e("a", "true");
                                    callback.callback(new BarCodeItem(barcode, et_name.getText().toString().trim(), et_unitPrice.getText().toString().trim())); // 바코드 가져오기
                                }
                                if (!yesOrNo) {
                                    Log.e("a", "false");
                                    getDialogForInsertBarcodeItem(context, barcode, callback).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(context, "단가는 숫자로 입력해주세요", Toast.LENGTH_SHORT).show();
                        Log.e("AlertDialogForInsert", e.getMessage());
                        getDialogForInsertBarcodeItem(context, barcode, callback).show();
                    }
                } else {
                    Toast.makeText(context, "항목을 적어주세요", Toast.LENGTH_SHORT).show();
                    getDialogForInsertBarcodeItem(context, barcode, callback).show();
                }
            }
        });
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    private void showCheckItem(Context context, String name, String unitPrice, String barcode, InfoDiaLogCallbackForBoolean callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("아래의 항목이 맞습니까?");
        DecimalFormat df = new DecimalFormat("###,###,###");
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_for_change_item_name_view, null);
        ((TextView) view.findViewById(R.id.tv_itemName)).setText(name);
        ((TextView) view.findViewById(R.id.tv_itemUnitPrice)).setText(df.format(Integer.parseInt(unitPrice)));
        ((TextView) view.findViewById(R.id.tv_itemBarcodeNum)).setText(barcode);
        builder.setView(view);
        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.callBack(true);
            }
        });
        builder.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.callBack(false);
            }
        });
        builder.create().show();
    }

    public AlertDialog showCheck(Context context, InfoDiaLogCallbackForBoolean callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("외상기록을 삭제 하시겠습니까?");
        builder.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.callBack(true);
            }
        });
        return builder.create();
    }
}
