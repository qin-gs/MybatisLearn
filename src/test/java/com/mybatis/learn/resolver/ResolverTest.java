package com.mybatis.learn.resolver;

import com.mybatis.learn.bean.Blog;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.plugin.Interceptor;

import java.util.Set;

public class ResolverTest {
	public static void main(String[] args) {
		ResolverUtil<A> resolver = new ResolverUtil<>();
		String packageName = "com.mybatis.learn";
		resolver.findImplementations(A.class, packageName); // 查找包下实现某个类的类
		resolver.find(Class::isInterface, packageName); // 查找packageName包下符合条件的类

		Set<Class<? extends A>> classes = resolver.getClasses(); // 获取上面查找的结构
		System.out.println(classes);
	}
}
