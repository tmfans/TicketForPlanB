package com.example.xlq_tm.planbfortickets.DataBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TrainDataBean implements Serializable{

    private String flag;
    private Map<String ,String > map;
    private List<String> result;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> cityMap) {
        this.map = cityMap;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
