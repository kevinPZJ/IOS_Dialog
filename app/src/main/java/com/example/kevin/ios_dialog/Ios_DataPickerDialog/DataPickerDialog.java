package com.example.kevin.ios_dialog.Ios_DataPickerDialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.kevin.ios_dialog.Ios_Picker_Dialog.LoopView;
import com.example.kevin.ios_dialog.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyx on 2017/1/22.
 */

public class DataPickerDialog extends Dialog {

    private TextView tvCancel;
    private TextView title;
    private TextView tvFinish;
    private LoopView loopData;
    private TextView txUnit;



    public DataPickerDialog(Context context) {
        super(context, R.style.ios_bottom_dialog);
        setContentView(R.layout.ios_datapicker);
        initView();
    }
    public static class Builder{
        private Context context;
        private Params p;

        public Builder(Context context) {
            this.context = context;
            p =  new Params();

        }
        public Builder setDataList(List<String> dataList){
            p.dataList.clear();
            p.dataList.addAll(dataList);
            return this;
        }
        public Builder setTitle(String title){
            p.title=title;
            return this;
        }

        public Builder setSelection(int selection){
            p.initSelection=selection;
            return this;
        }
        public Builder setOnDataSelectedListener(OnDataSelectedListener onDataSelectedListener) {
            p.callback = onDataSelectedListener;
            return this;
        }


        private  String getCurrDateValue() {
            return p.loopData.getCurrentItemValue();
        }

        public DataPickerDialog creat(){
            final DataPickerDialog dialog = new DataPickerDialog(context);


            //添加数据
            if (0!=p.dataList.size()) {
                p.loopData=dialog.loopData;
                dialog.loopData.setArrayList(p.dataList);
                dialog.loopData.setNotLoop();
            }
            //设置标题
            if (TextUtils.isEmpty(p.title)) {
                dialog.title.setText(p.title);
            }

            if (TextUtils.isEmpty(p.unit)){
                dialog.txUnit.setText(p.unit);
            }


            //设定默认选中项
            if (p.dataList.size()>0){
                if (p.initSelection!=null){
                dialog.loopData.setCurrentItem(p.initSelection);}
                else {
                    dialog.loopData.setCurrentItem(1);
                }
            }
            dialog.tvFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (p.callback!=null){
                    p.callback.onDataSelected(getCurrDateValue(),dialog.loopData.getCurrentItem());
                    }
                }
            });

            dialog.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            return dialog;
        }

        //根据数据中文本来设置默认选中项
        public void setSelection(String itemValue) {
            if (p.dataList.size() > 0) {
                int idx = p.dataList.indexOf(itemValue);
                if (idx >= 0) {
                    p.initSelection = idx;
                    p.loopData.setCurrentItem(p.initSelection);
                }
            }
        }


    }
    public static class Params{
        private LoopView loopData;
        private String title;
        private String unit;
        private Integer initSelection;
        private OnDataSelectedListener callback;
        private  List<String> dataList = new ArrayList<>();
    }






    private void initView() {

        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        title = (TextView) findViewById(R.id.title);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        loopData = (LoopView) findViewById(R.id.loop_data);
        txUnit = (TextView) findViewById(R.id.tx_unit);


        this.setCanceledOnTouchOutside(true); //点击空白区域可以取消dialog
        this.setCancelable(true); //点击返回键可以取消Dialog
        Window window = this.getWindow();
        window.setWindowAnimations(R.style.ios_bottom_dialog_anim); // 设置由下到上弹出效果动画
        window.setGravity(Gravity.BOTTOM); //位置在窗口底部
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT; //设置宽高
        window.setAttributes(lp);
    }




}
