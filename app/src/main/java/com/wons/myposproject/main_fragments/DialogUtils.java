package com.wons.myposproject.main_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wons.myposproject.R;
import com.wons.myposproject.databinding.FragmentHomeBinding;
import com.wons.myposproject.schedule.Schedule;

public final class DialogUtils {


    public static AlertDialog getAlertDialogWhenAddedSchedule(Activity activity, DialogCallback callback, FragmentHomeBinding binding) {
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
                    String date = binding.tvScheduleValue.getText().toString();
                    Schedule schedule = new Schedule();
                    schedule.date = date;
                    schedule.schedule_of_date = msg;
                    callback.onResult(schedule);
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
    public static AlertDialog getDeleteDialog(Activity activity, DialogCallback callback, Schedule schedule) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               callback.onResult(schedule);
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

    public static AlertDialog getPosDialog(int actionCode, Activity activity, DialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_view_bolt,  null);
        // TODO: 2022-02-05 뷰바인딩
        builder.setView(view);
        builder.setTitle("추가 항목");
        builder.setNegativeButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO: 2022-02-05 콜백
            }
        });
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public static AlertDialog getOnlyCheckQuantity(Activity activity, DialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("수량을 입력해주세요");
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_view_schedule, null);
        EditText et = view.findViewById(R.id.et_schedule);
        et.setHint("수량 입력");
        builder.setView(view);
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    callback.onResult(et.getText().toString());

                } catch (Exception e) {
                    Toast.makeText(activity, "숫자가 아닙니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();
    }

    private DialogUtils(){}
}
