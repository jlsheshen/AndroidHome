package com.edu.jnidemo;

/**
 * Created by Administrator on 2017/4/28.
 */

public class NdkJniUtils {
    static {
        System.loadLibrary("NdkJniUtils");
    }

    public static native String getCLanguageString();
}
