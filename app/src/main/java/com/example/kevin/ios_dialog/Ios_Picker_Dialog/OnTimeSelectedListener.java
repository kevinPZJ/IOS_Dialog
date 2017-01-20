package com.example.kevin.ios_dialog.Ios_Picker_Dialog;

import java.text.ParseException;

/**
 * Created by hyx on 2017/1/20.
 */

public interface OnTimeSelectedListener {
    void onTimeSelected(String time) throws ParseException;
    void onCancel();
}
