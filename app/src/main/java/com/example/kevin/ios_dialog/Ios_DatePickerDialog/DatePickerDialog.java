package com.example.kevin.ios_dialog.Ios_DatePickerDialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.kevin.ios_dialog.Ios_Picker_Dialog.LoopListener;
import com.example.kevin.ios_dialog.Ios_Picker_Dialog.LoopView;
import com.example.kevin.ios_dialog.R;


import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


/**
 * Created by hyx on 2017/1/22.
 */

public class DatePickerDialog extends Dialog {



    private static  int MIN_YEAR = 1900;
    private static  int MAX_YEAR = 2100;

    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvFinish;
    private LoopView loopYear;
    private LoopView loopMonth;
    private LoopView loopDay;



    public DatePickerDialog(Context context) {
        super(context, R.style.ios_bottom_dialog);
        setContentView(R.layout.ios_datepicker);
        initView();

    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        loopYear = (LoopView) findViewById(R.id.loop_year);
        loopMonth = (LoopView) findViewById(R.id.loop_month);
        loopDay = (LoopView) findViewById(R.id.loop_day);


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

    public static class  Builder {
        private Context context;
        private Params p;


        public Builder(Context context) {
            this.context = context;
            p = new Params();
        }

        public Builder setMinYear(int year) {
            p.minYear = year;
            return this;
        }

        public Builder setMaxYear(int year) {
            p.maxYear = year;
            return this;
        }

        public Builder setMinMonth(int month) {
            p.minMonth = month;
            return this;
        }

        public Builder setMaxMonth(int month) {
            p.maxMonth = month;
            return this;
        }

        public Builder setMinDay(int day) {
            p.minDay = day;
            return this;
        }

        public Builder setMaxDay(int day) {
            p.maxDay = day;
            return this;
        }

        public Builder setSelectYear(int year) {
            p.selectYear = year;
            return this;
        }

        public Builder setSelectMonth(int month) {
            p.selectMonth = month;
            return this;
        }

        public Builder setSelectDay(int day) {
            p.selectDay = day;
            return this;
        }

        public Builder setTitle(String title) {
            p.title = title;
            return this;
        }

        public Builder setOnSelectedLisenter(OnDateSelectedListener listener) {
            p.listener = listener;
            return this;
        }



        public DatePickerDialog create() {

            final DatePickerDialog dialog = new DatePickerDialog(context);


            Calendar calendar=Calendar.getInstance();

            p.loopYear = dialog.loopYear;
            p.loopMonth = dialog.loopMonth;
            p.loopDay = dialog.loopDay;

            dialog.tvTitle.setText(p.title);
            dialog.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    p.listener.onCancel();
                }
            });
            dialog.tvFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    p.listener.onDateSelected(getCurrentDateValues());
                }
            });

            //日

           p.loopDay.setArrayList(Num2List(1,30));
            if (p.selectDay!=null){
                p.loopDay.setCurrentItem(p.selectDay);
            }else {
                p.loopDay.setCurrentItem(calendar.get(Calendar.DATE)-1);
            }
//            p.loopDay.setNotLoop();

            //年
            p.loopYear.setArrayList(Num2List(MIN_YEAR, MAX_YEAR - MIN_YEAR + 1));
            if (p.selectYear==null) {
                p.loopYear.setCurrentItem(calendar.get(Calendar.YEAR)-MIN_YEAR);
                 }else{
                p.loopYear.setCurrentItem(p.selectYear-MIN_YEAR);

            }

            p.loopYear.setNotLoop();


            //月
            p.loopMonth.setArrayList(Num2List(1,12));
            if (p.selectMonth!=null){
                p.loopMonth.setCurrentItem(p.selectMonth);
            }else {
                p.loopMonth.setCurrentItem(calendar.get(Calendar.MONTH));
            }
            p.loopMonth.setNotLoop();


            LoopListener maxDaySyncListener=new LoopListener() {
                @Override
                public void onItemSelect(int item) {
                    Calendar calendar=Calendar.getInstance();
                    boolean needFixed=true;
                    if (p.minYear!=null){
                        if (Integer.parseInt(p.loopYear.getCurrentItemValue())==p.minYear){
                            if (p.loopMonth!=null){
                                if (Integer.parseInt(p.loopMonth.getCurrentItemValue())<p.minMonth){
                                    p.loopMonth.setCurrentItem(p.minMonth-1);
                                }
                            }
                        }else if (Integer.parseInt(p.loopYear.getCurrentItemValue())<p.minYear){
                            p.loopYear.setCurrentItem(p.minYear-MIN_YEAR);
                        }
                    }

                    if (p.maxYear!=null){
                        if (Integer.parseInt(p.loopYear.getCurrentItemValue())
                                == p.maxYear){
                            if (p.maxMonth!=null){
                                if (Integer.parseInt(p.loopMonth
                                .getCurrentItemValue())>p.maxMonth){
                                    p.loopMonth.setCurrentItem(p.maxMonth-1);
                                }
                            }
                        }else if (Integer.parseInt(p.loopYear.getCurrentItemValue()
                        )>p.maxYear){
                            p.loopYear.setCurrentItem(p.maxYear-MIN_YEAR);
                        }
                    }

                    calendar.set(Integer.parseInt(p.loopYear.getCurrentItemValue()),
                            Integer.parseInt(p.loopMonth.getCurrentItemValue())-1,1);
                    calendar.roll(Calendar.DATE,false);


                    if (needFixed){
                        int maxDayOfMonth =calendar.get(Calendar.DATE);
                        int fixedCurr=p.loopDay.getCurrentItem();
                        p.loopDay.setArrayList(Num2List(1,maxDayOfMonth));
                        // 修正被选中的日期最大值
                        if (fixedCurr > maxDayOfMonth){
                            fixedCurr = maxDayOfMonth - 1;
                        }
                        p.loopDay.setCurrentItem(fixedCurr);
                    }
                }




            };

            LoopListener dayLoopListener=new LoopListener() {
                @Override
                public void onItemSelect(int item) {
                    if (p.minYear != null && p.minMonth != null && p.minDay != null
                            && Integer.parseInt(p.loopYear.getCurrentItemValue()) == p.minYear
                            && Integer.parseInt(p.loopMonth.getCurrentItemValue()) == p.minMonth
                            && Integer.parseInt(p.loopDay.getCurrentItemValue()) < p.minDay) {
                        p.loopDay.setCurrentItem(p.minDay - 1);
                    }
                    if (p.maxYear != null && p.maxMonth != null && p.maxDay != null
                            && Integer.parseInt(p.loopYear.getCurrentItemValue()) == p.maxYear
                            && Integer.parseInt(p.loopMonth.getCurrentItemValue()) == p.maxMonth
                            && Integer.parseInt(p.loopDay.getCurrentItemValue()) > p.maxDay
                            ) {
                        p.loopDay.setCurrentItem(p.maxDay - 1);
                    }

                }


            };


            p.loopYear.setListener(maxDaySyncListener);
            p.loopMonth.setListener(maxDaySyncListener);
            p.loopDay.setListener(dayLoopListener);

            return dialog;



        }

        /**
         * 获取当前选择的日期
         *
         * @return int[]数组形式返回。例[1995,5,25]
         */

        private final int[] getCurrentDateValues() {
            int currentYear = Integer.parseInt(p.loopYear.getCurrentItemValue());
            int currentMonth = Integer.parseInt(p.loopMonth.getCurrentItemValue());
            int currentDay = Integer.parseInt(p.loopDay.getCurrentItemValue());

            return new int[]{currentYear, currentMonth, currentDay};
        }
    }

    public static class  Params{
        private boolean shadow = true;
        private boolean canCancel = true;
        private LoopView loopYear, loopMonth, loopDay;
        private OnDateSelectedListener listener;

        private Integer minYear;
        private Integer maxYear;

        private Integer minMonth;
        private Integer maxMonth;

        private Integer minDay;
        private Integer maxDay;

        private Integer selectYear;
        private Integer selectMonth;
        private Integer selectDay;

        private String title;

    }
    /**
     * 将数字传化为集合，并且补充0
     *
     * @param startNum 数字起点
     * @param count    数字个数
     * @return
     */
    private static List<String> Num2List(int startNum, int count) {
        String[] values = new String[count];
        for (int i = startNum; i < startNum + count; i++) {
            String tempValue = (i < 10 ? "0" : "") + i;
            values[i - startNum] = tempValue;
        }
        return Arrays.asList(values);
    }

}
