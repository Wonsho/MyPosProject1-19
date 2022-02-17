package com.wons.myposproject.main_fragments.posfregment;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.myposproject.layout_class.BasketLayout;
import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.adapter.Basket_BarcodeList_Adapter;
import com.wons.myposproject.databinding.FragmentPosItemBinding;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.BasketTypeItem;
import com.wons.myposproject.main_fragments.posfregment.dialog_utils.PosDialogCallback;
import com.wons.myposproject.main_fragments.posfregment.dialog_utils.PosDialogUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

class ForBasketLayout {
    //todo 1번 바스켓 2번 바스켓을 임시저장함
    // 라이브데이터에 바스켓아이디를 키값으로 저장
    // 그 map에 바스켓의 객체를 저장
    // 나중에 바스켓의 텍스트를 클릭을 하면 그 키값을 가져와서 뿌려줌*/
    Context context;
    FragmentPosItemBinding binding;
    ListView lv;
    Basket_BarcodeList_Adapter adapter;
    BasketLayout layout;

    ForBasketLayout(Context context, FragmentPosItemBinding binding) {
        this.context = context;
        this.binding = binding;
        this.layout = binding.layoutBasket;
        lv = binding.layoutBasket.findViewById(R.id.lv_basket);
        setView();
        onClick();
        getLiveData();
    }

    private void onClick() {
        layout.findViewById(R.id.tv_clearList_likeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getItems().size() == 0) {
                    Toast.makeText(context, "항목을 먼저 추가해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog alertDialog = new PosDialogUtils().getAlertDialogForBasketDelete(context, new PosDialogCallback() {
                    @Override
                    public void callBack(Boolean yOrN) {
                        if (yOrN) {
                            adapter.itemClear();
                            setView();
                            Toast.makeText(context, "모두 삭제 되었습니다", Toast.LENGTH_SHORT).show();
                            MainViewModel.setLiveDataBasketList(new ArrayList<>());
                        }
                    }
                    @Override
                    public void callBackString(String str) {

                    }
                });
                alertDialog.show();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog alertDialog = new PosDialogUtils().getAlertDialogForBasketDelete(context, new PosDialogCallback() {
                    @Override
                    public void callBack(Boolean yOrN) {
                        if(yOrN) {
                            adapter.deleteItem(position);
                            Toast.makeText(context, "삭제되었습니다", Toast.LENGTH_SHORT).show();
                            setView();
                        }
                    }

                    @Override
                    public void callBackString(String str) {

                    }
                });
                alertDialog.show();
                return false;
            }
        });
    }

    public void callSetViewFromPosMain() {
        setView();
    }

    private void getLiveData() {
        ArrayList<BasketTypeItem> items = MainViewModel.getLiveDataBasketList();
        adapter.addItem(items);
        setView();
        MainViewModel.setLiveDataBasketList(adapter.getItems());
    }

    private void setView() {
        if (lv.getAdapter() == null) {
            lv.setAdapter(new Basket_BarcodeList_Adapter());
        }
        adapter = (Basket_BarcodeList_Adapter) lv.getAdapter();
        adapter.notifyDataSetChanged();
        setPrice();
    }

    private void setPrice() {
        double price = 0.0;
        for(BasketTypeItem item : adapter.getItems()) {
            price += (Double.parseDouble(item.unitPrice) * (Integer.parseInt(item.quantity)));
        }
        DecimalFormat df = new DecimalFormat("###,###,###.##");
        ((TextView)layout.findViewById(R.id.tv_allPrice)).setText(df.format(price));
    }
}
