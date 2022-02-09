package com.wons.myposproject.layout_class;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.wons.myposproject.R;

public class BasketLayout extends LinearLayout {
    public BasketLayout(Context context) {
        super(context);
        init(context);
    }

    public BasketLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_basket, this);
    }
}
