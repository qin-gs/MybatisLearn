package com.mybatis.learn.cache;

import com.mybatis.learn.MybatisLearnApplication;
import com.mybatis.learn.bean.Blog;
import com.mybatis.learn.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.transaction.TransactionManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DisplayName("一级缓存测试")
public class FirstCacheTest {
    private static SqlSessionFactory factory;
    private static SqlSession sqlSession;

    @BeforeAll
    static void init() {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        factory = builder.build(MybatisLearnApplication.class.getResourceAsStream("/mybatis-config.xml"));
        sqlSession = factory.openSession();
    }

    /**
     * 一级缓存命中场景
     * <p>
     * 1. sql + 参数 必须相同
     * 2. statementId 相同
     * 3. sqlSession相同(一级缓存是会话级别的)
     * 4. RowBounds相同
     * <p>
     * 5. 没有手动清空 提交 回滚 sqlSession.clearCache();
     * 6. 未调用flushCache=true的查询 (@Optional(flushCache=true))
     * 7. 未执行update操作
     * 8. 缓存作用域不是Statement(xml中设置localCacheScope=LocalCacheScope.STATEMENT)
     */
    @Test
    public void test() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Blog blog1 = mapper.selectBlogById("blog-1");
        Blog blog2 = mapper.selectBlogById("blog-1");
        assertEquals(blog1, blog2);
    }

    @Test
    public void testSpring() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UserMapper mapper = context.getBean(UserMapper.class);
        // mapper -> SqlSessionTemplate -> SqlSessionInterceptor -> SalSessionFactory
        // 动态代理接口(MapperProxy)  会话模板(SqlSessionProxy) 会话拦截器  会话工厂

        // 获取事务管理器
        DataSourceTransactionManager manager = ((DataSourceTransactionManager) context.getBean("txManager"));
        // 手动开启事务
        TransactionStatus status = manager.getTransaction(new DefaultTransactionDefinition());

        // 以下两次查询，不会命中一级缓存
        // 因为每次都会创建新会话
        // 需要加上事务才能命中缓存
        Blog blog1 = mapper.selectBlogById("blog-1");
        Blog blog2 = mapper.selectBlogById("blog-1");

    }
}
