package com.learn.processor;

import com.learn.dao.UserDao;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.mapper.MapperFactoryBean;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

/**
 * 使用FactoryBean自己创建代理对象，不通过spring创建
 */
public class MyMapperFactoryBean extends MapperFactoryBean<UserDao> {

    @Override
    public UserDao getObject() throws Exception {
        // 生成代理对象
        UserDao dao = (UserDao) Proxy.newProxyInstance(
                MyMapperFactoryBean.class.getClassLoader(),
                new Class[]{UserDao.class},
                (proxy, method, args) -> {
                    Select select = method.getAnnotation(Select.class);
                    String[] sqls = select.value();
                    // 执行sql语句
                    System.out.println(Arrays.toString(sqls));
                    return List.of("sql执行结果");
                }
        );
        return dao;
    }

    @Override
    public Class<UserDao> getObjectType() {
        return UserDao.class;
    }
}
