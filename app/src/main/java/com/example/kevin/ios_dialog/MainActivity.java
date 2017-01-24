package com.example.kevin.ios_dialog;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kevin.ios_dialog.Ios_Alert_Dialog.VerticalCenterDialog;
import com.example.kevin.ios_dialog.Ios_Bottom_Dialog.IOS_Bottom_Dialog;
import com.example.kevin.ios_dialog.Ios_Bottom_Dialog.IOS_ItemClickListener;
import com.example.kevin.ios_dialog.Ios_DataPickerDialog.DataPickerDialog;
import com.example.kevin.ios_dialog.Ios_DataPickerDialog.OnDataSelectedListener;
import com.example.kevin.ios_dialog.Ios_DatePickerDialog.DatePickerDialog;
import com.example.kevin.ios_dialog.Ios_DatePickerDialog.OnDateSelectedListener;
import com.example.kevin.ios_dialog.Ios_Picker_Dialog.OnTimeSelectedListener;
import com.example.kevin.ios_dialog.Ios_Picker_Dialog.TimePickerDialog;
import com.example.kevin.ios_dialog.Utils.UIUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  IOS_Bottom_Dialog.Builder builder;
    private ArrayList<String> list =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        String[] data = getResources().getStringArray(R.array.list);
        for (String str : data) {
            this.list.add(str);
        }

    }

    private void initView() {
        Button btn0= (Button) findViewById(R.id.btn_0 );
        btn0.setOnClickListener(this);
        Button btn1= (Button) findViewById(R.id.btn_1 );
        btn1.setOnClickListener(this);
        Button btn2= (Button) findViewById(R.id.btn_2 );
        btn2.setOnClickListener(this);
        Button btn3= (Button) findViewById(R.id.btn_3 );
        btn3.setOnClickListener(this);
        Button btn4= (Button) findViewById(R.id.btn_4 );
        btn4.setOnClickListener(this);
        Button btn5= (Button) findViewById(R.id.btn_5 );
        btn5.setOnClickListener(this);
        Button btn6= (Button) findViewById(R.id.btn_6 );
        btn6.setOnClickListener(this);
        Button btn7= (Button) findViewById(R.id.btn_7 );
        btn7.setOnClickListener(this);
        Button btn8= (Button) findViewById(R.id.btn_8 );
        btn8.setOnClickListener(this);
        Button btn9= (Button) findViewById(R.id.btn_9 );
        btn9.setOnClickListener(this);
        Button btn10= (Button) findViewById(R.id.btn_10 );
        btn10.setOnClickListener(this);

    }

    private void  bothNull(){
       builder.create().show();

    }

    private  void onlyTitle(){
        builder.setTitle("你好我是标题").create().show();


    }

    private void one_nullTitle( ){
        builder .addItem("项目", R.color.black, new IOS_ItemClickListener() {
                    @Override
                    public void OnItemClick() {

                    }
                })
                .create().show();


    }

    private void one( ){
        builder.setTitle("标题一")
                .addItem("项目", R.color.black, new IOS_ItemClickListener() {
            @Override
            public void OnItemClick() {

            }
        }).create().show();



    }
    private void two_nullTitle( ){
        builder.addItem("项目", R.color.black, new IOS_ItemClickListener() {
                    @Override
                    public void OnItemClick() {

                    }
                })
                .addItem("项目", R.color.black, new IOS_ItemClickListener() {
                    @Override
                    public void OnItemClick() {

                    }
                }) .create().show();

    }

    private void two(){
        builder.setTitle("标题")
                .addItem("项目", R.color.black, new IOS_ItemClickListener() {
                    @Override
                    public void OnItemClick() {

                    }
                })
                .addItem("项目", R.color.black, new IOS_ItemClickListener() {
                    @Override
                    public void OnItemClick() {

                    }
                }) .create().show();



    }
    private void three(){
       builder.setTitle("标题")
                .addItem("项目", R.color.black, new IOS_ItemClickListener() {
                    @Override
                    public void OnItemClick() {

                    }
                })
                .addItem("项目", R.color.black, new IOS_ItemClickListener() {
                    @Override
                    public void OnItemClick() {

                    }
                })
                .addItem("项目", R.color.black, new IOS_ItemClickListener() {
            @Override
            public void OnItemClick() {

            }
        }) .create().show();


    }

    @Override
    public void onClick(View view) {
        builder =new IOS_Bottom_Dialog.Builder(this);
        switch (view.getId()){
            case R.id.btn_0:
                bothNull();
                break;
            case R.id.btn_1:
              onlyTitle();
                break;
            case R.id.btn_2:
              one();
                break;
            case R.id.btn_3:
             one_nullTitle();
                break;
            case R.id.btn_4:
              two();
                break;
            case R.id.btn_5:
             two_nullTitle();
                break;
            case R.id.btn_6:
             three();
                break;
            case R.id.btn_7:
                Time();
                break;
            case R.id.btn_8:
                date();
                break;
            case R.id.btn_9:
                Data(list);
                break;
            case R.id.btn_10:
               Center();
                break;
            default:
                break;
        }
    }


    private void Time() {
        TimePickerDialog.Builder builder=new TimePickerDialog.Builder(this);
        builder
                .setCurrentTime(new Date())
                .setTimeSelectedListener(new OnTimeSelectedListener() {
                    @Override
                    public void onTimeSelected(String time) throws ParseException {
                        UIUtils.showToast(time);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).create().show();
    }

    private void date() {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);

               builder .setOnSelectedLisenter(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(int[] dates) {
                        UIUtils.showToast(MainActivity.this, dates[0] + ""+
                                dates[1] + ""+dates[2] + "");
                    }
                    @Override
                    public void onCancel() {

                    }

                })
                  .setSelectYear(1995)
//                       .setMinYear(1993)
//                       .setMinMonth(5)
//                       .setMinDay(16)
                       .create().show();


    }
    private void Data(List<String> list){


        DataPickerDialog.Builder builder= new DataPickerDialog.Builder(this);

        builder.setTitle("编程语言")
                .setDataList(list)
                .setOnDataSelectedListener(new OnDataSelectedListener() {
                    @Override
                    public void onDataSelected(String itemValue, int position) {
                        UIUtils.showToast(MainActivity.this,itemValue);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).setSelection(1)
                .creat().show();


    }

    private void Center(){
        VerticalCenterDialog.Builder builder= new VerticalCenterDialog.Builder(this);

        builder.setTitle("IOS10.0更新")
                .setContent("sadas4d5as4fas1f23a1sf23as1f1as8dg4a3s1f6as4f6as1fa3s54fg6ag16a")
                .addItem("立即更新", R.color.black, new IOS_ItemClickListener() {
                    @Override
                    public void OnItemClick() {

                    }
                })
                .addItem("稍后提提", R.color.black, new IOS_ItemClickListener() {
                    @Override
                    public void OnItemClick() {

                    }
                })
                .addItem("下次提醒", R.color.black, new IOS_ItemClickListener() {
                    @Override
                    public void OnItemClick() {

                    }
                }) .create().show();


    }

}
