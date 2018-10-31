package com.example.xlq_tm.planbfortickets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xlq_tm.planbfortickets.StationList.PinyinComparator;
import com.example.xlq_tm.planbfortickets.StationList.PinyinUtils;
import com.example.xlq_tm.planbfortickets.StationList.SideBar;
import com.example.xlq_tm.planbfortickets.StationList.SortAdapter;
import com.example.xlq_tm.planbfortickets.StationList.SortModel;
import com.example.xlq_tm.planbfortickets.StationSQLiteDb.StationInfoDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SelectStationActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SideBar mSideBar;
    private TextView mDialog;
    private SortAdapter mAdapter;
    private EditText mClearEditText;
    private LinearLayoutManager mManager;
    private List<SortModel> mSourceDateList;
    private PinyinComparator mPinyinComparator;
    private StationInfoDao mDao;
    private TextView mErrorText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_station_layout);
        setStatusBarColor();

        mErrorText = findViewById(R.id.data_empty_text);

        mPinyinComparator = new PinyinComparator();
        mSideBar = findViewById(R.id.sideBar);
        mDialog = findViewById(R.id.dialog);
        mSideBar.setTextView(mDialog);

        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mManager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        mSourceDateList = getStationName();// 从数据库中查找数据，并装载到List<SortModel>中
        if (mSourceDateList.size() != 0){
            mErrorText.setVisibility(View.GONE);
        } else {
            mErrorText.setVisibility(View.VISIBLE);
            mSideBar.setVisibility(View.GONE);
        }
        mRecyclerView = findViewById(R.id.recyclerView);
        mManager = new LinearLayoutManager(this);
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mManager);
        mAdapter = new SortAdapter(this, mSourceDateList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(SelectStationActivity.this, ((SortModel) mAdapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        mClearEditText = findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public List<SortModel > getStationName(){
        mDao = new StationInfoDao(this);
        List<SortModel> data = mDao.searchStationName();
        return data;
    }

    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mSourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : mSourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                        //不区分大小写
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
                        ) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, mPinyinComparator);
        mAdapter.updateList(filterDateList);
    }

    public void setStatusBarColor(){
        ActionBar actionBar = getSupportActionBar();
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View v = LayoutInflater.from(this).inflate(R.layout.select_action_bar_layout,null);
        actionBar.setCustomView(v,lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

                
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
