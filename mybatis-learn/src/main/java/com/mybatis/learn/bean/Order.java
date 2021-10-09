package com.mybatis.learn.bean;

public class Order {
    private String id;
    private String package_;
    private String weight;
    private String type;
    private String sum;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackage_() {
        return package_;
    }

    public void setPackage_(String package_) {
        this.package_ = package_;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", package_='" + package_ + '\'' +
                ", weight='" + weight + '\'' +
                ", type='" + type + '\'' +
                ", sum='" + sum + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
