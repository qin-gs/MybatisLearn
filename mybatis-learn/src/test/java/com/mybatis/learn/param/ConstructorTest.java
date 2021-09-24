package com.mybatis.learn.param;

import java.lang.reflect.Constructor;

public class ConstructorTest {
    public static void main(String[] args) {
        Class clazz = User.class;
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
            // if (!constructor.isAccessible()) {
            // 	constructor.setAccessible(true);
            // }
        }
    }

    private class User {
        private String name;
        private int age;

        public User() {
        }

        public User(String name) {
            this.name = name;
        }

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return this.name;
        }

        public int getAge() {
            return this.age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}

