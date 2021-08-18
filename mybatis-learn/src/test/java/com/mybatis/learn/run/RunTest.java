package com.mybatis.learn.run;

import com.mybatis.learn.meta.Blog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RunTest {
	public static void main(String[] args) throws IOException {
		SqlSessionFactory factory = new SqlSessionFactoryBuilder()
				.build(Resources.getResourceAsReader("mybatis-config.xml"));
		SqlSession session = factory.openSession();
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", "1");
		Blog blog = session.selectOne("com.mybatis.learn.mapper.BlogMapper", parameters);
		System.out.println("blog = " + blog);
		session.close();
	}
}
