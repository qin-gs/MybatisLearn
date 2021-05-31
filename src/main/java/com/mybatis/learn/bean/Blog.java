package com.mybatis.learn.bean;

import java.util.List;

public class Blog {
    private String id;
    private String title;
    private User author;
    private String body;
    private List<Comment> comments;

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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", body='" + body + '\'' +
                ", comments=" + comments +
                '}';
    }
}
