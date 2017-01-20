package com.example.kevin.ios_dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kevin.ios_dialog.Ios_Bottom_Dialog.IOS_Bottom_Dialog;
import com.example.kevin.ios_dialog.Ios_Bottom_Dialog.IOS_ItemClickListener;
import com.example.kevin.ios_dialog.Ios_Picker_Dialog.OnTimeSelectedListener;
import com.example.kevin.ios_dialog.Ios_Picker_Dialog.TimePickerDialog;
import com.example.kevin.ios_dialog.Utils.UIUtils;

import java.text.ParseException;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  IOS_Bottom_Dialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

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
            default:
                break;
        }
    }

    private void Time() {
        TimePickerDialog.Builder builder=new TimePickerDialog.Builder(this);
        builder

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
}
