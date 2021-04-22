package com.zbl.springdemo.utils;

/**
 * @Description TODO
 * @Author ZhuBoLin
 * @Data 2021/4/22 17:09
 * @Version
 **/


public class Threads {

    public static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(Long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
