package com.example.xlq_tm.planbfortickets.Utils;


import android.content.Context;
import android.util.Log;

import com.example.xlq_tm.planbfortickets.DataBean.StationsBean;
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
    private static String ALL_STATIONS_URI = "http://k6b6yq.natappfree.cc/api/queryStationName";

    public static void GetStationAndInsert(final Context context){
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(ALL_STATIONS_URI)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("xlq111","onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                list = getStationListFromGson(response.body().string()); // 解析json数据，并写入List<StationBean>中
                Log.d("xlq111","gson 解析结束，并开始插入");
                insertData(list,context);//将所有数据插入数据库
            }
        });
    }

    public static void insertData(List<StationsBean> list,Context context){
        StationInfoDao dao = new StationInfoDao(context);
        dao.insertDataToDb(list);
    }

    public static List<StationsBean> getStationListFromGson(String jsonString){
        List<StationsBean> list = new ArrayList<>();
        Gson gson = new Gson();
        list = gson.fromJson(jsonString, new TypeToken<List<StationsBean>>(){}.getType());
        return list;
    }

}
