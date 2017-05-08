package com.edu.java8demo;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Apple {
    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }

    private int weight;
    private String color;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
