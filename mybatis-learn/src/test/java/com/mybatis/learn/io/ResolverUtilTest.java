package com.mybatis.learn.io;

import com.mybatis.learn.proxy.Subject;
import org.apache.ibatis.io.ResolverUtil;

import java.util.Set;

public class ResolverUtilTest {
    public static void main(String[] args) {
        ResolverUtil<Subject> util = new ResolverUtil<>();
        util.findImplementations(Subject.class, "com.mybatis.learn");
        Set<Class<? extends Subject>> classes = util.getClasses();
        System.out.println(classes);
    }
}
