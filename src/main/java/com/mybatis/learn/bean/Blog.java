package com.mybatis.learn.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Blog implements Serializable {
    private String id;
    private String title;
    private User author;
    private String body;
    private Date time;
    private List<Comment> comments;

    public static String staticField;

    public static String getStaticField() {
        return staticField;
    }

    public Blog() {
    }

    public Blog(String title) {
        this.title = title + "_constructor";
    }
}
