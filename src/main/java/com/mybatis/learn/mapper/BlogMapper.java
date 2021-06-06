package com.mybatis.learn.mapper;

import com.mybatis.learn.bean.Blog;
import com.mybatis.learn.bean.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@CacheNamespace
public interface BlogMapper {

	// @Select("select * from blog where id=#{id}")
	Blog getBlogById(String id);

	User selectUserById(String id);

	User selectUserByBlogId(String id);
}
