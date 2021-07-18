package com.mybatis.learn.cache;

import com.mybatis.learn.MybatisLearnApplication;
import com.mybatis.learn.bean.Blog;
import com.mybatis.learn.mapper.BlogMapper;
import net.minidev.json.writer.BeansMapper;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.logging.jdbc.BaseJdbcLogger;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SecondCacheTest {

	private Configuration configuration;
	private SqlSessionFactory factory;

	@Before
	public void init() throws SQLException {
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		factory = builder.build(MybatisLearnApplication.class.getResourceAsStream("/mybatis-config.xml"));
		configuration = factory.getConfiguration();
	}

	@Test
	public void cacheTest() {
		Cache cache = configuration.getCache("com.mybatis.learn.mapper.BlogMapper");
		Blog blog = new Blog();
		cache.putObject("key", blog);
		cache.getObject("key");
	}

	/**
	 * 二级缓存命中条件
	 * 1. 必须提交(autoCommit=true不行，必须commit或close)
	 * 2. sql相同，参数相同
	 * 3. 相同的statementId
	 * 4. RoundBounds相同
	 */
	@Test
	public void cache2Test() {
		SqlSession session1 = factory.openSession();
		BlogMapper mapper1 = session1.getMapper(BlogMapper.class);
		Blog blog1 = mapper1.getBlogById("blog-1");
		System.out.println(blog1);

		session1.commit();

		SqlSession session2 = factory.openSession();
		BlogMapper mapper2 = session2.getMapper(BlogMapper.class);
		Blog blog2 = mapper2.getBlogById("blog-1");
		System.out.println(blog2);

		System.out.println(blog1.equals(blog2));
	}
}
