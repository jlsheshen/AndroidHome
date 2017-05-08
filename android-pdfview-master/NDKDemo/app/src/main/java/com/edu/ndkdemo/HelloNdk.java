package com.edu.ndkdemo;

/**
 * Created by Administrator on 2017/5/2.
 */

public class HelloNdk {
    static {
        //装备<Tn>
        System.loadLibrary("HelloNdk");
    }
    public static native String sayHello();
}
