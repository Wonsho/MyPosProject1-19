package com.wons.myposproject.main_fragments.posfregment;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.adapter.Basket_BarcodeList_Adapter;
import com.wons.myposproject.databinding.FragmentPosItemBinding;
import com.wons.myposproject.main_fragments.infofragment.InfoDialogCallbackForString;
import com.wons.myposproject.main_fragments.infofragment.InfoDialogUtils;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.BasketTypeItem;
import com.wons.myposproject.main_fragments.posfregment.dialog_utils.PosDialogCallback;
import com.wons.myposproject.main_fragments.posfregment.dialog_utils.PosDialogUtils;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.BarCodeItem;

public class ForBarCodeLayout {
    Context context;
    FragmentPosItemBinding binding;
    ListView lv;

    public ForBarCodeLayout(Context context, FragmentPosItemBinding binding) {
        this.context = context;
        this.binding = binding;
        lv = binding.lvInPosInBarcode;
        setView();
        onClick();
    }

    private void onClick() {
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog dialog = new PosDialogUtils().getAlertDialogForBasketDelete(context, new PosDialogCallback() {
                    @Override
                    public void callBack(Boolean yOrN) {
                        if (yOrN) {
                            deleteView(position);
                            return;
                        }
                    }

                    @Override
                    public void callBackString(String str) {
                        return;
                    }
                });
                dialog.show();
                return false;
            }
        });

        binding.btnAddBarcodeInPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForBarcode();
            }
        });
    }

    private void dialogForBarcode() {
        AlertDialog alertDialog = new InfoDialogUtils().getDialogForWriteBarcode(context, new InfoDialogCallbackForString() {

            @Override
            public void callBack(String str) {
                if(!str.isEmpty()) {
                    searchBarcodeItemInDB(str);
                }else {
                    Toast.makeText(context, "???????????? ??????????????????",Toast.LENGTH_SHORT).show();
                    dialogForBarcode();
                }
            }
        });
        alertDialog.setTitle("??????????????????????????????");
        alertDialog.show();
    }

    public void searchBarcodeItemInDB(String barcode) {
        BarCodeItem item = MainViewModel.getBarcodeItem(context, barcode);
        if (item == null) {
            Toast.makeText(context, "???????????? ????????? ????????????", Toast.LENGTH_SHORT).show();
            return;
        } else {
            dialog(item);
        }
    }

    private void deleteView(int position) {
        Toast.makeText(context, "?????????????????????", Toast.LENGTH_SHORT).show();
        ((Basket_BarcodeList_Adapter) lv.getAdapter()).deleteItem(position);
        setView();
    }


    private void dialog(BarCodeItem item) {
        AlertDialog dialog = new PosDialogUtils().getDialogForItemQuantity(context, new PosDialogCallback() {
            @Override
            public void callBack(Boolean yOrN) {
                return;
            }

            @Override
            public void callBackString(String str) {
                if (!str.isEmpty()) {
                    ((Basket_BarcodeList_Adapter) lv.getAdapter()).addItem(new BasketTypeItem(item.name, null, item.unitPrice, str.trim(), null));
                    setView();
                    Toast.makeText(context, "?????? ???????????????", Toast.LENGTH_SHORT).show();
                } else {
                    dialog(item);
                    Toast.makeText(context, "????????? ??????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void setView() {
        if (lv.getAdapter() == null) {
            lv.setAdapter(new Basket_BarcodeList_Adapter());
        }
        ((Basket_BarcodeList_Adapter) lv.getAdapter()).notifyDataSetChanged();
    }

}
