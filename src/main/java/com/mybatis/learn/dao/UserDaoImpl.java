package com.mybatis.learn.dao;

import com.mybatis.learn.bean.Blog;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class UserDaoImpl extends SqlSessionDaoSupport implements BaseDao {

	public Blog getBlog(String id) {
		return getSqlSession().selectOne("com.mybatis.learn.mapper.getBlog", id);
	}

}
