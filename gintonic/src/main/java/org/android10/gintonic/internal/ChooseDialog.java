package org.android10.gintonic.internal;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import org.android10.gintonic.R;

/**
 * Created by lingyi.mly on 2016/5/19.
 */
public class ChooseDialog {
    private Activity activity ;
    private static int currentCompareType = 1;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    public ChooseDialog(Activity activity){
        this.activity = activity;
        init();
    }
    public void init(){
        View view = LayoutInflater.from(activity).inflate(R.layout.monitor_item,null,false);
        RadioGroup accuracyGroup = (RadioGroup) view.findViewById(R.id.acurracy_group);
        RadioGroup sortGroup = (RadioGroup) view.findViewById(R.id.sort_group);
        Button okBtn = (Button) view.findViewById(R.id.ok);
        Button cancelBtn = (Button) view.findViewById(R.id.cancel);

        builder = new AlertDialog.Builder(activity).setTitle("日志输出选项").setView(view);
        accuracyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.accuracy_mic_btn == checkedId){
                    StopWatch.Accuracy = 2;
                }else {
                    StopWatch.Accuracy = 1;
                }
                Log.e("Accuracy",StopWatch.Accuracy+" ");
            }
        });

        sortGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.sortByTime == checkedId){
                    MethodMsg.comparableType = 1;
                }else if(R.id.sortByClass == checkedId){
                    MethodMsg.comparableType = 2;
                }else if (R.id.sortByMethod == checkedId){
                    MethodMsg.comparableType = 3;
                }
                Log.e("comparableType",  MethodMsg.comparableType+" ");
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCompareType != MethodMsg.comparableType){
                    DebugLog.reSort();
                }
                alertDialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
    public void show(){
        alertDialog = builder.show();
    }
}
