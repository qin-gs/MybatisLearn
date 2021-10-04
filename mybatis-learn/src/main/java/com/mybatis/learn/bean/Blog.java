package com.mybatis.learn.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static void setStaticField(String staticField) {
        Blog.staticField = staticField;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", body='" + body + '\'' +
                ", time=" + time +
                ", comments=" + comments +
                '}';
    }
}
