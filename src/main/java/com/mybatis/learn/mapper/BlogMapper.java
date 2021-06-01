package com.mybatis.learn.mapper;

import com.mybatis.learn.bean.Blog;
import com.mybatis.learn.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogMapper {

    Blog getBlogById(String id);

    User selectUserByBlogId(String id);
}
