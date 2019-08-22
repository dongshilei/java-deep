package com.dong;

/**
 * 向指定目标输出指定内容
 * 消除Dependancy
 */
public class App {

    /**
     * bootstrap:
     * parse command line
     * set up environment
     * kick off main logic
     */
    public static void main(String[] args) {
        MyApplet mainApplet = Configuration.getMainApplet();
        mainApplet.run();
    }

}
