package com.mybatis.learn.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.mybatis.learn.bean.*;
import org.apache.ibatis.mapping.StatementType;


@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{1}")
    @Options(statementType = StatementType.PREPARED)
    User getUserById(String id);
}
