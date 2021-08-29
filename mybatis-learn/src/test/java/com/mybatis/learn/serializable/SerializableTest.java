package com.mybatis.learn.serializable;

import java.io.*;

/**
 * 对象的类名、Field（包括基本类型、数组及对其他对象的引用）都会被序列化，对象的static Field，transient Field及方法不会被序列化；
 * 实现Serializable接口的类，如不想某个Field被序列化，可以使用transient关键字进行修饰；
 * 保证序列化对象的引用类型Filed的类也是可序列化的，如不可序列化，可以使用transient关键字进行修饰，否则会序列化失败；
 * 反序列化时必须要有序列化对象的类的class文件；
 * 当通过文件网络读取序列化对象的时候，必需按写入的顺序来读取。
 */
public class SerializableTest {
}


class User implements Serializable {

	private static final long serialVersionUID = -3901223346764386324L;

	private String id;
	private String name;
	private int age;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", age=" + age +
				'}';
	}

	/**
	 * 序列化时
	 * ObjectOutputStream在执行自己的writeObject方法前会先通过反射在要被序列化的对象的类中查找有无自定义的writeObject方法，
	 * 如有的话，则会优先调用自定义的writeObject方法。
	 * 因为查找反射方法时使用的是getPrivateMethod，所以自定以的writeObject方法的作用域要被设置为private。
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {

	}

	/**
	 * 反序列化时
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

	}

	/**
	 * 在下面的情况下返回一个合理值:
	 * 1. 序列化版本不兼容；
	 * 2. 输入流被篡改或者损坏；
	 */
	private void readObjectNoData() throws ObjectStreamException {

	}

	/**
	 * 在序列化时会先调用writeReplace方法将当前对象替换成该方法返回的对象, 并将其写入流中
	 * 所以该方法的返回值必须是可序列化的
	 * 优先级最高，定义之后其他三个方法都不会被调用
	 */
	private Object writeReplace() throws ObjectStreamException {
		return new Object();
	}

	/**
	 * 反序列化时，在{@code readObject}之后被调用
	 * 可以通过this得到{@code readObject}的结果进行进一步的修改或直接替换
	 * 最终反序列化返回的是该方法的结果, {@code readObject}中反序列化生成的对象被抛弃
	 * 可以用来 保护性恢复单例、枚举类型的对象
	 * 恢复的时候没有改变其值（val的值没有改变）同时恢复的时候又能正常实现枚举值的对比（地址也完全相同）
	 */
	private Object readResolve() throws ObjectStreamException {
		return new Object();
	}
}