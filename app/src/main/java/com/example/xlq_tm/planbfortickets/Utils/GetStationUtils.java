package com.example.xlq_tm.planbfortickets.Utils;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.xlq_tm.planbfortickets.DataBean.StationsBean;
import com.example.xlq_tm.planbfortickets.R;
import com.example.xlq_tm.planbfortickets.StationSQLiteDb.StationInfoDao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetStationUtils {

    private static List<StationsBean> list = new ArrayList<>();
    private static Context mContext;
    private static String ALL_STATIONS_URI = "http://bq8h8n.natappfree.cc/api/queryStationName";
    private static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0X11:
                    Toast.makeText(mContext, R.string.get_station_error,Toast.LENGTH_LONG).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public static void GetStationAndInsert(final Context context){
        mContext = context;
        StationInfoDao dao = new StationInfoDao(context);
        if (dao.searchStationName().size() != 0){
            return;//数据表已存在，且有数据，不用重复获取，并插入
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(ALL_STATIONS_URI)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.arg1 = 0X11;
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                list = getStationListFromGson(response.body().string()); // 解析json数据，并写入List<StationBean>中
                insertData(list,context);//将所有数据插入数据库
            }
        });
    }

    public static void insertData(List<StationsBean> list,Context context){
        StationInfoDao dao = new StationInfoDao(context);
        dao.insertDataToDb(list);
    }

    public static List<StationsBean> getStationListFromGson(String jsonString){
        List<StationsBean> list;
        Gson gson = new Gson();
        list = gson.fromJson(jsonString, new TypeToken<List<StationsBean>>(){}.getType());
        return list;
    }

}
