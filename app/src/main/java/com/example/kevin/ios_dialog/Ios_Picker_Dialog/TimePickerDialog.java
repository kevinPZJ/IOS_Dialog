package com.example.kevin.ios_dialog.Ios_Picker_Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.kevin.ios_dialog.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by hyx on 2017/1/20.
 */

public class TimePickerDialog extends Dialog {


    private TextView tvCancel;
    private TextView tvtitle;
    private TextView tvFinish;
    private LoopView loopHour;
    private LoopView loopMin;

    public TimePickerDialog(Context context) {
        super(context,R.style.ios_bottom_dialog);
        setContentView(R.layout.ios_timepicker);
        initView();//初始化原始布局以及Dialog设置
    }

    private void initView() {


        tvtitle = (TextView) findViewById(R.id.title);
        tvFinish = (TextView) findViewById(R.id.tx_finish);
        loopHour = (LoopView) findViewById(R.id.loop_hour);
        loopMin = (LoopView) findViewById(R.id.loop_min);
        tvCancel = (TextView) findViewById(R.id.tx_cancel);



        //Dialog设置

        this.setCanceledOnTouchOutside(true); //点击空白区域可以取消dialog
        this.setCancelable(true); //点击返回键可以取消Dialog
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setWindowAnimations(R.style.ios_bottom_dialog_anim); // 设置由下到上弹出效果动画
        window.setGravity(Gravity.BOTTOM); //位置在窗口底部
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT; //设置宽高
        window.setAttributes(lp);

    }



    public static class  Builder{
        private Context context;
        private Params p;

        public Builder(Context context) {
            this.context = context;
            this.p =new Params();
        }
        public Builder setTvfinishColor(int tvfinishColor) {
            p.tvfinishColor = tvfinishColor;
            return this;
        }

        public Builder setTvcancelColor(int tvcancelColor) {
            p.tvcancelColor = tvcancelColor;
            return this;
        }

        public Builder setTitle(String title){
            p.title=title;
            return this;
        }
        public Builder setCurrentTime(Date date){
            p.hour= Integer.parseInt(new SimpleDateFormat("HH:mm").format(date).split(":")[0]);
            p.min= Integer.parseInt(new SimpleDateFormat("HH:mm").format(date).split(":")[1]);
            return this;

        }

        public Builder setTimeSelectedListener(OnTimeSelectedListener listener) {
            p.callback = listener;
            return this;
        }
        public TimePickerDialog create(){
            final TimePickerDialog dialog=new TimePickerDialog(context);


            dialog.tvtitle.setText(p.title);
            dialog.tvFinish.setTextColor(p.tvfinishColor);
            dialog.tvCancel.setTextColor(p.tvcancelColor);

            dialog.tvFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (p.callback!=null){
                        try {
                            p.callback.onTimeSelected(getCurrDateValues());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

            dialog.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });



            //修改优化边界值 by lmt 16/ 9 /12.禁用循环滑动,循环滑动有bug
            p.loopHour=dialog.loopHour;
            p.loopHour.setCyclic(false);
            p.loopHour.setArrayList(NumList(0, 24));
            p.loopHour.setCurrentItem(p.hour);

            p.loopMin=dialog.loopMin;
            p.loopMin.setCyclic(false);
            p.loopMin.setArrayList(NumList(0, 60));
            p.loopMin.setCurrentItem(p.min);



            return dialog;
        }

        /**
         * 获取当前选择的时间
         *
         * @return int[]数组形式返回。例[12,30]
         */
        private final String getCurrDateValues() {
            String time = p.loopHour.getCurrentItemValue()+":"+ p.loopMin.getCurrentItemValue();

            return time;
        }

    }

    private static class  Params{
        private String   title;
        private boolean shadow = true;
        private boolean canCancel = true;
        private OnTimeSelectedListener callback;
        private LoopView loopHour,loopMin;
        private Integer hour;
        private Integer min;
        private int tvfinishColor;
        private int tvcancelColor;

        public Params() {
            tvcancelColor = Color.BLUE;
            tvfinishColor =  Color.BLUE;
            title="开始时间";


        }
    }



    /**
     * 将数字传化为集合，并且补充0
     *
     * @param startNum 数字起点
     * @param count    数字个数
     * @return
     */
    private static List<String> NumList(int startNum, int count) {
        String[] values = new String[count];
        for (int i = startNum; i < startNum + count; i++) {
            String tempValue = (i < 10 ? "0" : "") + i;
            values[i - startNum] = tempValue;
        }
        return Arrays.asList(values);
    }
}
