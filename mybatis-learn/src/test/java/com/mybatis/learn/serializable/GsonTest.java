package com.mybatis.learn.serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@DisplayName("gson HashMap 测试")
public class GsonTest {

	@Test
	public void test() {
		HashMap<String, String> map = new HashMap<>() {
			private final String s = "s"; // 这个值不会被序列化

			{

				put("a", "1");
				put("b", "2");
				put("c", "3");
			}
		};
		Gson gson = new Gson();
		// 无法序列化 Gson只知道该类的父类是HashMap而不知道该类。由于Gson是通过类反射得到属性再序列化，因为不知道类名，所以转json失败。
		// String s = gson.toJson(map);

		// 指定类型之后可以序列化
		String s = gson.toJson(map, new TypeToken<Map<String, String>>() {
		}.getType());
		System.out.println(s);
	}

	@Test
	public void test2() {

		HashMap<String, String> map = new HashMap<>();
		map.put("a", "1");
		map.put("b", "2");
		map.put("c", "3");

		Set<Map.Entry<String, String>> entries = map.entrySet();
		Gson gson = new Gson();
		// 序列化结果没有值，HashMap中的内部类EntrySet中没有存储值，遍历时还是获取外部类的原数据
		String s = gson.toJson(entries);
		System.out.println(s);
	}

}
