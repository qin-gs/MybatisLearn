package com.learn.config;

import com.learn.anno.MyMapperScan;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@MyMapperScan
@ComponentScan("com.learn")
public class MyConfig {

    /**
     * 这是一个FactoryBean会创建一个SqlSessionFactory
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        // 使用自己的事务管理器，不使用spring的
        // bean.setTransactionFactory(new ManagedTransactionFactory());
        bean.setDataSource(dataSource);
        return bean;
    }

    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis_learn?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT-8");
        return dataSource;
    }

    /**
     * 注入之后可以直接在dao代码中注入SqlSession(线程安全)
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Autowired SqlSessionFactory factory) {
        return new SqlSessionTemplate(factory, ExecutorType.BATCH);
    }

    /**
     * 使用spring默认的事务管理器
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
