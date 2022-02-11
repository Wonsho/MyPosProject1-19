package com.wons.myposproject.layout_class;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.wons.myposproject.R;

public class Saved_barcode_Item_LayOut extends LinearLayout {
    public Saved_barcode_Item_LayOut(Context context) {
        super(context);
        init(context);
    }

    public Saved_barcode_Item_LayOut(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_barcode_item_view, this);
    }
}

