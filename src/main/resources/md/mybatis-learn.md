SqlSession(门面):

1. 基本api 增删查改
2. 辅助api 提交关闭会话

Executor：

1. 基本功能： 改 查 维护缓存 事务管理
2. 辅助api: 提交关闭执行器，批处理刷新  
   ![executor继承关系](./image/executor-hierarchy.jpg)

StatementHandler:

1. 生命JdbcStatement 填充参数
2. sql执行  
   jdbc处理器
   ![statementHandler继承关系](./image/statementhandler-hierarchy.jpg)
   执行语句，预编译，设置参数，执行jdbc，结果集映射

| 执行器 | 基础jdbc处理器 | 预处理器 | 参数处理器 | 结果处理器 | 
| --- | --- | --- | --- | --- |
| Executor | BaseStatementHandler |PreparedStatementHandler | ParameterHandler | ResultSetHandler |

![statementHandler执行流程](./image/statementhandler处理流程.jpg)

参数处理  
java bean -> jdbc参数

ParamNameResolver

1. 单个参数 默认不转换，除非设置了@Param
2. 多个参数
    1. 转换成Param1, Param2 -> Map
    2. 通过@Param中的name属性转换
    3. 基于反射转换成变量名，如果不支持(jdk7)转换成arg0, arg1

ParameterHandler

1. 单个原始类型 直接映射，忽略sql中的引用名称
2. Map类型 基于key映射
3. Object 基于属性名映射，支持嵌套对象属性访问(MetaObject)

ResultSetHandler(将结果集的行转换成对象)  
ResultContext(存放当前行对象，以及解析状态和控制解析数量)  
ResultHandler(处理存入解析结果)

mybatis映射体系(反射)  
**MetaObject**

1. 查找属性， 忽略大小写，支持驼峰，支持子属性
2. 获取属性值
    1. 基于点获取子属性 user.name
    2. 基于索引获取列表值 users[1].name
    3. 基于key获取map值 user[name]
3. 设置属性
    1. 可设置子属性值
    2. 支持自动创建子属性(必须带有无参构造函数，不能有集合)

**BeanWrapper**(只能设置当前对象的属性，不能设置属性的属性)

1. 查找属性
2. 获取属性值
3. 设置属性
4. 创建属性

**MetaClass**  
基于属性名获取set, get方法，支持子属性获取

**Reflector**  
基于属性名获取set, get方法，属性类别，不支持子属性获取








