package com.example.xlq_tm.planbfortickets.StationSQLiteDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StationDbHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static final String mDbName = "station.db";
    private static String CREATE_TABLE_STRING = "create table if not exists stations_info(" +
            "id integer primary key AUTOINCREMENT" +
            ",stationName varchar(255)" +
            ",stationCode varchar(20)" +
            ",stationSpell varChar(255))";

    public StationDbHelper(Context context) {
        super(context,mDbName,null,1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
