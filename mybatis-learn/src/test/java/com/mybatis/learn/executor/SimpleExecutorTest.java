package com.mybatis.learn.executor;

import com.mybatis.learn.MybatisLearnApplication;
import com.mybatis.learn.mapper.BlogMapper;
import com.mybatis.learn.mapper.UserMapper;
import com.mysql.cj.jdbc.JdbcConnection;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.mybatis.learn.bean.*;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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

    @Test
    public void simpleTest() throws SQLException {
        SimpleExecutor executor = new SimpleExecutor(configuration, transaction);
        MappedStatement statement = configuration.getMappedStatement("com.mybatis.learn.mapper.BlogMapper.getBlogById");
        List<User> user = executor.doQuery(statement, "blog-1", RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, statement.getBoundSql(1));
        System.out.println("user.get(0) = " + user.get(0));

    }

    @Test
    public void testSqlSession() {
        SqlSession sqlSession = factory.openSession(ExecutorType.SIMPLE, true);
        List<Object> objects = sqlSession.selectList("com.mybatis.learn.mapper.BlogMapper.getBlogById", "blog-1");
        System.out.println(objects);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    }

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
