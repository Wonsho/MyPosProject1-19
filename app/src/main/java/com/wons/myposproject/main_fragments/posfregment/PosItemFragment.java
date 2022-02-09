package com.wons.myposproject.main_fragments.posfregment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.wons.myposproject.itemvalues.GroupCode;
import com.wons.myposproject.itemvalues.Value;
import com.wons.myposproject.pos_value.BarCodeItem;
import com.wons.myposproject.pos_value.BasketItem;

import java.util.ArrayList;
import java.util.Arrays;

enum ActionCode {
    INSERT,
    SELECTED_DELETE,
    ALL_DELETE,
    NOTHING;
}

enum ItemViewCode {
    BARCODE,
    ITEM_LIST_VIEW;
}

enum SetCheckCode {
    NEED_CHECK,
    NON_NEED;
}

public class PosItemFragment extends Fragment {
    private FragmentPosItemBinding binding;
    public static final String TAG = "PosItemFragment";
    private ForItemMenu forItemMenu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPosItemBinding.inflate(inflater, container, false);
        forItemMenu =  new ForItemMenu(getContext(), binding, this);
        MyExpandableAdapter adapter = new MyExpandableAdapter();
        binding.lvExpandable.setAdapter(adapter);
        adapter.putGroupList(new ArrayList<>(Arrays.asList(Group.values())));
        adapter.notifyDataSetChanged();
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            binding.lvExpandable.expandGroup(i);
        }
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
                forItemMenu.searchItemInDB(ItemViewCode.BARCODE, result.getContents());
                Log.e("Pos", "onActivityResult + Scanned" + result.getContents());
            }
        } else {
            Log.e("Pos", "onActivityResult6");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}


//todo 메뉴의 선택에 따라 바코드뷰를 띄워주거나 아이템리스트 뷰를 띄워줌 , 바코드 버튼을 누르면 바코드 스캔 시작

class ForItemMenu extends ForItemListView {
    Fragment fragment;
    ForItemMenu(Context context, FragmentPosItemBinding binding, Fragment fragment) {
        super(context, binding);
        this.fragment = fragment;
        onClick();
    }

    private void onClick() {
        //todo 비코드 스캔 버튼
        binding.tvBarcodeLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator.forSupportFragment(fragment).initiateScan();
                itemLayoutChange(ItemViewCode.BARCODE);
            }
        });

        //todo 아이템 메뉴 클릭 버튼
        binding.lvExpandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //todo

                itemLayoutChange(ItemViewCode.ITEM_LIST_VIEW);
                return false;
            }
        });

    }

    private void itemLayoutChange(ItemViewCode code) {
        switch (code) {
            case ITEM_LIST_VIEW: {
                binding.layoutItem.setVisibility(View.VISIBLE);
                binding.layoutBarcode.setVisibility(View.GONE);
                whatDoYouWantToDoInListView(ActionCode.ALL_DELETE, binding.lvInPosInBarcode);
                break;
            }
            case BARCODE: {
                binding.layoutItem.setVisibility(View.GONE);
                binding.layoutBarcode.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    public void searchItemInDB(ItemViewCode code, String itemValue) {
        switch (code) {
            case BARCODE: {
                BarCodeItem item = MainViewModel.getBarcodeItem(context, itemValue);
                if(item!=null) {
                    checkQuantity(item);
                    break;
                }
                Toast.makeText(context, "해당 품목이 없습니다", Toast.LENGTH_SHORT).show();
            }
            case ITEM_LIST_VIEW: {

                //todo setListVertical 동작
                break;
            }
        }

    }

    private void checkQuantity(BarCodeItem item) {

        AlertDialog alertDialog = new PosDialogUtils().getDialogForItemQuantity(context, new PosDialogCallback() {
            @Override
            public void callBack(Boolean yOrN) {
                return;
            }

            @Override
            public void callBackString(String str) {
                if(str.isEmpty()) {
                    Toast.makeText(context, "수량을 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                    checkQuantity(item);
                    return;
                }
                whatDoYouWantToDoInListView(ActionCode.INSERT, binding.lvInPosInBarcode, new ArrayList<>(Arrays.asList(new BasketItem(item.name,null,item.unitPrice,str.trim()))));
                Toast.makeText(context, "추가 되었습니다", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }
    public void setListVerticalValue(Value verticalValue) {

    }

    private void setListHorizontalValue(ArrayList<String> horizontalValue) {

    }
}

class ForItemListView extends ForBasketView {
    ForItemListView(Context context, FragmentPosItemBinding binding) {
        super(context, binding);
    }

}


//todo 바스켓 뷰   판매, 외상, 영수증 버튼 기능 미완성 // 작동 가능
class ForBasketView extends ForSetView {
    BasketLayout basketLayout;
    ListView basketListView;

    ForBasketView(Context context, FragmentPosItemBinding binding) {
        super(context, binding);
        basketLayout = binding.layoutBasket;
        basketListView = basketLayout.findViewById(R.id.lv_basket);
        onclick();
        whatDoYouWantToDoInListView(ActionCode.NOTHING, basketListView, MainViewModel.getLiveDataBasketList());
    }

    private void onclick() {

        //todo 모두 지우는 버튼
        basketLayout.findViewById(R.id.tv_clearList_likeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new PosDialogUtils().getAlertDialogForBasketDelete(context, new PosDialogCallback() {
                    @Override
                    public void callBack(Boolean yOrN) {
                        if (basketListView.getAdapter().getCount() == 0) {
                            Toast.makeText(context, "먼저 항목을 추가해주세요", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (yOrN) {
                            whatDoYouWantToDoInListView(ActionCode.ALL_DELETE, basketListView);
                            Toast.makeText(context, "전부 삭제되었습니다", Toast.LENGTH_SHORT).show();
                            MainViewModel.setLiveDataBasketList(((BasKetListAdapter) basketListView.getAdapter()).getItems());
                            setPriceTextView();
                        }

                    }

                    @Override
                    public void callBackString(String str) {
                        if (str == null) {
                            return;
                        }

                    }
                });
                alertDialog.show();
            }
        });
        basketListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog alertDialog = new PosDialogUtils().getAlertDialogForBasketDelete(context, new PosDialogCallback() {
                    @Override
                    public void callBack(Boolean yOrN) {
                        if (yOrN) {
                            whatDoYouWantToDoInListView(ActionCode.SELECTED_DELETE, basketListView, position);
                            Toast.makeText(context, "삭제되었습니다", Toast.LENGTH_SHORT).show();
                            MainViewModel.setLiveDataBasketList(((BasKetListAdapter) basketListView.getAdapter()).getItems());
                            setPriceTextView();
                        }

                    }

                    @Override
                    public void callBackString(String str) {
                        if (str == null) {
                            return;
                        }

                    }
                });
                alertDialog.show();
                return false;
            }
        });
        //todo 외상 버튼
        basketLayout.findViewById(R.id.tv_credit_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 외상 로직 짜기

                if (MainViewModel.getLiveDataBasketList().size() == 0) {
                    Toast.makeText(context, "상품을 추가 해주세요 ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "외상 등록 되었습니다", Toast.LENGTH_SHORT).show();
                whatDoYouWantToDoInListView(ActionCode.ALL_DELETE, basketListView);
                MainViewModel.setLiveDataBasketList(((BasKetListAdapter) basketListView.getAdapter()).getItems());
                setPriceTextView();
            }
        });

        //todo 판매 버튼
        basketLayout.findViewById(R.id.tv_sold_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainViewModel.getLiveDataBasketList().size() == 0) {
                    Toast.makeText(context, "상품을 추가 해주세요 ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "판매 완료 되었습니다", Toast.LENGTH_SHORT).show();
                whatDoYouWantToDoInListView(ActionCode.ALL_DELETE, basketListView);
                MainViewModel.setLiveDataBasketList(((BasKetListAdapter) basketListView.getAdapter()).getItems());
                setPriceTextView();
            }
        });
        //todo 프린트 영수증 버튼
        basketLayout.findViewById(R.id.tv_printReceipt_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainViewModel.getLiveDataBasketList().size() == 0) {
                    Toast.makeText(context, "상품을 추가 해주세요 ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "준비중 ...", Toast.LENGTH_SHORT).show();
                MainViewModel.setLiveDataBasketList(((BasKetListAdapter) basketListView.getAdapter()).getItems());
                setPriceTextView();
            }
        });

    }

    private void setPriceTextView() {
        ArrayList<BasketItem> items = MainViewModel.getLiveDataBasketList();
        int price = 0;
        if (items.size() == 0) {
            price = price;
        } else {
            for (BasketItem item : items) {
                price += (Integer.parseInt(item.unitPrice.trim()) * Integer.parseInt(item.quantity.trim()));
            }
        }
        ((TextView) basketLayout.findViewById(R.id.tv_allPrice)).setText(String.valueOf(price));
    }
}

class ForSetView {
    Context context;
    FragmentPosItemBinding binding;

    ForSetView(Context context, FragmentPosItemBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    public void whatDoYouWantToDoInListView(ActionCode actionCode, ListView lv, int indexOfWantToChangeData) {
        changeListView(actionCode, lv, null, indexOfWantToChangeData);

    }

    public void whatDoYouWantToDoInListView(ActionCode actionCode, ListView lv, ArrayList<BasketItem> wantToChangeData) {
        changeListView(actionCode, lv, wantToChangeData, 0);

    }

    public void whatDoYouWantToDoInListView(ActionCode actionCode, ListView lv) {
        changeListView(actionCode, lv, null, 0);

    }


    public void changeListView(ActionCode code, ListView lv, ArrayList<BasketItem> items, int index) {
        ArrayList<BasketItem> wantToChangeData = null;
        SetCheckCode checkCode = null;
        switch (code) {
            case ALL_DELETE: {
                checkCode = SetCheckCode.NON_NEED;
                wantToChangeData = new ArrayList<>();
                break;
            }
            case INSERT: {
                checkCode = SetCheckCode.NEED_CHECK;
                wantToChangeData = items;
                break;
            }
            case NOTHING: {
                checkCode = SetCheckCode.NON_NEED;
                wantToChangeData = items;
                break;
            }
            case SELECTED_DELETE: {
                checkCode = SetCheckCode.NON_NEED;
                ((BasKetListAdapter) lv.getAdapter()).deleteItem(index);
                wantToChangeData = ((BasKetListAdapter) lv.getAdapter()).getItems();
                break;
            }
        }
        if (checkCode == SetCheckCode.NON_NEED) {
            setDataToList(lv, wantToChangeData);
        }
        if (checkCode == SetCheckCode.NEED_CHECK) {
            checkSameItemInTheViewBeforeAdd(lv, wantToChangeData);
        }
    }


    private void checkSameItemInTheViewBeforeAdd(ListView lv, ArrayList<BasketItem> addedData) {
        ArrayList<BasketItem> originDataInListView= ((BasKetListAdapter) lv.getAdapter()).getItems();
        if (originDataInListView.size() == 0 || originDataInListView == null) {
            setDataToList(lv, addedData);
            return;
        }
        //todo 원래의 데이터와 새로 들어옴 데이터를 비교하여 넣어주기
       for(int i=0 ; i<originDataInListView.size() ; i++ ) { //todo 원래의 데이터 값
           for(int j=0; j<addedData.size() ;j++) { //todo 넣어줄 데이터의 값
               if((originDataInListView.get(i).itemName == addedData.get(j).itemName) && (originDataInListView.get(i).quantity == addedData.get(j).quantity)) {
                   originDataInListView.get(i).quantity = String.valueOf(Integer.parseInt(originDataInListView.get(i).quantity)+Integer.parseInt(addedData.get(j).quantity));
               } else {
                   originDataInListView.add(addedData.get(j));
               }
           }
       }
        setDataToList(lv, originDataInListView);
    }

    private void setDataToList(ListView lv, ArrayList<BasketItem> dataToSet) {
        if (lv.getAdapter() == null) {
            lv.setAdapter(new BasKetListAdapter());
        }
        ((BasKetListAdapter) lv.getAdapter()).setItems(dataToSet);
        ((BasKetListAdapter) lv.getAdapter()).notifyDataSetChanged();
    }
}