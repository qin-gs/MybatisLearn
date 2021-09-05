package com.mybatis.learn.node;

import com.mybatis.learn.MybatisLearnApplication;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;

@DisplayName("sqlNode 测试")
public class SqlNodeTest {

    private static Configuration configuration;

    @BeforeAll
    static void init() throws SQLException {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(MybatisLearnApplication.class.getResourceAsStream("/mybatis-config.xml"));
        configuration = factory.getConfiguration();
    }

    /**
     * if, where的SqlNode节点
     */
    @Test
    public void test() {
        User user = new User();
        user.setId("1");
        user.setName("qqq");

        DynamicContext context = new DynamicContext(configuration, user);
        new StaticTextSqlNode("select * from user").apply(context);

        IfSqlNode ifSqlNode1 = new IfSqlNode(new StaticTextSqlNode(" and id=#{id}"), "id != null");
        IfSqlNode ifSqlNode2 = new IfSqlNode(new StaticTextSqlNode(" or name=#{name}"), "name != null");

        MixedSqlNode mixedSqlNode = new MixedSqlNode(Arrays.asList(ifSqlNode1, ifSqlNode2));
        // 需要加上where，去除一些and, or等
        WhereSqlNode whereSqlNode = new WhereSqlNode(configuration, mixedSqlNode);
        whereSqlNode.apply(context);

        System.out.println(context.getSql());
    }

    /**
     * forEach节点
     */
    public void forEachTest() {

    }
}
