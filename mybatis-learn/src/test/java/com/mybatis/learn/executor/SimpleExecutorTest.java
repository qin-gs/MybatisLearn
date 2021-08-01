package com.mybatis.learn.executor;

import com.mybatis.learn.MybatisLearnApplication;
import com.mybatis.learn.bean.Blog;
import com.mybatis.learn.bean.User;
import com.mybatis.learn.mapper.BlogMapper;
import org.apache.ibatis.executor.*;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleExecutorTest {

    private Configuration configuration;
    private Connection connection;
    private JdbcTransaction transaction;
    private SqlSessionFactory factory;

    @Before
    public void init() throws SQLException {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        factory = builder.build(MybatisLearnApplication.class.getResourceAsStream("/mybatis-config.xml"));
        configuration = factory.getConfiguration();
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis_learn?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT-8",
                "root", "root");
        transaction = new JdbcTransaction(connection);
    }

    /**
     * 简单执行器
     * 每次都会创建一个新的预处理器(PrepareStatement)
     */
    @Test
    public void simpleTest() throws SQLException {
        SimpleExecutor executor = new SimpleExecutor(configuration, transaction);
        MappedStatement statement = configuration.getMappedStatement("com.mybatis.learn.mapper.BlogMapper.getBlogById");
        List<Blog> blogs = executor.doQuery(statement, "blog-1", RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, statement.getBoundSql(1));
        System.out.println("user.get(0) = " + blogs.get(0));
        assertEquals("blog-1", blogs.get(0).getId());
    }

    /**
     * 可重用处理器
     * ReuseExecutor 只编译一次，可以设置多次参数
     */
    @Test
    public void reuseTest() throws SQLException {
        ReuseExecutor executor = new ReuseExecutor(configuration, transaction);
        MappedStatement statement = configuration.getMappedStatement("com.mybatis.learn.mapper.BlogMapper.getBlogById");
        List<Blog> blogs = executor.doQuery(statement, "blog-1", RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, statement.getBoundSql(1));
        List<Blog> blogs2 = executor.doQuery(statement, "blog-2", RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, statement.getBoundSql(1));

        System.out.println(blogs);
        System.out.println(blogs2);
    }

    /**
     * 批处理执行器
     * 对于修改操作，会编译一次，执行多次
     * 查询操作还是会编译多次
     * 修改以后需要flush
     */
    @Test
    public void batchTest() throws SQLException {
        BatchExecutor executor = new BatchExecutor(configuration, transaction);
        MappedStatement statement = configuration.getMappedStatement("com.mybatis.learn.mapper.BlogMapper.updateBlogTitleById");
        Map<String, Object> params = Map.of("id", "blog-3", "title", "batch update");
        int i = executor.doUpdate(statement, params);
        int j = executor.doUpdate(statement, params);
        executor.doFlushStatements(false);
        assertEquals(i, j);
    }

    /**
     * BaseExecutor
     * 一级缓存
     * 虽然是SimpleExecutor，但是两次查询也编译了一次，因为BaseExecutor里面有缓存
     */
    @Test
    public void baseTest() throws SQLException {
        Executor executor = new SimpleExecutor(configuration, transaction);
        MappedStatement statement = configuration.getMappedStatement("com.mybatis.learn.mapper.BlogMapper.getBlogById");
        List<Object> query = executor.query(statement, "blog-1", RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);
        List<Object> query1 = executor.query(statement, "blog-1", RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);
        assertEquals(query, query1);
    }

    /**
     * CachingExecutor
     * 二级缓存
     * 事务提交之后会将数据放入缓存
     */
    @Test
    public void cacheTest() throws SQLException {
        Executor executor = new SimpleExecutor(configuration, transaction);
        Executor cache = new CachingExecutor(executor);
        MappedStatement statement = configuration.getMappedStatement("com.mybatis.learn.mapper.BlogMapper.getBlogById");

        List<Object> query1 = cache.query(statement, "blog-1", RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);
        cache.commit(true); // 这里不提交 无法命中缓存
        // 先查询二级缓存，找不到的话查询一级缓存
        List<Object> query2 = cache.query(statement, "blog-1", RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);
        cache.commit(true); // 这里不提交 无法命中缓存
        List<Object> query3 = cache.query(statement, "blog-1", RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER);

        assertEquals(query1, query2);
    }

    /**
     * openSession默认是SimpleExecutor，可以修改
     */
    @Test
    public void testSqlSession() {
        SqlSession sqlSession = factory.openSession(ExecutorType.SIMPLE, true);
        List<Blog> blogs = sqlSession.selectList("com.mybatis.learn.mapper.BlogMapper.getBlogById", "blog-1");
        System.out.println(blogs);

        // UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    }

    @Test
    public void testSpring() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring.xml");
        BlogMapper mapper = context.getBean(BlogMapper.class);

        User user1 = mapper.selectUserByBlogId(""); // 每次都会构造一个新会话，两次调用不会命中缓存
        User user2 = mapper.selectUserByBlogId("");

        // 手动开启事务，事务未结束之前，所有的查询用用同一个sqlSession
        DataSourceTransactionManager manager = (DataSourceTransactionManager) context.getBean("txManager");
        TransactionStatus status = manager.getTransaction(new DefaultTransactionDefinition());

    }
}
