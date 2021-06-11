package com.mybatis.learn.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCacheTest {
	public static void main(String[] args) {
		Map<String, String> map = new LinkedHashMap<>(5, .75f, true) {
			@Override
			protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
				return size() > 5;
			}
		};
	}
}
