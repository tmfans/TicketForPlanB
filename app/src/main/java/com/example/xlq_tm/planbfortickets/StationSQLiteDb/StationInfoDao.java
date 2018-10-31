package com.example.xlq_tm.planbfortickets.StationSQLiteDb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.xlq_tm.planbfortickets.DataBean.StationsBean;
import com.example.xlq_tm.planbfortickets.StationList.SortModel;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class StationInfoDao {

    private Context mContext;
    private StationDbHelper mHelper;
    private SQLiteDatabase mDb;
    private static String INSERT_DATA_SQL = "insert into stations_info(id,stationName,stationCode,stationSpell,stationFirstSpell) values (?,?,?,?,?)";
    private static String SEARCH_STATION_NAME_SQL = "select stationName,stationFirstSpell from stations_info order by stationSpell";

    public StationInfoDao(Context context){
        this.mContext = context;
        mHelper = new StationDbHelper(mContext);

    }

    public void insertDataToDb(List<StationsBean> list){
        mDb = mHelper.getReadableDatabase();
        mDb.beginTransaction();
        try {
            for (StationsBean bean : list){
                mDb.execSQL(INSERT_DATA_SQL,new Object[]{null,
                        bean.getStationName(),
                        bean.getStationCode(),
                        bean.getStationSpell(),
                        bean.getStationSpell().charAt(0)});
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e){
            throw e;
        } finally {
            mDb.endTransaction();
            mDb.close();
        }
    }

    public List<SortModel> searchStationName(){
        mDb = mHelper.getReadableDatabase();
        Cursor c = mDb.rawQuery(SEARCH_STATION_NAME_SQL,null);
        List<SortModel> mNameData = new ArrayList<>();
        while (c.moveToNext()){
            SortModel model = new SortModel();
            model.setName(c.getString(c.getColumnIndex("stationName")));
            model.setLetters(c.getString(c.getColumnIndex("stationFirstSpell")));
            mNameData.add(model);
        }
        c.close();
        mDb.close();
        return mNameData;
    }
}
