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
        mDb.beginTransaction();
        try {
            for (StationsBean bean : list){
                mDb.execSQL(INSERT_DATA_SQL,new Object[]{null,bean.getStationName(),bean.getStationCode(),bean.getStationSpell()});
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e){
            Log.d("xlq111","插入失败");
        } finally {
            Log.d("xlq111","插入完成");
            mDb.endTransaction();
            mDb.close();
        }
    }

    public boolean tableIsExist(){
        mDb = mHelper.getReadableDatabase();
        boolean result = false;
        String CHECK_TABLE_IS_EXIST = "select count(*) as c from stations_info  where type ='stationName' and name ='stations_info'";
        Cursor c = mDb.rawQuery(CHECK_TABLE_IS_EXIST,null);
        if (c.moveToNext()){
            int count = c.getInt(0);
            if (count > 0){
                result = true;
            }
        }
        c.close();
        mDb.close();
        return result;
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
