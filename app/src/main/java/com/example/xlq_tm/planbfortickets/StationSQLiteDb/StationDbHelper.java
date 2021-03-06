package com.example.xlq_tm.planbfortickets.StationSQLiteDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StationDbHelper extends SQLiteOpenHelper {

    private static final String mDbName = "station.db";
    private static String CREATE_TABLE_STRING = "create table if not exists stations_info(" +
            "id integer primary key AUTOINCREMENT" +
            ",stationName varchar(255)" +
            ",stationCode varchar(20)" +
            ",stationSpell varChar(255)" +
            ",stationFirstSpell varChar(20))";

    public StationDbHelper(Context context) {
        super(context,mDbName,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
