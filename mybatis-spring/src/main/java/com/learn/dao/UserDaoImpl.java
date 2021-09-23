package com.learn.dao;

import com.learn.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * {@code SqlSessionDaoSupport#getSqlSession()}用来提供SqlSession
 */
// @Mapper
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {
    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 进行手动事务管理
     */
    public void addUser() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        // 手动提交回滚事务
        try {
            // 插入用户
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new RuntimeException(e.getMessage());
        }
        transactionManager.commit(status);

        // 使用TransactionTemplate自动提交回滚
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.execute(status_ -> {
            // 插入用户
            return null;
        });
    }

    /**
     * 使用父类中的方法获取SqlSession
     */
    @Override
    public List<User> getUsers() {
        return getSqlSession().selectList("com.learn.dao.UserDao.getUsers");
    }
}
