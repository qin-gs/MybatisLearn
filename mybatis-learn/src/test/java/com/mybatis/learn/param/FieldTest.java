package com.mybatis.learn.param;

import com.mybatis.learn.bean.User;

import java.lang.reflect.Field;

public class FieldTest {
	public static void main(String[] args) throws IllegalAccessException {
		User user = new User();
		user.setName("qqq");
		user.setAge(12);
		user.setGender("F");

		User dest = new User();

		Class parent = User.class;
		while (parent != null) {
			final Field[] fields = parent.getDeclaredFields(); // 不包含继承过来的
			for (Field field : fields) {
				field.setAccessible(true);
				field.set(dest, field.get(user));
			}
			parent = parent.getSuperclass();
		}
		System.out.println("dest = " + dest);
	}
}
