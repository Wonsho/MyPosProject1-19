package com.wons.myposproject.main_fragments.infofragment;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.adapter.BarcodeListAdapter;
import com.wons.myposproject.databinding.FragmentInfoBinding;
import com.wons.myposproject.layout_class.Saved_barcode_Item_LayOut;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.BarCodeItem;

import java.util.ArrayList;

public class ForSearedBarcodeItemLayout {
    Saved_barcode_Item_LayOut layOut;
    Context context;
    FragmentInfoBinding binding;
    ListView lv;  //todo 나중에 리사이클러뷰로 변환

    ForSearedBarcodeItemLayout(Context context, FragmentInfoBinding binding) {
        this.context = context;
        this.binding = binding;
        this.layOut = binding.layoutSearchedBarcodeItem;
        lv = layOut.findViewById(R.id.lv_barcodeItem_SavedDB);
        setView();
        onClick();
    }
    //todo 바코드 스캔버튼
    private void onClick() {
        //todo 바코드 적는 버튼
        layOut.findViewById(R.id.btn_write_barcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForWriteBarcode();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog alertDialog = new InfoDialogUtils().getAskYesOrNoDialog(context, new InfoDiaLogCallbackForBoolean() {
                    @Override
                    public void callBack(boolean yesOrNo) {
                        if(yesOrNo) {
                            ListView lv = layOut.findViewById(R.id.lv_barcodeItem_SavedDB);
                            dataChange(ActionCode.DELETE, ((BarcodeListAdapter)lv.getAdapter()).getItems().get(position));
                        }
                        if(!yesOrNo) {
                            dialogForUpdate(((BarcodeListAdapter)lv.getAdapter()).getItems().get(position));
                        }
                    }
                });
                alertDialog.show();
                return false;
            }
        });

    }


    private void dialogForWriteBarcode() {
        AlertDialog alertDialog = new InfoDialogUtils().getDialogForWriteBarcode(context, new InfoDialogCallbackForString() {
            @Override
            public void callBack(String str) {
                if(!str.isEmpty()) {
                    searchInDB(str);
                } else {
                    Toast.makeText(context, "숫자를 입력해주세요",Toast.LENGTH_SHORT).show();
                    dialogForWriteBarcode();
                }
            }
        });
        alertDialog.show();

    }

    public void searchInDB(String barcode) {
        BarCodeItem item = MainViewModel.getBarcodeItem(context, barcode);
        if(item == null) {
            dialogForInsert(barcode);
        } else {
            dialogForUpdate(item);
        }
    }

    private void dialogForUpdate(BarCodeItem item) {
        AlertDialog alertDialog = new InfoDialogUtils().getDialogForUpdate(context, item, new InfoDiaLogCallbackForGetItem() {
            @Override
            public void callback(BarCodeItem item) {
                dataChange(ActionCode.UPDATE, item);
            }
        });
        alertDialog.show();
    }

    private void dialogForInsert(String barcode) {
        AlertDialog dialog = new InfoDialogUtils().getDialogForInsertBarcodeItem(context, barcode, new InfoDiaLogCallbackForGetItem() {
            @Override
            public void callback(BarCodeItem item) {
                dataChange(ActionCode.INSERT, item);
            }
        });
        dialog.show();
    }

    void dataChange(ActionCode actionCode, BarCodeItem item) {
        switch (actionCode) {
            case INSERT: {
                MainViewModel.insertBarcodeItem(context, item.barCode, item.name, item.unitPrice);
                break;
            }
            case UPDATE: {
                MainViewModel.upDateBarcodeItem(context, item);
                break;
            }
            case DELETE: {
                MainViewModel.deleteBarcodeItem(context, item);
                break;
            }
        }
        setView();
    }

    private void setView() {
        if(lv.getAdapter() == null) {
            lv.setAdapter(new BarcodeListAdapter());
        }
        BarcodeListAdapter adapter = (BarcodeListAdapter) lv.getAdapter();
        ArrayList<BarCodeItem> items = MainViewModel.getAllBarcodeItemsInDB(context);
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }
}
