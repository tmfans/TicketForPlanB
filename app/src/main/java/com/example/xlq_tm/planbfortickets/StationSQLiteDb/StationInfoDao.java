package com.example.xlq_tm.planbfortickets.StationSQLiteDb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.xlq_tm.planbfortickets.DataBean.StationsBean;

import java.util.ArrayList;
import java.util.List;

public class StationInfoDao {

    private Context mContext;
    private StationDbHelper mHelper;
    private SQLiteDatabase mDb;
    private static String INSERT_DATA_SQL = "insert into stations_info(id,stationName,stationCode,stationSpell) values (?,?,?,?)";
    private static String SEARCH_STATION_NAME_SQL = "select stationName from stations_info";


    public StationInfoDao(Context context){
        this.mContext = context;
        mHelper = new StationDbHelper(mContext);

    }

    public void insertDataToDb(List<StationsBean> list){
        mDb = mHelper.getReadableDatabase();
        for (StationsBean bean : list){
            mDb.execSQL(INSERT_DATA_SQL,new Object[]{null,bean.getStationName(),bean.getStationCode(),bean.getStationSpell()});
        }
        Log.d("xlq111","插入完成");
        mDb.close();
    }

    public List<String> searchStationName(){
        mDb = mHelper.getReadableDatabase();
        Cursor c = mDb.rawQuery(SEARCH_STATION_NAME_SQL,null);
        List<String> mNameData = new ArrayList<>();
        while (c.moveToNext()){
            String name = c.getString(0);
            mNameData.add(name);
        }
        c.close();
        mDb.close();
        return mNameData;
    }
}
