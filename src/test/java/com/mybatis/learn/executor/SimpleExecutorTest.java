package com.mybatis.learn.executor;

import com.mybatis.learn.MybatisLearnApplication;
import com.mysql.cj.jdbc.JdbcConnection;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.mybatis.learn.bean.*;
import org.junit.Test;

public class SimpleExecutorTest {

    private Configuration configuration;
    private Connection connection;
    private JdbcTransaction transaction;

    @Before
    public void init() throws SQLException {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(MybatisLearnApplication.class.getResourceAsStream("application.yml"));
        configuration = factory.getConfiguration();
        connection = DriverManager.getConnection("", "root", "root");
        transaction = new JdbcTransaction(connection);
    }

    @Test
    public void simpleTest() throws SQLException {
        SimpleExecutor executor = new SimpleExecutor(configuration, transaction);
        MappedStatement statement = configuration.getMappedStatement("com.mybatis.learn.mapper.UserMapper.getUserById");
        List<User> user = executor.doQuery(statement, 1, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, statement.getBoundSql(1));
        System.out.println("user.get(0) = " + user.get(0));

    }
}
