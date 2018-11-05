package com.example.xlq_tm.planbfortickets.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xlq_tm.planbfortickets.R;

import java.util.Calendar;

public class DataPickerDialogUtils implements DatePicker.OnDateChangedListener {

    private Context mContext;
    private DatePicker mPicker;
    private TextView mShowDateText;

    public DataPickerDialogUtils(Context mContext,TextView mShowDateText) {
        this.mContext = mContext;
        this.mShowDateText = mShowDateText;
    }


    private void initDatePicker(DatePicker picker) {
        Calendar calendar = Calendar.getInstance();
        picker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE),
                null);
    }

    public static String formatDate(int date){
        if (date >= 10)
            return String.valueOf(date);
        else
            return "0" + String.valueOf(date);
    }

    public void showDataPickerDialog() {
        LinearLayout dataPickerLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.data_picker_layout, null);
        mPicker = dataPickerLayout.findViewById(R.id.DataPicker);
        initDatePicker(mPicker);
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(R.string.date_tip)
                .setView(dataPickerLayout)
                .setPositiveButton(R.string.positive_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mShowDateText.setText(mPicker.getYear() + "-" + formatDate(mPicker.getMonth()+ 1 ) + "-" + formatDate(mPicker.getDayOfMonth()));
                dialog.dismiss();
            }
        }).setNegativeButton(R.string.negative_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);//显示位置在底部
        dialog.show();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    }
}
