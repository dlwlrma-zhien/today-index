package com.lcyy.stock.config;

import java.util.Map;

public abstract class StockTaskRunable implements Runnable{
    //携带的任务信息,任务拒绝时，使用
    private Map<String,Object> infos;

    public StockTaskRunable(Map<String, Object> infos) {
        this.infos = infos;
    }

    //提供get方法
    public Map<String, Object> getInfos() {
        return infos;
    }
}