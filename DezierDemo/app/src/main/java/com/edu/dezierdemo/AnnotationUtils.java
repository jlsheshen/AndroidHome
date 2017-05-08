package com.edu.dezierdemo;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/2/8.
 */

public class AnnotationUtils {
    public static void bindView(Activity activity){
        Class<? extends Activity> object = activity.getClass();
        Field[] fields = object.getDeclaredFields();
        for (Field field : fields) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null){
                int viewId = bindView.value();
                try {
                    Method method = object.getMethod("findViewById",int.class);
                    Object resView = method.invoke(activity,viewId);
                    field.setAccessible(true);
                    field.set(activity,resView);


                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }


        }



    }
}
