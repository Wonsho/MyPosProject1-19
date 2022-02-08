package com.wons.myposproject.main_fragments.posfregment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wons.myposproject.BasketLayout;
import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.adapter.BasKetListAdapter;
import com.wons.myposproject.adapter.MyExpandableAdapter;
import com.wons.myposproject.databinding.FragmentPosItemBinding;
import com.wons.myposproject.itemvalues.Group;
import com.wons.myposproject.pos_value.BarCodeItem;
import com.wons.myposproject.pos_value.BasketItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

enum ActionCode {
    ACTION_CODE_INSERT,
    ACTION_CODE_SELECTED_DELETE,
    ACTION_CODE_ALL_DELETE;
}

enum ItemViewCode {
    BARCODE,
    ITEM_LIST_VIEW;
}

// TODO: 2022-02-08  리스트 추가할때 버그 있음

public class PosItemFragment extends Fragment {
    private final String TAG = "PosItemFragment";
    PosItemMovement movement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPosItemBinding binding = FragmentPosItemBinding.inflate(inflater, container, false);
        movement = new PosItemMovement(getContext(), this, binding);
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
                Log.e("Pos", "onActivityResult + Scanned" + result.getContents());
                movement.searchBarcodeItemInDB(result.getContents());
            }
        } else {
            Log.e("Pos", "onActivityResult6");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}


final class PosItemMovement extends BasketLayoutMovement {
    private final Fragment fragment;

    PosItemMovement(Context context, Fragment fragment, FragmentPosItemBinding binding) {
        super(context, binding);
        this.fragment = fragment;
        setBarcodeItemView(null);
        setItemMenuListView();
        onClick();
    }

    private void onClick() {

        //todo 오른쪽메뉴 버튼
        binding.lvExpandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO: 아이템 뷰의 레이아웃을 바꿔주고 기존의 바코드 리스트뷰는 초기화
                //  왼쪽 아이템 메뉴 클릭후 그 아이템 값을 가져와 tv_itemTitle 와
                //  tv_vertical, tv_horizontal 과 vertical리스트에 vertical값을 쀼려줌
                Log.e("lvExpandable", "Onc");
                changeView(ItemViewCode.ITEM_LIST_VIEW);
                return false;
            }
        });
        //todo 바코드 버튼 누를때
        binding.tvBarcodeLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.layoutBarcode.getVisibility() != View.VISIBLE){
                    changeView(ItemViewCode.BARCODE);
                }
                barcodeScan(fragment);
                Log.e("tvBarcodeLikeBtn", "Onc");
            }
        });

        //todo 바코드 레이아웃 리스트의 아이템을 눌렀을때
        binding.lvInPosInBarcode.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //todo 바코드 리스트를 롱클릭후 삭제
                return false;
            }
        });

        //todo 바코드 레이아웃의 아이템을 Basket레이아웃으로 옮기는 버튼
        binding.btnInPosInBarCodeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasKetListAdapter adapter = ((BasKetListAdapter) binding.lvInPosInBarcode.getAdapter());
                if(adapter.getCount() == 0) {
                    Toast.makeText(context, "먼저 상품을 추가해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                PosItemMovement.super.changeBasketListData(ActionCode.ACTION_CODE_INSERT, adapter.getItems());
                adapter.setItems(new ArrayList<>());
                adapter.notifyDataSetChanged();
                binding.drawer.openDrawer(Gravity.RIGHT);
            }
        });

        //todo 아직 해당사항 없음
        binding.btnAddInPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo 볼트 아이템 추가버튼
                // TODO: 아이템을 조회후 선택한 항목이 없거나 오류인 경우 return
                //  else 면 dialog를 띄워준후 바구니에 넣기
                //  그다음 horizontalView 선택품목 단가 뷰 클리어
                //  drawer 나오게 하기
            }
        });
        binding.lvVertival.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo verticalText에 선택한 항목을 셋 해줌
                // horizontal값 조회후
                // 그다음 horizontal에 맞는 리스트를 뿌려줌
            }
        });
        binding.lvHorizontal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo horizontal 에 선택한 항목을 셋 해줌
            }
        });
    }

// todo 조회된 바코드로 상품 검색
    //todo 조회가 완료되면 바코드 리스트뷰에 추가 하기
    public void searchBarcodeItemInDB(String barcode) {
        Log.e("searchBarcodeItemInDB", barcode);
        BarCodeItem item = MainViewModel.getBarcodeItem(context, barcode);
        if (item == null) {
            Toast.makeText(context, "해당하는 품목이 없습니다", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog alertDialog = new PosDialogUtils().getDialogForItemQuantity(context, new PosDialogCallback() {
            @Override
            public void callBack(Boolean yOrN) {
            }
            @Override
            public void callBackString(String str) {
                if(!str.isEmpty()) {
                    setBarcodeItemView(new BasketItem(item.name, null, item.unitPrice, str.trim()));
                } else {
                    Toast.makeText(context, "수량입력이 안되었습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
    }


    //todo 바코드 조회후 셋 해주기
    private void setBarcodeItemView(BasketItem item) {
        ListView lv = binding.lvInPosInBarcode;
        if (lv.getAdapter() == null) {
            lv.setAdapter(new BasKetListAdapter());
        }
        if(item == null) {
            return;
        }
        BasKetListAdapter adapter = ((BasKetListAdapter) lv.getAdapter());
//        ArrayList<BasketItem> originItems = adapter.getItems();
        adapter.addItem(item);
        adapter.notifyDataSetChanged();
    }


    private void barcodeScan(Fragment fragment) {
        IntentIntegrator.forSupportFragment(fragment).initiateScan();
    }


    private void changeView(ItemViewCode itemViewCode) {
        switch (itemViewCode) {
            case BARCODE: {
                Log.e("changeView", "BARCODE");
                binding.layoutBarcode.setVisibility(View.VISIBLE);
                binding.layoutItem.setVisibility(View.GONE);
                break;
            }
            case ITEM_LIST_VIEW: {
                Log.e("changeView", "ITEM_LIST_VIEW");
                binding.layoutItem.setVisibility(View.VISIBLE);
                binding.layoutBarcode.setVisibility(View.GONE);
                //todo 클리어뷰
                break;
            }
        }
        clearView();
    }
    private void clearView() {
        ((BasKetListAdapter)(binding.lvInPosInBarcode.getAdapter())).setItems(new ArrayList<>());
        ((BasKetListAdapter)(binding.lvInPosInBarcode.getAdapter())).notifyDataSetChanged();
        //todo 아이템 레이아웃 클리어 해주기

    }
    private void setItemMenuListView() {
        MyExpandableAdapter adapter = new MyExpandableAdapter();
        ExpandableListView expandableListView = binding.lvExpandable;
        expandableListView.setAdapter(adapter);
        ArrayList<Group> groupArrayList = new ArrayList<>(Arrays.asList(Group.values()));
        adapter.putGroupList(groupArrayList);
        adapter.notifyDataSetChanged();
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
    }
}

class BasketLayoutMovement extends LogicInPos {
    BasketLayoutMovement(Context context, FragmentPosItemBinding binding) {
        super(context, binding);
        setBasketListView();
        setOnclick();
    }

    BasketLayout basket = super.binding.layoutBasket;

    private void setOnclick() {
        basket.findViewById(R.id.tv_credit_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 외상버튼
            }
        });
        basket.findViewById(R.id.tv_printReceipt_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "준비중....", Toast.LENGTH_SHORT).show();
                // todo 영수증 출력
            }
        });
        basket.findViewById(R.id.tv_sold_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 계산 완료
            }
        });
        basket.findViewById(R.id.tv_clearList_likeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PosDialogUtils utils = new PosDialogUtils();
                AlertDialog alertDialog = utils.getAlertDialogForBasketDelete(context, new PosDialogCallback() {
                    @Override
                    public void callBack(Boolean yOrN) {
                        if (yOrN) {
                            changeBasketListData(ActionCode.ACTION_CODE_ALL_DELETE, new ArrayList<>());
                        }
                    }

                    @Override
                    public void callBackString(String str) {

                    }
                });
                alertDialog.setTitle("전부 삭제 하시겠습니까?");
                alertDialog.show();
            }
        });
        ((ListView) basket.findViewById(R.id.lv_basket)).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new PosDialogUtils().getAlertDialogForBasketDelete(context, new PosDialogCallback() {
                    @Override
                    public void callBack(Boolean yOrN) {
                        if (yOrN) {
                            ListView lv = basket.findViewById(R.id.lv_basket);
                            ArrayList<BasketItem> arr = new ArrayList<>();
                            arr.add((BasketItem) ((BasKetListAdapter)lv.getAdapter()).getItem(position));
                            changeBasketListData(ActionCode.ACTION_CODE_SELECTED_DELETE,arr);
                        }
                    }

                    @Override
                    public void callBackString(String str) {

                    }
                }).show();
                return false;
            }
        });
    }

    private ArrayList<BasketItem> getBasketListData() {
        ArrayList<BasketItem> basketItemArrayList = MainViewModel.getLiveDataBasketList();
        return basketItemArrayList;
    }

    public void changeBasketListData(ActionCode actionCode, ArrayList<BasketItem> items) {
        switch (actionCode) {
            case ACTION_CODE_ALL_DELETE: {
                if (MainViewModel.getLiveDataBasketList().size() != 0) {
                    MainViewModel.setLiveDataBasketList(new ArrayList<>());
                    Toast.makeText(context, "모두 삭제 되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "삭제할 데이터가 없습니다", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case ACTION_CODE_INSERT: {
                if (items.size() == 0 || items == null) {
                    Toast.makeText(context, "추가할 데이터가 없습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "바구니에 추가 되었습니다", Toast.LENGTH_SHORT).show();
                MainViewModel.setLiveDataBasketList(items);
                break;
            }
            case ACTION_CODE_SELECTED_DELETE: {
                ArrayList<BasketItem> arrayList = MainViewModel.getLiveDataBasketList();
                for (BasketItem item : items) {
                    arrayList.remove(item);
                }
                Toast.makeText(context, "삭제 되었습니다", Toast.LENGTH_SHORT).show();
                MainViewModel.setLiveDataBasketList(arrayList);
                break;
            }
        }
        setBasketListView();
    }

    private void setBasketListView() {
        ListView lv = basket.findViewById(R.id.lv_basket);
        ArrayList<BasketItem> basketItemArrayList = getBasketListData();
        if (lv.getAdapter() == null) {
            lv.setAdapter(new BasKetListAdapter());
        } if(basketItemArrayList.size() == 0) {
            ((BasKetListAdapter) lv.getAdapter()).setItems(new ArrayList<>());
        } else {
            ((BasKetListAdapter) lv.getAdapter()).setItems(basketItemArrayList);
        }
        DecimalFormat df = new DecimalFormat("###,###,###");
        TextView tv = basket.findViewById(R.id.tv_allPrice);
        int price = 0;
        for(BasketItem item : basketItemArrayList) {
            price += (Integer.parseInt(item.unitPrice.trim()) * Integer.parseInt(item.quantity.trim()));
        }
        tv.setText(df.format(price));
        ((BasKetListAdapter) lv.getAdapter()).notifyDataSetChanged();
    }


    private void soldBasket() {
    }

    private void creditBasket() {

    }

    private void printReceipt() {

    }
}

class LogicInPos {

    Context context;
    FragmentPosItemBinding binding;

    public LogicInPos(Context context, FragmentPosItemBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    @SuppressLint("LongLogTag")
    //todo 규격이 null 이면 이름만 체크
    //  null 이 아니면 규격 == 이름 체크
    ArrayList<BasketItem> checkSameNameItem(ArrayList<BasketItem> originItems, ArrayList<BasketItem> needCheckItems) {
        Log.e("checkSameNameItem","originItems.size() : "+originItems.size() + " needCheckItems.size : " +  needCheckItems.size());
        ArrayList<BasketItem> originBasketItemArrayList = originItems;
        if(originBasketItemArrayList.size() == 0) {
            Log.e("checkSameNameItem", "if(originBasketItemArrayList.size() == 0)" + originBasketItemArrayList.size());
            return needCheckItems;
        }
        for (int i = 0; i < needCheckItems.size(); i++) {
            for (int j = 0; j < originBasketItemArrayList.size(); j++) {
                if (needCheckItems.get(i).itemName.equals(originBasketItemArrayList.get(j).itemName)) {
                    originBasketItemArrayList.get(j).quantity = String.valueOf(Integer.parseInt(originBasketItemArrayList.get(j).quantity) + Integer.parseInt(needCheckItems.get(i).quantity));
                    Log.e("checkSameNameItem1"," String.valueOf(Integer.parseInt(originBasketItemArrayList.get("+j+").quantity) = " +originBasketItemArrayList.get(j).quantity+"  Integer.parseInt(needCheckItems.get("+i+").quantity)) = "+needCheckItems.get(i).quantity);
                } else {
                    originBasketItemArrayList.add(needCheckItems.get(i));
                    Log.e("checkSameNameItem2"," originBasketItemArrayList.add(needCheckItems.get("+i+")) = " + originItems.get(i).quantity);
                }
            }
        }
        return originBasketItemArrayList;
    }
}
