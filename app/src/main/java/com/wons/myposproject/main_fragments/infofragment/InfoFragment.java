package com.wons.myposproject.main_fragments.infofragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.databinding.FragmentInfoBinding;
import com.wons.myposproject.layout_class.Saved_barcode_Item_LayOut;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.BarCodeItem;

public class InfoFragment extends Fragment {
    private FragmentInfoBinding binding;
    private Fragment fragment;
    private ForSearedBarcodeItemLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        this.fragment = this;
        layout = new ForSearedBarcodeItemLayout(getContext(), binding);

        //todo 오른쪽 메뉴 버튼
        binding.btnSearchBarcodeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layoutSearchedBarcodeItem.setVisibility(View.VISIBLE);
            }
        });

        //todo 바코드 스캔 버튼
        binding.layoutSearchedBarcodeItem.findViewById(R.id.btn_scanBarcode).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                IntentIntegrator.forSupportFragment(fragment).initiateScan();
            }
        });
        return binding.getRoot();
    }

    //todo 바코드 상품 조히 및 추가
    //     왼쪽의 DB에 저장된 바코드 상품을 보여주는 리사이클러뷰뷰를 만들고
    //     추가 및 삭제 기능 넣기
    //     중복되는 바코드가 나올시 다일로그를 띄워 원래의 아이템의 이름 및 단가가 가오고 수정을 물어봄
    //      ...............................
    //     저장되어있는 상품입니다 수정하시겠습니까?
    //     이름 : ooo 단가: ooo
    //     이름수정 단가수정 취소
    //     ................................


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("Pos", "onActivityResult");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.e("Pos", "onActivityResult + if (result.getContents() == null)");
            } else {
                layout.searchInDB(result.getContents());
                Log.e("Pos", "onActivityResult + Scanned" + result.getContents());
            }
        } else {
            Log.e("Pos", "onActivityResult6");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}


class ForSearedBarcodeItemLayout {
    Saved_barcode_Item_LayOut layOut;
    Context context;
    FragmentInfoBinding binding;
    RecyclerView lv;

    ForSearedBarcodeItemLayout(Context context, FragmentInfoBinding binding) {
        this.context = context;
        this.binding = binding;
        this.layOut = binding.layoutSearchedBarcodeItem;
        onClick();
    }

    //todo 바코드 스캔버튼
    private void onClick() {
        //todo 바코드 적는 버튼
        layOut.findViewById(R.id.btn_write_barcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 위와 같음

            }
        });

        //todo 롱클릭 리스너 --> 어뎁터에서 해당되는 아이템을 가져와서 datachange에 해당되는 아이템을 넣어줌
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
        BarCodeItem item =MainViewModel.getBarcodeItem(context, barcode);
        if(item == null) {
            dialogForInsert(barcode);
        } else {
            dialogForUpdate(item);
        }
        //todo 바코드 찾기
        // 상품이 있으면 수정 다이로그
        // 없으면 추가 다이로그 */
    }

    private void dialogForUpdate(BarCodeItem item) {
        AlertDialog alertDialog = new InfoDialogUtils().getDialogForUpdate(context, item, new InfoDiaLogCallbackForGetItem() {

            @Override
            public void callback(BarCodeItem item) {

            }
        });
    }

    private void dialogForInsert(String barcode) {

        dataChange(ActionCode.INSERT, new BarCodeItem(null, null, null));
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
        //todo DB에서 데이터를 가져와서 뿌려줌
    }
}
