package com.eteng.moblieplayer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by gch on 2016/7/6.
 *
 */
public class StringTimeUtil {
    private StringBuilder mformatbulider;
    private Formatter mformatter;

    public StringTimeUtil(){
        mformatbulider=new StringBuilder();
        mformatter=new Formatter(mformatbulider, Locale.getDefault());
    }

    /***
     * 毫秒转格式
     *
     * @param timeMs
     */
    public String stringfortime(int timeMs) {
        int totalsecond = timeMs / 1000;
        int second = totalsecond % 60;
        int minutes = (totalsecond / 60) % 60;

        int hour=totalsecond/3600;

        mformatbulider.setLength(0);
        if(hour>0){
            return mformatter.format("%d:%02d:%02d",hour,minutes,second).toString();
        }else {
            return mformatter.format("%02d:%02d",minutes,second).toString();
        }
    }

    /***
     * 得到系统时间
     * @return
     */
    public String getSystemTime(){
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }
}
