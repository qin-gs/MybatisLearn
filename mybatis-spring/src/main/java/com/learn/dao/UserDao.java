package com.learn.dao;

import com.learn.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// @Mapper
public interface UserDao {

    @Select("select * from user")
    List<User> getUsers();
}
