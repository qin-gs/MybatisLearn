package com.mybatis.learn.test;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.stream.Stream;

public class YmlTest {
	public static void main(String[] args) {
		Yaml yaml = new Yaml();
		InputStream stream = YmlTest.class.getResourceAsStream("/application.yml");
		Map<String, String> map = yaml.loadAs(stream, Map.class);
		System.out.println(map);
	}
}
