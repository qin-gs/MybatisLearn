package com.qin.learn.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qin.learn.bean.User;
import com.qin.learn.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    public void select() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("email")
                .gt("age", 10);
        List<User> users = mapper.selectList(wrapper);
        System.out.println(users);
    }

    @Test
    public void select2() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "qqq");
        User user = mapper.selectOne(wrapper);
        System.out.println(user);
    }
}
