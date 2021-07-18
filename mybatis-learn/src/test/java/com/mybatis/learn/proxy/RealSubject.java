package com.mybatis.learn.proxy;

public class RealSubject implements Subject {

    @Override
    public void operate() {
        System.out.println("原始类的operate");
    }
}
