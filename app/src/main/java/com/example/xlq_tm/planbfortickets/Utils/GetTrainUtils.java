package com.example.xlq_tm.planbfortickets.Utils;

import android.util.Log;

import com.example.xlq_tm.planbfortickets.DataBean.TrainDataBean;
import com.example.xlq_tm.planbfortickets.DataBean.TrainsResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetTrainUtils {

    private static String QUERY_TRAIN_URI = "https://kyfw.12306.cn/otn/leftTicket/";
    private static String TAG = "GetTrainUtils";

    public static void request(String data, String from, String to, String code){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QUERY_TRAIN_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetTrainMessageService service = retrofit.create(GetTrainMessageService.class);
        Call<TrainsResult<TrainDataBean>> call = service.getCall(data,from,to,code);

        call.enqueue(new Callback<TrainsResult<TrainDataBean>>() {
            @Override
            public void onResponse(Call<TrainsResult<TrainDataBean>> call, Response<TrainsResult<TrainDataBean>> response) {
                TrainsResult<TrainDataBean> trainsResult = response.body();
                Log.d("xlq111","map.size = " + trainsResult.getData().getResult().length);
                mListener.onSuccess(trainsResult);
            }

            @Override
            public void onFailure(Call<TrainsResult<TrainDataBean>> call, Throwable t) {
                Log.d("xlq111","error = " + t.toString());
            }
        });

    }

    private static listener mListener;

    public static void setListener(listener listener){
        mListener = listener;
    }

   public interface listener{
        void onSuccess(TrainsResult<TrainDataBean> result);
   }
}
