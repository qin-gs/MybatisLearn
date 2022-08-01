### Mybatis plus



```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.3</version>
</dependency>
```

#### BaseMapper 原理

- `MybatisPlusAutoConfiguration ` 创建(`@Bean`)  `MybatisSqlSessionFactoryBean`，并设置`MybatisConfiguration`作为配置类。

- 其中`MybatisConfiguration extends Configuration` (mybatis的核心配置类)存放一个 `MybatisMapperRegistry`

- 解析自定义 mapper 的 xml文件，获取 `AbstractMethod `集合

- 遍历 `AbstractMethod `集合，然后调用各自实现的`injectMappedStatement()`方法，注入SQL
- 添加注册`MappedStatement`对象

```java
class MybatisSqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean, ApplicationListener<ApplicationEvent>

afterPropertiesSet -> 创建 SqlSessionFactory -> 解析自定义mapper的xml文件(xmlMapperBuilder.parse())

public void parse() {
  if (!configuration.isResourceLoaded(resource)) {
    configurationElement(parser.evalNode("/mapper"));
    configuration.addLoadedResource(resource);
    bindMapperForNamespace(); // 绑定命名空间，MybatisMapperRegistry#addMapper
  }

  parsePendingResultMaps();
  parsePendingCacheRefs();
  parsePendingStatements();
}

// MybatisMapperRegistry 使用 MapperAnnotationBuilder 进行解析
// MybatisMapperAnnotationBuilder#parse 注入基本sql
GlobalConfigUtils.getSqlInjector(configuration).inspectInject(assistant, type);
AbstractMethod 子类可以通过重写 injectMappedStatement 注入自定义方法

com.baomidou.mybatisplus.core.injector.methods.Insert extends AbstractMethod

BaseMapper里的语句信息模板，来自于枚举类 SqlMethod，最终转成 MappedStatement 对象，然后添加注册
```

