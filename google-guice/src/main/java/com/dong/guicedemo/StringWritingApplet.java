package com.dong.guicedemo;

import com.dong.MyApplet;

/**
 * Created by DONGSHILEI on 2017/11/14
 */
public class StringWritingApplet implements MyApplet {

    private MyDestination destination;
    private StringProvider stringProvider;

    public StringWritingApplet(MyDestination destination, StringProvider stringProvider) {
        this.destination = destination;
        this.stringProvider = stringProvider;
    }

    private void printHelloWorld() {
        destination.write(stringProvider.get());
    }

    @Override
    public void run() {
        printHelloWorld();
    }
}
