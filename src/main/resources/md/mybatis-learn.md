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







