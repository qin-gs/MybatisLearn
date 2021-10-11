package com.example.mapper;

import com.example.bean.Department;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DepartmentMapperTest {

    @Autowired
    private DepartmentMapper mapper;

    @Test
    void count() {
        SelectStatementProvider provider = SqlBuilder.countColumn(DepartmentDynamicSqlSupport.id)
                .from(DepartmentDynamicSqlSupport.department)
                .build()
                .render(RenderingStrategies.MYBATIS3);
        long count = mapper.count(provider);
        System.out.println(count);
    }

    @Test
    void selectMany() {
        SelectStatementProvider provider = SqlBuilder.select(DepartmentMapper.selectList)
                .from(DepartmentDynamicSqlSupport.department)
                .build()
                .render(RenderingStrategies.MYBATIS3);
        List<Department> departments = mapper.selectMany(provider);
        departments.forEach(System.out::println);

    }
}