package com.mybatis.learn.mapper;

import com.mybatis.learn.bean.Blog;
import com.mybatis.learn.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
// @CacheNamespace
public interface BlogMapper {

	@Select("select * from blog where id=#{id}")
	@Results(id = "blog", value = {
			@Result(property = "id", column = "id", id = true),
			@Result(property = "author", column = "userid", javaType = User.class,
					one = @One(select = "com.mybatis.learn.mapper.BlogMapper.selectUserById")),
			@Result(property = "comments", column = "id", javaType = List.class,
					many = @Many(select = "com.mybatis.learn.mapper.BlogMapper.selectUserByBlogId")),
			@Result(property = "time", column = "time", typeHandler = com.mybatis.learn.handler.DateStringTypeHandler.class)
	})
	@ConstructorArgs({@Arg(column = "title", javaType = String.class)})
	@Options(keyColumn = "id")
	Blog getBlogById(@Param("id") String id);

	User selectUserById(@Param("id") String id);

	User selectUserByBlogId(@Param("id") String id);

	@Select(value = {
			"<script>",
			"	select * from user where id in ",
			"		<foreach item='item' index='index' open='(' close=')' separator=', ' collection='ids'>",
			"			#{item}",
			"		</foreach>",
			"</script>"})
	@MapKey("id")
	Map<String, User> selectUserMapById(List<String> ids);

	@Insert("insert into blog(id, title, body, time) values (#{blog.id}, #{blog.title}, #{blog.body}," +
			" #{blog.time, typeHandler=com.mybatis.learn.handler.DateStringTypeHandler})")
	// @SelectKey(statement = "call identity()", keyProperty = "id", before = true, resultType = String.class)
	int insertBlog(@Param("blog") Blog blog);

	int updateBlogTitleById(@Param("id")String id, @Param("title")String title);
}
