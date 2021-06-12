package com.mybatis.learn.ognl;

import com.mybatis.learn.bean.Blog;
import com.mybatis.learn.bean.Comment;
import com.mybatis.learn.bean.User;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlContext;
import org.apache.ibatis.ognl.OgnlException;

import java.util.*;

public class OgnlTest {

    private static Blog blog;
    private static User author;
    private static List<Comment> comments;
    private static OgnlContext context;

    static {

        blog = new Blog();
        blog.setId("blog id");
        blog.setTitle("blog title");
        blog.setTime(new Date());

        Blog.staticField = "static field";
        author = new User();
        author.setName("user name");
        author.setAge(12);

        blog.setAuthor(author);

        Comment comment = new Comment();
        comment.setId("comment id");
        comment.setContent("comment content");
        comments = new ArrayList<>();
        comments.add(comment);
        blog.setComments(comments);

        context = new OgnlContext();
        context.put("blog", blog);
        context.setRoot(blog);
    }

    public static void main(String[] args) throws OgnlException {
        User user = new User();
        user.setName("another user");
        user.setAge(99);
        user.setName("another user name");
        context.put("author", user);

        // --- 获取指定对象的属性

        // author 是 root对象(blog)的一个属性
        Object obj = Ognl.getValue(Ognl.parseExpression("author"), context, context.getRoot());
        System.out.println("obj = " + obj);

        // 获取 root 中属性 author 的属性
        obj = Ognl.getValue(Ognl.parseExpression("author.name"), context, context.getRoot());
        System.out.println(obj);

        // 操作非root对象
        obj = Ognl.getValue(Ognl.parseExpression("#author.name"), context, context.getRoot());
        System.out.println(obj);

        // --- 调用指定对象的方法
        // 调用实例方法
        obj = Ognl.getValue("author.getName()", context, context.getRoot());
        System.out.println(obj);

        // 调用静态方法
        obj = Ognl.getValue("@com.mybatis.learn.bean.Blog@getStaticField()", context, context.getRoot());
        System.out.println(obj);

        // 获取静态字段
        obj = Ognl.getValue("@com.mybatis.learn.bean.Blog@staticField", context, context.getRoot());
        System.out.println(obj);

        // --- 访问集合元素
        obj = Ognl.getValue("comments[0]", context, context.getRoot());
        System.out.println(obj);

        obj = Ognl.getValue("author.name.length", context, context.getRoot());
        System.err.println(obj);

        obj = Ognl.getValue("author.name.length()", context, context.getRoot());
        System.err.println(obj);

        obj = Ognl.getValue("comments.size", context, context.getRoot());
        System.err.println(obj);

        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        context.put("map", map);
        obj = Ognl.getValue("#map['key2']", context, context.getRoot());
        System.out.println(obj);

    }
}
