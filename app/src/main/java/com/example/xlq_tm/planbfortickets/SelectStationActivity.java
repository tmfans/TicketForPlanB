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
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private EditText mClearEditText;
    private LinearLayoutManager manager;
    private List<SortModel> SourceDateList;
    private PinyinComparator pinyinComparator;
    private StationInfoDao mDao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_station_layout);
        setStatusBarColor();

        pinyinComparator = new PinyinComparator();
        sideBar = findViewById(R.id.sideBar);
        dialog = findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });


        Log.d("xlq111","开始查找数据并装载");
        SourceDateList = filledData(getStationName()); // 从数据库中查找数据，并装载到List<SortModel>中
        Log.d("xlq111","装载完成");


        mRecyclerView = findViewById(R.id.recyclerView);
        Collections.sort(SourceDateList, pinyinComparator);

        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        adapter = new SortAdapter(this, SourceDateList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(SelectStationActivity.this, ((SortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
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

    public List<String > getStationName(){
        mDao = new StationInfoDao(this);
        List<String > data = mDao.searchStationName();
        Log.d("xlq111","查找完成");
        return data;
    }

    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
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
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateList(filterDateList);
    }

    private List<SortModel> filledData(List<String > data) {
        List<SortModel> mSortList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(data.get(i));
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(data.get(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

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
