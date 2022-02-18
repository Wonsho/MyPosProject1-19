package com.wons.myposproject.layout_class;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wons.myposproject.R;

public class Info_credit_layout extends ConstraintLayout {
    public Info_credit_layout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public Info_credit_layout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.info_creditlayout, this);
    }
}
