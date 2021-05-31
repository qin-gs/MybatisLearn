package com.mybatis.learn.bean;

public class Comment {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "user=" + user +
                '}';
    }
}
