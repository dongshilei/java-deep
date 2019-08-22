package com.dong;

import com.dong.guicedemo.MyDestination;

import java.io.PrintStream;

/**
 * Created by DONGSHILEI on 2017/11/14
 */
public class PrintStreamWriter implements MyDestination {
    private PrintStream destination;

    public PrintStreamWriter(PrintStream destination) {
        this.destination = destination;
    }

    @Override
    public void write(String s) {
        destination.println(s);
    }
}
