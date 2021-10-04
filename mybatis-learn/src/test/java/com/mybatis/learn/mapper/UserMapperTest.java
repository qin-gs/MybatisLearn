package com.mybatis.learn.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {

	@Autowired
	private UserMapper mapper;

	@Test
	public void selectBlogById() {
		System.out.println(mapper.selectBlogById("blog-1"));
	}
}