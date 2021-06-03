package com.mybatis.learn.service;

import com.mybatis.learn.bean.Blog;
import com.mybatis.learn.mapper.BlogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BlogService {

    @Resource
    private BlogMapper blogMapper;

    public List<Blog> getBlogById(String id) {
        return blogMapper.getBlogById(id);
    }

}
