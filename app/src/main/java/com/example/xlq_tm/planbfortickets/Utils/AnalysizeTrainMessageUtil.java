package com.example.xlq_tm.planbfortickets.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.xlq_tm.planbfortickets.DataBean.TrainDataBean;
import com.example.xlq_tm.planbfortickets.DataBean.TrainsResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysizeTrainMessageUtil {

    public static List<Map<String,Object>> Analysize(TrainsResult<TrainDataBean> trainsResult){
        List<Map<String,Object>> cs = new ArrayList<>();
        Log.d("xlq111","cv = " + trainsResult.getData().getMap());
        Map<String,String> cv = trainsResult.getData().getMap();

        for (String s : trainsResult.getData().getResult()){
            String [] cq = s.split("\\|");

            Map<String,Object> cw = new HashMap<>();
            cw.put("secretHBStr",cq[36]);
            cw.put("secretStr",cq[0]);
            cw.put("buttonTextInfo",cq[1]);

            Map<String,String> cu = new HashMap<>();
            cu.put("train_no",cq[2]);
            cu.put("station_train_code",cq[3]);
            cu.put("start_station_telecode",cq[4]);
            cu.put("end_station_telecode",cq[5]);
            cu.put("from_station_telecode",cq[6]);
            cu.put("to_station_telecode",cq[7]);
            cu.put("start_time",cq[8]);
            cu.put("arrive_time",cq[9]);
            cu.put("lishi",cq[10]);
            cu.put("canWebBuy",cq[11]);
            cu.put("yp_info",cq[12]);
            cu.put("start_train_date",cq[13]);
            cu.put("train_seat_feature",cq[14]);
            cu.put("location_code",cq[15]);
            cu.put("from_station_no",cq[16]);
            cu.put("to_station_no",cq[17]);
            cu.put("is_support_card",cq[18]);
            cu.put("controlled_train_flag",cq[19]);

            cu.put("gg_num", TextUtils.isEmpty(cq[20])? "--" :cq[20]);
            cu.put("gr_num", TextUtils.isEmpty(cq[21])? "--" :cq[21]);
            cu.put("qt_num", TextUtils.isEmpty(cq[22])? "--" :cq[22]);
            cu.put("rw_num", TextUtils.isEmpty(cq[23])? "--" :cq[23]);
            cu.put("rz_num", TextUtils.isEmpty(cq[24])? "--" :cq[24]);
            cu.put("tz_num", TextUtils.isEmpty(cq[25])? "--" :cq[25]);
            cu.put("wz_num", TextUtils.isEmpty(cq[26])? "--" :cq[26]);
            cu.put("yb_num", TextUtils.isEmpty(cq[27])? "--" :cq[27]);
            cu.put("yw_num", TextUtils.isEmpty(cq[28])? "--" :cq[28]);
            cu.put("yz_num", TextUtils.isEmpty(cq[29])? "--" :cq[29]);
            cu.put("ze_num", TextUtils.isEmpty(cq[30])? "--" :cq[30]);
            cu.put("zy_num", TextUtils.isEmpty(cq[31])? "--" :cq[31]);
            cu.put("swz_num", TextUtils.isEmpty(cq[32])? "--" :cq[32]);
            cu.put("srb_num", TextUtils.isEmpty(cq[33])? "--" :cq[33]);

            cu.put("yp_ex",cq[34]);
            cu.put("seat_types",cq[35]);
            cu.put("exchange_train_flag",cq[36]);
            cu.put("from_station_name",cv.get(cq[6]));
            cu.put("to_station_name",cv.get(cq[7]));

            cw.put("queryLeftNewDTO",cu);
            cs.add(cw);
        }
        return cs;
    }

}
