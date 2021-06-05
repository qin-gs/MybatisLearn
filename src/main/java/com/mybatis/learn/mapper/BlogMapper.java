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
	// @Options(flushCache = Options.FlushCachePolicy.TRUE)
	Blog getBlogById(String id);

	User selectUserByBlogId(String id);
}
