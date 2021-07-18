package com.qin.learn.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qin.learn.bean.User;
import com.qin.learn.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserMapper mapper;

    @Test
    public void insert() {
        User user = new User();
        user.setName("www");
        user.setAge(10);
        user.setEmail("123@qq.com");
        // user.setVersion(0);
        // user.setDelete(0);

        mapper.insert(user);

    }

    @Test
    public void update() {
        User user = new User();
        user.setId("46b5b3b6453183633a14228937ac16e9");
        user.setName("update");
        user.setAge(12);
        user.setEmail("123456@qq.com");
        mapper.updateById(user);
    }

    @Test
    public void select() {

        List<User> users = mapper.selectBatchIds(Arrays.asList("1", "2", "3"));
        users.forEach(System.out::println);

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "qqq");
        List<User> usersByMap = mapper.selectByMap(map);
        usersByMap.forEach(System.out::println);
    }

    @Test
    public void delete() {
        mapper.deleteById("42e019b44d0109c6ddb1261ef9095a22");
    }


    @Test
    public void lockTest() {
        User user = mapper.selectById("46b5b3b6453183633a14228937ac16e9");
        user.setAge(123);
        mapper.updateById(user);
    }

    @Test
    public void pageTest() {
        Page<User> page = new Page<>(1, 5);
        mapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
    }

}
