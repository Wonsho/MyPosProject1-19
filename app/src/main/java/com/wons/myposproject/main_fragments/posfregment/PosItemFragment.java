package com.wons.myposproject.main_fragments.posfregment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.adapter.Basket_BarcodeList_Adapter;
import com.wons.myposproject.adapter.ItemValueAdapter;
import com.wons.myposproject.adapter.PosItemMenu_Adapter;
import com.wons.myposproject.databinding.FragmentPosItemBinding;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.BasketTypeItem;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.Group;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.Item;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.SelectedItem;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

enum ItemViewCode {
    BARCODE,
    ITEM_LIST_VIEW;
}


public class PosItemFragment extends Fragment {
    private FragmentPosItemBinding binding;
    public static final String TAG = "PosItemFragment";
    private Fragment fragment;
    private ForBarCodeLayout forBarcodeLayOut;
    private ForBasketLayout forBasketLayout;
    private ForItemLayout forItemLayout;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPosItemBinding.inflate(inflater, container, false);
        PosItemMenu_Adapter adapter = new PosItemMenu_Adapter();
        binding.lvExpandable.setAdapter(adapter);
        adapter.putGroupList(new ArrayList<>(Arrays.asList(Group.values())));
        adapter.notifyDataSetChanged();
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            binding.lvExpandable.expandGroup(i);
        }
        fragment = this;
        forBarcodeLayOut = new ForBarCodeLayout(getContext(), binding);
        forBasketLayout = new ForBasketLayout(getContext(), binding);
        forItemLayout = new ForItemLayout(getContext(), binding);

        //todo 아이템 메뉴 클릭
        binding.lvExpandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                binding.layoutBarcode.setVisibility(View.GONE);
                binding.layoutItem.setVisibility(View.VISIBLE);
                String child = ((PosItemMenu_Adapter)(binding.lvExpandable.getExpandableListAdapter())).getChild(groupPosition, childPosition);
                Item[] items = Item.values();
                Item item = null;
                for(Item item1 : items) {
                    if(item1.koreanName.equals(child)) {
                        item = item1;
                    } else {
                        Log.e(TAG, "Menu is null");
                    }
                }
                forItemLayout.getSelectedItemNameFromMain(item);
                forItemLayout.setAllViewInit();
                return false;
            }
        });

        //todo 비코드 스캔 버튼
        binding.tvBarcodeLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator.forSupportFragment(fragment).initiateScan();
                binding.layoutBarcode.setVisibility(View.VISIBLE);
                binding.layoutItem.setVisibility(View.GONE);
            }
        });
        //todo 외상 버튼 클릭
        binding.layoutBasket.findViewById(R.id.tv_credit_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (forBasketLayout.adapter.getItems().size() == 0) {
                    Toast.makeText(getContext(), "항목을 먼저 추가해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), "외상으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                forBasketLayout.adapter.itemClear();
                MainViewModel.setLiveDataBasketList(new ArrayList<>());
                forBasketLayout.callSetViewFromPosMain();

            }
        });
        //todo 영수증 클릭 버튼
        binding.layoutBasket.findViewById(R.id.tv_printReceipt_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "준비중...", Toast.LENGTH_SHORT).show();
            }
        });
        //todo 계산 완료 버튼
        binding.layoutBasket.findViewById(R.id.tv_sold_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (forBasketLayout.adapter.getItems().size() == 0) {
                    Toast.makeText(getContext(), "항목을 먼저 추가해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), "계산이 완료되었습니다", Toast.LENGTH_SHORT).show();
                forBasketLayout.adapter.itemClear();
                MainViewModel.setLiveDataBasketList(new ArrayList<>());
                forBasketLayout.callSetViewFromPosMain();
            }
        });

        //todo 바코드 리스트에서 Basket리스트로 이동
        binding.btnInPosInBarCodeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.lvInPosInBarcode.getAdapter().getCount() == 0) {
                    Toast.makeText(getContext(), "상품을 추가해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<BasketTypeItem> items = ((Basket_BarcodeList_Adapter) binding.lvInPosInBarcode.getAdapter()).getItems();
                Basket_BarcodeList_Adapter adapter = ((Basket_BarcodeList_Adapter) forBasketLayout.lv.getAdapter());
                adapter.addItem(items);
                forBasketLayout.callSetViewFromPosMain();
                binding.drawer.openDrawer(Gravity.RIGHT);
                Toast.makeText(getContext(), "추가되었습니다", Toast.LENGTH_SHORT).show();
                ((Basket_BarcodeList_Adapter) binding.lvInPosInBarcode.getAdapter()).itemClear();
                ((Basket_BarcodeList_Adapter) binding.lvInPosInBarcode.getAdapter()).notifyDataSetChanged();
            }
        });
        return binding.getRoot();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("Pos", "onActivityResult");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.e("Pos", "onActivityResult + if (result.getContents() == null)");
            } else {
                forBarcodeLayOut.searchBarcodeItemInDB(result.getContents());
                Log.e("Pos", "onActivityResult + Scanned" + result.getContents());
            }
        } else {
            Log.e("Pos", "onActivityResult6");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

class ForItemLayout {
    Context context;
    FragmentPosItemBinding binding;
    LinearLayout layout;
    ListView verticalList;
    ListView horizontalList;
    TextView tv_selectedFromMenu;
    TextView tv_verticalValue;
    TextView tv_horizontalValue;
    TextView tv_selectedVerticalValueInList;
    TextView tv_selectedHorizontalValueInList;
    TextView tv_selectedVerticalInUnitPrice;
    TextView tv_selectedHorizontalInUnitPrice;
    TextView tv_unitPrice;
    HashMap<String, HashMap<String, String>> selectedItem;
    SelectedItem itemValueClass;
    ForItemLayout(Context context, FragmentPosItemBinding binding) {
        this.context = context;
        this.binding = binding;
        this.layout = binding.layoutItem;
        verticalList = layout.findViewById(R.id.lv_vertical);
        horizontalList = layout.findViewById(R.id.lv_horizontal);
        tv_selectedFromMenu = binding.tvItemTitle;
        tv_verticalValue = layout.findViewById(R.id.tv_vertical);
        tv_horizontalValue = layout.findViewById(R.id.tv_horizontal);
        tv_selectedVerticalValueInList = layout.findViewById(R.id.tv_selectedVerticalValue);
        tv_selectedHorizontalValueInList = layout.findViewById(R.id.tv_selectedHorizontalValue);
        tv_selectedHorizontalInUnitPrice = layout.findViewById(R.id.tv_selectedHorizontalInUnitPrice);
        tv_unitPrice = layout.findViewById(R.id.tv_selectedUnitPrice);
        tv_selectedVerticalInUnitPrice = layout.findViewById(R.id.tv_selectedVerticalInUnitPrice);
        onClick();
    }


    private void onClick() {
        //todo 선택한 물건 바구니에 담기
        layout.findViewById(R.id.btn_add_in_pos_to_basket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //todo 세로값 선택하는 리스트 온클릭
        verticalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String verticalValue = (String)((ItemValueAdapter)verticalList.getAdapter()).getItem(position);
                setHorizontalListView(verticalValue);
                if(verticalValue.contains("_")) {
                    verticalValue = verticalValue.replace("_","/");
                }
                tv_selectedVerticalValueInList.setText(verticalValue);
                tv_selectedVerticalInUnitPrice.setText(verticalValue);
            }
        });

        //todo 가로값 선택하는 리스트 온클릭

        horizontalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String horizontalValue = (String)((ItemValueAdapter)horizontalList.getAdapter()).getItem(position);
                String verticalValue = tv_selectedVerticalValueInList.getText().toString();
                if(verticalValue.contains("/")) {
                    verticalValue = verticalValue.replace("/","_");
                }
                tv_unitPrice.setText(selectedItem.get(verticalValue).get(horizontalValue));
                if(horizontalValue.contains("_")) {
                    horizontalValue = horizontalValue.replace("_","/");
                }
                tv_selectedHorizontalValueInList.setText(horizontalValue);
                tv_selectedHorizontalInUnitPrice.setText(horizontalValue);
            }
        });

    }
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getSelectedItemNameFromMain(Item item) {
        ArrayList<Value> items = MainViewModel.getSelectedValue(context, item.itemCode);
        this.itemValueClass = SelectedItem.getSelectedItem(items);
        this.selectedItem = SelectedItem.item;
        setVerticalListView();
        tv_selectedFromMenu.setText(item.koreanName);
        tv_verticalValue.setText(item.verticalValue);
        tv_horizontalValue.setText(item.horizontalValue);
        ((TextView)layout.findViewById(R.id.tv_verticalInfo)).setText(item.verticalValue + " : ");
        ((TextView)layout.findViewById(R.id.tv_horizontalInfo)).setText(item.horizontalValue + " : ");
    }

    private void setVerticalListView() {
        if(verticalList.getAdapter() == null) {
            verticalList.setAdapter(new ItemValueAdapter());
        }
        ((ItemValueAdapter)verticalList.getAdapter()).setValue(SelectedItem.verticalValue);
        ((ItemValueAdapter)verticalList.getAdapter()).notifyDataSetChanged();
    }

    private void setHorizontalListView(String verticalValue) {
        if(horizontalList.getAdapter() == null) {
            horizontalList.setAdapter(new ItemValueAdapter());
        }
        Log.e("setHorizontalListView",verticalValue);
        ((ItemValueAdapter)horizontalList.getAdapter()).setValue(SelectedItem.valueOfKey.get(verticalValue));
        ((ItemValueAdapter)horizontalList.getAdapter()).notifyDataSetChanged();
    }
    public void setAllViewInit() {
        if(horizontalList.getAdapter() == null) {
            horizontalList.setAdapter(new ItemValueAdapter());
        }
        ((ItemValueAdapter)horizontalList.getAdapter()).setValue(new ArrayList<>());
        ((ItemValueAdapter)horizontalList.getAdapter()).notifyDataSetChanged();
        tv_selectedHorizontalValueInList.setText("");
        tv_selectedVerticalValueInList.setText("");
        tv_selectedVerticalInUnitPrice.setText("");
        tv_unitPrice.setText("");
        tv_selectedHorizontalInUnitPrice.setText("");
    }
}







































