package com.mybatis.learn.mapper;

import com.mybatis.learn.bean.Blog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    Blog selectBlogById(String id);
}
