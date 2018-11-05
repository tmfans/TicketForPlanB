package com.example.xlq_tm.planbfortickets.Utils;

import com.example.xlq_tm.planbfortickets.DataBean.TrainDataBean;
import com.example.xlq_tm.planbfortickets.DataBean.TrainsResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetTrainMessageService {

    @GET("query")
    Call<TrainsResult<TrainDataBean>> getCall(@Query("leftTicketDTO.train_date") String date,
                                              @Query("leftTicketDTO.from_station") String from_station,
                                              @Query("leftTicketDTO.to_station") String to_station,
                                              @Query("purpose_codes") String code);

}
