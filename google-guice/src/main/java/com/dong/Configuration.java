package com.dong;

import com.dong.guicedemo.StringProvider;
import com.dong.guicedemo.StringWritingApplet;

/**
 * Created by DONGSHILEI on 2017/11/14
 */
public class Configuration {

    public static MyApplet getMainApplet() {
        return new StringWritingApplet(new PrintStreamWriter(System.out), new StringProvider() {
            @Override
            public String get() {
                return "Hello World!";
            }
        });
    }
}
