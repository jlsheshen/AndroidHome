package com.edu.counternativedemo;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Administrator on 2017/5/4.
 */

public class CounterNative {
    private static Handler handler;
    static {
        System.loadLibrary("CounterNative");
    }

    public CounterNative() {
        handler  = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("CounterNative", "msg:" + msg.obj.toString());

            }
        };

        nativeSetup();
    }
    public static void setValue(int value){
        String str = "Value(static) = " + String.valueOf(value);
        Message m = handler.obtainMessage(1,1,1,str);
        handler.sendMessage(m);
    }
    public  void setV(int value){
        String str = "V(static) = " + String.valueOf(value);
        Message m = handler.obtainMessage(1,1,1,str);
        handler.sendMessage(m);
    }


    native void nativeSetup();
    native void nativeExec(int number);
     native static void nativeExecute(int number);

}
