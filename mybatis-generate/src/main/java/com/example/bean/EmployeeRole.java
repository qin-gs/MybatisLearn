package com.example.bean;

import javax.annotation.Generated;

public class EmployeeRole {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String employeeId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String roleId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(String id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getEmployeeId() {
        return employeeId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getRoleId() {
        return roleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", roleId=").append(roleId);
        sb.append("]");
        return sb.toString();
    }
}