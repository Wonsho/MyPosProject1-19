package com.wons.myposproject.main_fragments.homefragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.wons.myposproject.R;
import com.wons.myposproject.databinding.FragmentHomeBinding;
import com.wons.myposproject.schedule.Schedule;

public final class HomeDialogUtils {
    public static AlertDialog getAlertDialogWhenAddedSchedule(Activity activity, HomeDialogCallback callback, FragmentHomeBinding binding) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("스케쥴을 적어주세요");
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_view_schedule, null);
        EditText et_schedule = view.findViewById(R.id.et_schedule);
        builder.setView(view);
        builder.setNegativeButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("TAG", "DiaLog On Click Positive");
                String msg = et_schedule.getText().toString();
                if (!msg.isEmpty()) {
                    callback.onResult(msg);
                }
            }
        });
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
    public static AlertDialog getDeleteDialog(Activity activity, HomeDialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               callback.onResultBoolean(true);
            }
        });
        builder.setMessage("선택한 스케쥴을 삭제하시겠습니까?");
        builder.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
    private HomeDialogUtils(){}
}

