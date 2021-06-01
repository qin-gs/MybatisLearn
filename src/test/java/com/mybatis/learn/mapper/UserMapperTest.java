package com.mybatis.learn.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

	@Autowired
	private UserMapper mapper;

	@Test
	public void selectBlogById() {
		System.out.println(mapper.selectBlogById("blog-1"));
	}
}