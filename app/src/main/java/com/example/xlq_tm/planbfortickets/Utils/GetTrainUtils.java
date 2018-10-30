package com.example.xlq_tm.planbfortickets.Utils;

import android.util.Log;

import com.example.xlq_tm.planbfortickets.DataBean.TrainsResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetTrainUtils {

    private static String QUERY_TRAIN_URI = "https://kyfw.12306.cn/otn/leftTicket/";
    private static String TAG = "GetTrainUtils";

    public static void request(String data, String from, final String to, String code){
        Log.d(TAG,"request");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QUERY_TRAIN_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetTrainMessageService service = retrofit.create(GetTrainMessageService.class);
        Call<TrainsResult> call = service.getCall(data,from,to,code);
        call.enqueue(new Callback<TrainsResult>() {
            @Override
            public void onResponse(Call<TrainsResult> call, Response<TrainsResult> response) {
                TrainsResult trainsResult = response.body();
            }

            @Override
            public void onFailure(Call<TrainsResult> call, Throwable t) {
                Log.d(TAG,"error = " + t.toString());
            }
        });

    }
}
