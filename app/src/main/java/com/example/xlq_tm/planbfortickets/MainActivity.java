package com.example.xlq_tm.planbfortickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xlq_tm.planbfortickets.DataBean.StationsBean;
import com.example.xlq_tm.planbfortickets.Utils.DataPickerDialogUtils;
import com.example.xlq_tm.planbfortickets.Utils.GetStationUtils;
import com.example.xlq_tm.planbfortickets.Utils.GetTrainUtils;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mStartStation;
    private TextView mEndStation;
    private TextView mDateStr;
    private Button mQueryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setStatusBarColor();
    }

    public void setStatusBarColor(){
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor,null));
        ActionBar actionBar = getSupportActionBar();
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View v = LayoutInflater.from(this).inflate(R.layout.action_bar_layout,null);
        actionBar.setCustomView(v,lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_btn:
                //GetStationUtils.GetStationMethod();
                QueryStation();
                break;
            case R.id.start_station:
                Intent intent = new Intent(MainActivity.this,SelectStationActivity.class);
                startActivity(intent);
                break;
            case R.id.end_station:
                break;
            case R.id.date_text:
                DataPickerDialogUtils utils = new DataPickerDialogUtils(this,mDateStr);
                utils.showDataPickerDialog();
        }

    }

    public void initView(){
        mStartStation = findViewById(R.id.start_station);
        mEndStation = findViewById(R.id.end_station);
        mQueryBtn = findViewById(R.id.search_btn);
        mDateStr = findViewById(R.id.date_text);
        Calendar calendar = Calendar.getInstance();
        mDateStr.setText(calendar.get(Calendar.YEAR)
                + "-" + (calendar.get(Calendar.MONTH) + 1)
                + "-" + calendar.get(Calendar.DATE));
        mQueryBtn.setOnClickListener(this);
        mStartStation.setOnClickListener(this);
        mEndStation.setOnClickListener(this);
        mDateStr.setOnClickListener(this);
    }

    public void QueryStation(){
        String startStation = mStartStation.getText().toString();
        String endStation = mEndStation.getText().toString();
        String startDate = mDateStr.getText().toString();

        GetTrainUtils.request(startDate,startStation,endStation,"ADULT");
    }

}
