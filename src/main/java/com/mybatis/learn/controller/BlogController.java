package com.mybatis.learn.controller;

import com.mybatis.learn.bean.Blog;
import com.mybatis.learn.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BlogController {

    @Resource
    private BlogService service;

    @GetMapping("getBlog/{id}")
    public Blog getBlogById(@PathVariable String id) {
        return service.getBlogById(id);
    }
}
