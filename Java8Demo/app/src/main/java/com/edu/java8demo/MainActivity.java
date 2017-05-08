package com.edu.java8demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Apple apple1 = new Apple(1,"1");
        Apple apple2 = new Apple(3,"1");
        Apple apple3 = new Apple(2,"1");
        Apple apple4 = new Apple(4,"1");
        List<Apple> apples = new ArrayList();
        apples.add(apple1);
        apples.add(apple2);
        apples.add(apple3);
        apples.add(apple4);


        apples.sort(Comparator.comparing(Apple::getWeight));

        for (Apple apple : apples) {
            Log.d("MainActivity", "apple.getWeight():" + apple.getWeight());
        }


    }
}
