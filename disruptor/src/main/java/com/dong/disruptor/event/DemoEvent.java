package com.dong.disruptor.event;

import javafx.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/7/1 17:35
 **/
public class DemoEvent {
    private List<String> params;

    public DemoEvent(){
        params = new ArrayList<>();
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "DemoEvent{" +
                "params=" + params +
                '}';
    }
}
