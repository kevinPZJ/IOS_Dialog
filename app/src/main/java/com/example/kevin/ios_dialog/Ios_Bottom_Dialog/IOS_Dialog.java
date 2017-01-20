package com.example.kevin.ios_dialog.Ios_Bottom_Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kevin.ios_dialog.R;
import com.example.kevin.ios_dialog.Utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzj on 2017/1/19.
 */

public class IOS_Dialog extends Dialog{

    public static final int DEFAULT_PADDING = 8;
    public static final int DEFAULT_TITLE_SIZE = 20;
    public static final int DEFAULT_ITEM_SIZE = 20;

    private LinearLayout Item_ll;
    private TextView title;
    private View title_line;
    private TextView cancel;

    private IOS_Dialog_DismissListener OnDismissListener;


    public IOS_Dialog(Context context) {
        //给我们的Dialog定制了一个主题（透明背景，无边框，无标题栏，浮在Activity上面，毛玻璃模糊效果）
        super(context, R.style.ios_bottom_dialog);
        setContentView(R.layout.ios_bottom_dialog);
        initView();//初始化原始布局以及Dialog设置
    }

    private void initView() {

        title_line = findViewById(R.id.bottom_dialog_title_line);  // 标题分割线
        Item_ll = (LinearLayout) findViewById(R.id.item_ll);        // Item布局
        title = (TextView) findViewById(R.id.bottom_dialog_title_tv);  //Dialog标题栏
        cancel= (TextView) findViewById(R.id.bottom_dialog_cancel_tv); //取消按钮
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IOS_Dialog.this.dismiss();
            }
        });
        //Dialog设置

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


    /**
     * Builder的参数
     *
     * */

    private static class Params {

        public int titleSize; //标题字体大小
        public int ItemTextSize; //Item字体大小
        public String title;    //标题的text
        public int titleColor;
        public boolean cancelable; //是否可以点击dialog外部取消
        public List<Item> Items;        //Item
        public IOS_Dialog_DismissListener dismissListener;  //Dismiss监听器


        public Params(){
            title="";
            titleColor = Color.BLACK;
            titleSize=DEFAULT_TITLE_SIZE;
            ItemTextSize=DEFAULT_ITEM_SIZE;
            cancelable = true;
            Items=new ArrayList<>();



        }

    }


    public static class Builder{
        private Params p;
        private Context context;

        public Builder(Context context) {
            p = new Params();
            this.context = context;
        }

        public Builder setTitleColor(int color){

            p.titleColor = color;
            return  this;
        }
        public Builder setTitle(String title){
            p.title = title;
    
            return  this;
        }

        public Builder addItem(String title,int color ,IOS_ItemClickListener listener){
            p.Items.add(new Item(title,color,listener));
            return this;
        }
        public Builder setIOS_Dialog_DismissListener(IOS_Dialog_DismissListener dismissListener){
            p.dismissListener=dismissListener;
            return  this;
        }

        public IOS_Dialog create(){
            final IOS_Dialog dialog=new IOS_Dialog(context);

            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            if (p.title.isEmpty()|| p.title.equals("")){
                //默认情况为空，设置标题栏不可见

                dialog.title.setVisibility(View.GONE);
                dialog.title_line.setVisibility(View.GONE);
                dialog.cancel.setTextColor(context.getResources().getColor(R.color.gray_line));
                //分割线不可见
            }else{
                //设置标题栏可见，Title的文字样式
                dialog.title.setText(p.title);
                dialog.title.setTextColor(Color.GRAY);
                dialog.title.setTextSize(p.titleSize);
                dialog.title.setVisibility(View.VISIBLE);
                dialog.title_line.setVisibility(View.VISIBLE);

            }

            if (p.Items.size()==0){
                dialog.title_line.setVisibility(View.GONE);//假如没有Item，则不需要分割线
            }else{
                for(int i =0 ;i<p.Items.size(); i++){    //动态添加Item
                    final Item item=p.Items.get(i);
                    TextView itemTextView=new TextView(context);
                    int padding= UIUtils.dp2px(DEFAULT_PADDING);

                    itemTextView.setPadding(padding,padding,padding,padding);
                    itemTextView.setText(item.getTitle());
                    itemTextView.setGravity(Gravity.CENTER);
                    itemTextView.setTextSize(p.titleSize);
                    itemTextView.setTextColor(p.titleColor);
                    itemTextView.setOnClickListener(new View.OnClickListener() {

                        /**
                         * 为Item添加点击事件监听器
                         * */
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            if (item.getListener()!=null){   //Item监听器
                               item.listener.OnItemClick();
                            }
                        }
                    });
                    dialog.Item_ll.addView(itemTextView);  //动态添加Item，并添加Onclick事件

                    //动态添加分割线View
                    if (i!=p.Items.size()-1){
                        View divider =new View (context);
                        divider.setBackgroundResource(R.color.gray_line); //分割线颜色
                        dialog.Item_ll.addView(divider,params);
                    }

                    if (p.Items.size()==1){
                        /**假如只有1个Item，而且没有标题则采用全圆角背景图片，
                         假如有标题则使用Bottom圆角图片作为背景*/
                        if (p.title.isEmpty()){
                            itemTextView.setBackgroundResource(R.drawable.bottom_dialog_radius);
                        }else {
                            itemTextView.setBackgroundResource(R.drawable.bottom_dialog_bottom_radius);
                        }
                    }else if (i==0){/**
                     假如是第一个Item，判断是否存在标题，假如不存在标题则采用Top圆角背景图片，
                     假如存在标题则使用没有圆角的图片
                     */
                        if (p.title.isEmpty()){
                            itemTextView.setBackgroundResource(R.drawable.bottom_dialog_top_radius);
                        }else {
                            itemTextView.setBackgroundResource(R.drawable.bottom_dialog_normnal);
                        }
                    }else if (i<p.Items.size()-1){
                        itemTextView.setBackgroundResource(R.drawable.bottom_dialog_normnal);
                    }else {
                        itemTextView.setBackgroundResource(R.drawable.bottom_dialog_bottom_radius);
                    }


                }
            }
            setIOS_Dialog_DismissListener(p.dismissListener);

            return  dialog;
        }



    }



    private static class Item {
        private String Title;
        private int TitleColor;
        private IOS_ItemClickListener listener;

        public Item(String title,  int titleColor,IOS_ItemClickListener listener) {
            this.listener = listener;
            this.Title = title;
            this.TitleColor = titleColor;
        }

        public IOS_ItemClickListener getListener() {
            return listener;
        }

        public void setListener(IOS_ItemClickListener itemClickListener) {
            this.listener = itemClickListener;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public int getTitleColor() {
            return TitleColor;
        }

        public void setTitleColor( int  titleColor) {
            TitleColor = titleColor;
        }
    }
    @Override
    public void dismiss() {
        super.dismiss();
        if (OnDismissListener!=null){
            OnDismissListener.OnDismiss();
        }
    }



    private void  setOnDismissListener(IOS_Dialog_DismissListener OnDismissListener){
        this.OnDismissListener=OnDismissListener;
    }
}
