package com.mybatis.learn.type;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResolveTypeTest {

	public static void main(String[] args) throws NoSuchFieldException {

		Field field = Foo.class.getDeclaredField("list");
		System.out.println(field.getGenericType()); // java.util.List<java.lang.String>
		System.out.println(field.getAnnotatedType()); // sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedParameterizedTypeImpl@14acaea5
		System.out.println(Arrays.toString(Foo.class.getTypeParameters())); // [K, V]
		System.out.println(field.getGenericType() instanceof ParameterizedType); // true
		// ParameterizedType的三个方法
		System.out.println(((ParameterizedType) field.getGenericType()).getRawType()); // interface java.util.List
		System.out.println(Arrays.toString(((ParameterizedType) field.getGenericType()).getActualTypeArguments())); // [class java.lang.String]
		System.out.println(((ParameterizedType) field.getGenericType()).getOwnerType()); // null

		// TypeVariable的三个方法
		Field field2 = Foo.class.getDeclaredField("map");
		System.out.println(field2.getType()); // interface java.util.Map
		Type genericType = field2.getGenericType();
		System.out.println(Arrays.toString(((ParameterizedType) genericType).getActualTypeArguments())); // [K, K]
		for (Type type : ((ParameterizedType) genericType).getActualTypeArguments()) {
			System.out.println(Arrays.toString(((TypeVariable) type).getBounds())); // [class java.lang.Object]
			System.out.println(((TypeVariable) type).getGenericDeclaration()); // class com.mybatis.learn.type.Foo
			System.out.println(((TypeVariable) type).getName()); // K
		}

		// WildcardType
		Field field3 = Foo.class.getDeclaredField("num");
		System.out.println(field3.getGenericType()); // java.util.List<? extends java.lang.Number>
		Type[] types = ((ParameterizedType) field3.getGenericType()).getActualTypeArguments();
		for (Type type : types) {
			System.out.println(Arrays.toString(((WildcardType) type).getUpperBounds())); // [class java.lang.Number]
			System.out.println(Arrays.toString(((WildcardType) type).getLowerBounds())); // []
		}

		Field field4 = Foo.class.getDeclaredField("integer");
		System.out.println(field4.getGenericType()); // java.util.List<? super java.lang.Integer>
		Type[] types4 = ((ParameterizedType) field4.getGenericType()).getActualTypeArguments();
		for (Type type : types4) {
			System.out.println(Arrays.toString(((WildcardType) type).getUpperBounds())); // [class java.lang.Object]
			System.out.println(Arrays.toString(((WildcardType) type).getLowerBounds())); // [class java.lang.Integer]
		}

		// GenericArrayType
		Field field5 = Foo.class.getDeclaredField("lists");
		System.out.println(field5.getGenericType()); // java.util.List<java.lang.String>[]
		System.out.println(((GenericArrayType) field5.getGenericType()).getGenericComponentType()); // java.util.List<java.lang.String>

	}
}

class Foo<K, V> {
	Map<K, K> map = new HashMap<>();
	List<String> list = new ArrayList<>();
	List<? extends Number> num = new ArrayList<>();
	List<? super Integer> integer = new ArrayList<>();
	List<String>[] lists = new List[2];
}

