### mybatis技术内幕

1. **接口层** sqlSession
2. **核心处理层** 配置解析，参数映射，sql解析，sql执行，结果集映射，插件
3. **基础支持层** 数据源模块，事务管理模块，缓存模块，Binding模块，反射模块，类型转换，日志模块，资源加载，解析器模块

![mybatis语句执行过程](./image/mybatis语句执行过程.png)

#### 基础支持层

2.1 解析器模块  
相关类(org.mybatis.ibatis.parsing)：XPathParser XNode TokenHandler PropertyParser

2.2 反射工具

1. **Reflector**(缓存反射操作需要使用的类的元信息)  
   JavaBean:
   字段: 定义的成员变量 属性: 通过getter/setter得到的(只与类中的方法有关，与是否存在成员变量没有关系)

Map<String, Invoker> getMethods = new HashMap<>();  
![invoker接口](./image/invoker接口.png)

addGetMethods(clazz) 处理clazz中的getter方法，填充getMethods和getTypes集合

1. getClassMethods(clazz) 获取当前类及其父类中定义的所有方法的唯一签名 和 对应的Methods对象
2. resolveGetterConflicts 子类覆盖父类的getter方法且返回值发生变化时，处理冲突
3. addGetMethod 完成getMethods 和 getTypes 集合的填充

addFields(clazz) 处理类中定义的所有字段，将处理后的字段信息添加到集合中
(final static can only be set by the classloader)

ReflectorFactory接口 实现对Reflector对象的创建和缓存  
DefaultReflectorFactory

2. **TypeParameterResolver**  
   java.lang.reflect.Type接口:
    1. 子接口: ParameterizedType, GenericArrayType, TypeVariable, WildcardType
        1. ParameterizedType 参数化类型 List<String>  
           Type getRawType 返回参数化类型中的原始类型List  
           Type[] getActualTypeArguments 返回类型变量或实际类型列表 String  
           Type getOwnerType 返回类型所所属类型 Map<K,V>接口是Map.Entry<K, V>接口的所有者
        2. TypeVariable 类型变量，反应在JVM编译该泛型前的信息 List<T> T就是类型变量  
           Type[] getBounds 获取类型变量的上界，为明确声明则为Object Test<T extends User> -> User  
           D getGenericDeclaration 获取声明改类型变量的原始类型 Test<T extends User> -> Test  
           String getName 获取源码中定义的名称 T
        3. GenericArrayType 表示数组类型且组成元素是ParameterizedType 或 TypeVariable  
           Type getGenericComponentType 返回数组的组成元素
        4. WildcardType 通配符泛型(<? extends Number>, <? super Integer>)  
           Type[] getUpperBounds 返回泛型变量上界  
           Type[] getLowerBounds 返回泛型变量下界
    2. 实现类: Class
        1. Class: 它表示的是原始类型。Class 类的对象表示JVM中的一个类或接口，每个Java 类在NM 里都表现为一个Class 对象。在程序中可以通过“类名.class ”、“对象.getClass()
           ”或是Class.forName(类名)等方式获取Class。**数组也被映射为Class对象，所有元素类型相同且维数相同的数组都共享同一个Class对象**

TypeParameterResolver 提供静态方法解析指定类中的字段，方法返回值或方法参数类型

3. **ObjectFactory**  
   通过多个重载的create方法创建对象  
   DefaultObjectFactory是唯一实现  
   instantiateClass根据传入的参数列表选择合适的构造函数实例化对象

4. **Property**工具类
    1. PropertyTokenizer Iterable 对传入的表达式进行解析 (orders[0].items[0].name)
    2. PropertyNamer 完成方法名到属性名的转换
    3. PropertyCopier 完成相同类型的两个对象之间的属性值拷贝

5. **MetaClass**  
   完成对复杂属性表达式的解析，并获取指定属性描述信息  
   类级别的元信息封装和处理

6. **ObjectWrapper**
   对象级别的元信息处理：抽象了对象的属性信息，定义一系列查询和更新对象属性信息的方法  
   ObjectWrapperFactory(实现类DefaultObjectWrapperFactory不可用)负责创建ObjectWrapper  
   ![ObjectWrapper继承关系.png](./image/ObjectWrapper继承关系.png)

7. **MetaObject**
   完成属性表达式的解析过程

2.3 **TypeHandler**类型转换  
完成Java类型 和 JDBC类型的互相转换  
enum JdbcType代表JDBC中的数据类型，HashMap<TYPE_CODE, JdbcType>维护常量编码和JdbcType的关系  
所有的类型转换器全部继承TypeHandler

1. setParameter 通过PreparedStatement为sql语句绑定参数时，将数据从JdbcType类型转换成Java类型
2. getResult 从ResultSet中获取结果时，将数据从Java类型转换成JdbcType类型

TypeHandlerRegistry 管理众多的TypeHandler  
mybaits初始化时，会为所有已知的TypeHandler创建对象，注册在其中

TypeAliasRegistry 完成别名注册和管理功能(管理别名和java类型之间的关系)

2.4 日志模块  
设计模式六大原则：

1. 单一职责原则
2. 里氏替换原则
3. 依赖倒置原则
4. 接口隔离原则
5. 迪米塔法则
6. **开放封闭原则**  程序要对扩展开放，对修改关闭

适配器模式  
需要适配的类(真正的业务逻辑) <--> 适配器 <--> 目标接口(调用者使用)  
com.apache.ibatis.logging.Log 定义日志模块的功能  
LogFactory 创建对应的日志组件适配器

代理模式 与 JDK动态代理    
代理模式可以控制对真正对象的访问，或在执行业务处理的前后进行相关的预处理和后置处理，还可以用于实现延迟加载(当系统真正使用数据时，再调用 代理对象完成数据库的查询并返回数据)功能  
静态代理：编译阶段就要创建代理类  
JDK动态代理：InvocationHandler接口，动态创建代理类并通过类加载器加载，然后在创建代理对象时将InvokeHandler对象作为构造参数传入，当调用代理对象时 ，会调用InvokerHandler.invoke()
方法，并最终调用真正的业务对象的相应方法。

```text
Proxy.newProxyInstance(ClassLoader loader, Class<?> interfaces, InvocationHandler h)
loader 加载动态生成的代理类的类加载器
interfaces 业务类实现的接口
h 实现InvocationHandler的对象

业务逻辑(java.lang.reflect.Proxy类中):
 1. 获取代理类的Class getProxyClass0(loader, interfaces)
   1. 限制接口数量 < 65536
   2. 如果指定的类加载器中已经创建了实现指定接口的代理类，就从缓存(WeakCache<ClassLoader, Class<?>[], Class<?>> proxyClassCache)中查找；否则通过ProxyClassFactory创建实现指定接口的代理类
   3. WeakCache.get先从缓存中查找代理类，如果找不到创建Factory(WeakCache的内部类)对象调用get方法获取代理类，Factory.get会调用ProxyClassFactory.apply(Proxy类中，是一个BiFunction<T, U, R>(提供两个参数，返回一个结果))创建并加载代理类
   4. apply方法先检测代理类需要实现的接口集合，确定代理类的名称，创建代理类并写入文件，最后加载代理类返回对应的Class对象用于后续实例化代理类对象
 2. 获取代理类的构造方法
 3. 创建代理对象
```

JDBC调试  
com.apache.ibatis.logging.jdbc包通过动态代理的方式将JDBC操作通过指定的日志框架打印出来  
![BaseJdbcLogger继承关系](./image/BaseJdbcLogger继承关系.png)
BaseJdbcLogger ConnectionLogger 封装Connection对象同时为其封装的Connection对象创建相应的代理对象  
PreparedStatementLogger 封PreparedStatement对象，并为其创建相应的代理对象  
StatementLogger  
ResultLogger 封装ResultSet对象并为其创建代理对象 (展示查询结果)

2.5 资源加载  
org.apache.ibatis.io包封装ClassLoader以及读取资源文件的API  
ClassLoaderWrapper是ClassLoader包装器  
Resources 通过类加载器读取资源文件  
ResolverUtil 根据指定条件查找指定包下的类

