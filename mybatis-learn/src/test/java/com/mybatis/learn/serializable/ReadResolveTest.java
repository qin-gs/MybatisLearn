package com.mybatis.learn.serializable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Objects;

@DisplayName("使用readResolve恢复单例枚举类型的字段")
public class ReadResolveTest {
    /**
     * 序列化对象
     */
    @Test
    public void write() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dog"))) {
            oos.writeObject(Dog.A);
        }
    }

    @Test
    public void read() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dog"))) {
            Dog dog = (Dog) ois.readObject();
            System.out.println(dog);
            // 使用readResolve方法保证这里是同一个对象
            System.out.println(Objects.equals(dog, Dog.A));
        }
    }
}

class Dog implements Serializable {
    private static final long serialVersionUID = 363078090301545215L;
    private String name;

    public Dog(String name) {
        this.name = name;
    }

    public static final Dog A = new Dog("a");
    public static final Dog B = new Dog("b");

    /**
     * 自己定义的单例或枚举对象需要在反序列化时进行处理
     */
    private Object readResolve() {
        if (Objects.equals(name, "a")) {
            return A;
        } else if (Objects.equals(name, "b")) {
            return B;
        }
        return null;
    }


    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                '}';
    }
}