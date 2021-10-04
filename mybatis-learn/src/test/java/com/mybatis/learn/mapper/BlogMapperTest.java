package com.mybatis.learn.mapper;

import com.mybatis.learn.bean.Blog;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BlogMapperTest {

    @Resource
    private BlogMapper mapper;

    @Test
    public void selectUserByBlogId() {
        System.out.println(mapper.selectUserByBlogId("blog-1"));
    }

    @Test
    public void getBlogById() {
        System.out.println(mapper.getBlogById("blog-1"));
    }

	@Test
	public void selectUserMapById() {
		System.out.println(mapper.selectUserMapById(Arrays.asList("user-1", "user-2", "user-3")));
	}

	@Test
	@Transactional
	public void insertBlog() {
		Blog blog = new Blog();
		blog.setId("insert id");
		blog.setTitle("insert title");
		blog.setBody("insert body");
		blog.setTime(new Date());
		System.out.println(mapper.insertBlog(blog));
	}
}