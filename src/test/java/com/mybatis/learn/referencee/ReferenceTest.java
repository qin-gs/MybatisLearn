package com.mybatis.learn.referencee;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.WeakHashMap;

public class ReferenceTest {
	public static void main(String[] args) {
		Object obj = new Object();
		Map<Object, Object> map = new WeakHashMap<>();
		ReferenceQueue<Object> queue = new ReferenceQueue<>();
		for (int i = 0; i < 10000; i++) {
			byte[] bytes = new byte[1024 * 1024];
			// 当bytes没有在其他地方被引用，只有reference这个地方存在引用
			// 就把bytes对象gc掉
			// 当bytes被回收时，会把reference对象放入queue中
			WeakReference<byte[]> weakReference = new WeakReference<>(bytes, queue);
			map.put(weakReference, obj);
		}

		Thread thread = new Thread(() -> {
			try {
				int count = 0;
				WeakReference<byte[]> k;
				while ((k = (WeakReference<byte[]>) queue.remove()) != null) {
					System.out.println((count ++) + " 回收了 " + k);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread.setDaemon(true);
		thread.start();

		Scanner sc = new Scanner(System.in);
		sc.next();
		System.out.println("map.size() = " + map.size());
	}
}
