package com.example.xlq_tm.planbfortickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.xlq_tm.planbfortickets.DataBean.TrainDataBean;
import com.example.xlq_tm.planbfortickets.DataBean.TrainsResult;
import com.example.xlq_tm.planbfortickets.Order.DisplayOrderAdapter;
import com.example.xlq_tm.planbfortickets.Utils.AnalysisTrainMessageUtil;

import java.util.List;
import java.util.Map;

public class DisplayTrainActivity extends AppCompatActivity implements DisplayOrderAdapter.OnClickItemListener{

    /*
     * Create by xlq for Showing trains message
     */

    private String mTitle;
    private List<Map<String, String>> mResultList;
    private TrainsResult<TrainDataBean> mResult;
    private RecyclerView mDisplayList;
    private LinearLayoutManager mManager;
    private DisplayOrderAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_train_layout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mResult = (TrainsResult) intent.getSerializableExtra("trainBean");
        mTitle = bundle.getString("title");
        mResultList = AnalysisTrainMessageUtil.Analysis(mResult);

        mDisplayList = findViewById(R.id.display_train_massage_list);
        mManager = new LinearLayoutManager(this);
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDisplayList.setLayoutManager(mManager);
        mAdapter = new DisplayOrderAdapter(this, mResultList);
        mDisplayList.setAdapter(mAdapter);
        mAdapter.setOnClickItemListener(this);

        setStatusBarColor();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void setStatusBarColor() {
        ActionBar actionBar = getSupportActionBar();
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View v = LayoutInflater.from(this).inflate(R.layout.action_bar_layout, null);
        TextView title = v.findViewById(R.id.action_bar_title);
        title.setGravity(Gravity.CENTER);
        title.setText(mTitle);
        actionBar.setCustomView(v, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
    }


    @Override
    public void onItemClick(DisplayOrderAdapter.ViewHolder view, int position) {
        
    }
}
